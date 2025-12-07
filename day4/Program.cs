namespace Day4
{
    class Solution
    {
        static char[][] LoadMap(string filepath)
        {
            var lines = File.ReadAllLines(filepath);
            var map = new char[lines.Length][];
            for (int i = 0; i < lines.Length; i++)
            {
                map[i] = lines[i].ToCharArray();
            }
            return map;
        }
        static int CountNeighbors(char[][] map, int r, int c)
        {
            int mapHeight = map.Length;
            int mapWidth = map[0].Length;
            int count = 0;
            for (int row_i = Math.Max(0, r - 1); row_i <= Math.Min(mapHeight - 1, r + 1); row_i++)
            {
                for (int col_i = Math.Max(0, c - 1); col_i <= Math.Min(mapWidth - 1, c + 1); col_i++)
                {
                    if ((row_i != r || col_i != c) && map[row_i][col_i] == '@')
                    {
                        count++;
                    }
                }
            }
            return count;
        }
        static List<(int row, int col)> GetAccessibleRolls(char[][] map)
        {
            List<(int, int)> accessibleRolls = new List<(int, int)> ();
            for (int r = 0; r < map.Length; r++)
            {
                for (int c = 0; c < map[r].Length; c++)
                {
                    if (map[r][c] == '@' && CountNeighbors(map, r, c) < 4)
                    {
                        accessibleRolls.Add((r, c));
                    }
                }
            }
            return accessibleRolls;
        }
        static int RemoveAccessibleRolls(char[][] map)
        {
            var removableRolls = GetAccessibleRolls(map);
            var count = 0;
            while (removableRolls.Count > 0)
            {
                count += removableRolls.Count;
                foreach (var (row, col) in removableRolls)
                {
                    map[row][col] = 'x';
                }
                removableRolls = GetAccessibleRolls(map);
            }
            return count;
        }
        static void Main(string[] args)
        {
            string inputFilePath = "input.txt";
            var map = LoadMap(inputFilePath);
            Console.WriteLine($"There are {GetAccessibleRolls(map).Count} rolls of paper that can be accessed by a forklift.");
            Console.WriteLine($"A total of {RemoveAccessibleRolls(map)} rolls of paper can be removed ");
        }
    }
}
