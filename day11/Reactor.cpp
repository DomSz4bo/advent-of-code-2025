#include <cstdint>
#include <iostream>
#include <fstream>
#include <sstream>
#include <unordered_map>
#include <vector>

using namespace std;

class Devices {
    unordered_map<string, vector<string>> connected_devices;

    uint64_t countPathsWithMemory(
        const string& fromDevice, const string& toDevice,
        unordered_map<string, uint64_t> &path_count)
    {
        if (path_count.contains(fromDevice)) {
            return path_count.at(fromDevice);
        }

        if (fromDevice == toDevice) {
            return 1;
        }
        uint64_t paths = 0;
        for (const auto &connected_device : connected_devices[fromDevice]) {
            paths += countPathsWithMemory(connected_device, toDevice, path_count);
        }
        path_count[fromDevice] = paths;
        return paths;
    }

  public:
    explicit Devices(const string &filepath) {
        ifstream in_file(filepath);
        if (in_file.is_open() == false) {
            cerr << "Error opening file" << endl;
            exit(1);
        }
        string line;
        while (getline(in_file, line)) {
            istringstream iss(line);
            string device;
            iss >> device;
            device.pop_back();
            string toDevice;
            while (iss >> toDevice) {
                connected_devices[device].push_back(toDevice);
            }
        }
    }
    uint64_t countPaths(const string& fromDevice, const string& toDevice) {
        unordered_map<string, uint64_t> path_count;
        return countPathsWithMemory(fromDevice, toDevice, path_count);
    }
};

int main() {
    auto devices = Devices("input.txt");

    const auto count = devices.countPaths("you", "out");
    cout << "There are " << count << " different paths leading from you to out." << endl;

    const auto svrToFft = devices.countPaths("svr", "fft");
    const auto fftToDac = devices.countPaths("fft", "dac");
    const auto dacToOut = devices.countPaths("dac", "out");
    const auto solution2 = svrToFft * fftToDac * dacToOut;

    cout << "There are " << solution2
    << " paths leading from svr to out that visit both dac and fft." << endl;
    return 0;
}