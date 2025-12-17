#include <stdio.h>
#include <string.h>
#include <stdint.h>

int update_state(char state[], const char input[], const size_t len) {
    int split_count = 0;
    for (int i = 0; i < len; i++) {
        if (input[i] == '^' && state[i] == '|') {
            split_count++;
            state[i] = '^';
            if (i > 0) {
                state[i - 1] = '|';
            }
            if (i < len - 1) {
                state[i + 1] = '|';
            }
        }
    }
    return split_count;
}

void update_quantum_state(uint64_t state[], const char input[], const size_t len) {
    for (int i = 0; i < len; i++) {
        if (input[i] == '^' && state[i] > 0) {
            if (i > 0) {
                state[i - 1] += state[i];
            }
            if (i < len - 1) {
                state[i + 1] += state[i];
            }
            state[i] = 0;
        }
    }
}

void trim(char string[], const size_t len) {
    for (int i = (int)len - 1; i >= 0; i--) {
        if (string[i] == '\n' || string[i] == ' ') {
            string[i] = '\0';
        } else {
            break;
        }
    }
}

int main(void) {
    FILE *fptr = fopen("input.txt", "r");
    if (fptr == NULL) {
        fprintf(stderr, "File not found\n");
        return 1;
    }
    char line[256];
    fgets(line, sizeof(line), fptr);

    const size_t manifoldWidth = strlen(line) - 1;
    char manifold[manifoldWidth+1];
    manifold[manifoldWidth] = '\0';
    uint64_t q_manifold[manifoldWidth];
    for (size_t i = 0; i < manifoldWidth; i++) {
        if (line[i] == 'S') {
            manifold[i] = '|';
            q_manifold[i] = 1;
        }
        else {
            manifold[i] = '.';
            q_manifold[i] = 0;
        }
    }

    int split_count_sum = 0;
    int c = 1;
    while (fgets(line, sizeof(line), fptr) != NULL) {
        c++;
        trim(line, strlen(line));
        if (strlen(line) != manifoldWidth) {
            printf("Problem: %s", line);
            break;
        }
        split_count_sum += update_state(manifold, line, manifoldWidth);
        update_quantum_state(q_manifold, line, manifoldWidth);
    }

    printf("The beam will be split %d times.\n", split_count_sum);
    uint64_t timeline_count = 0;
    for (size_t i = 0; i < manifoldWidth; i++) {
        timeline_count += q_manifold[i];
    }
    printf("The tachyon particle ends up on %llu different timelines.", timeline_count);

    fclose(fptr);
    return 0;
}
