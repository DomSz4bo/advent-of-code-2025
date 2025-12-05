package main

import (
	"bufio"
	"fmt"
	"os"
)

func maxJolt(battery string) int {
	maxDigit := -1
	maxDigitAfterMaxDigit := -1
	length := len(battery)

	for i, x := range battery {
		d := int(x - '0')
		if maxDigit < d && i < length-1 {
			maxDigit = d
			maxDigitAfterMaxDigit = -1
		} else if maxDigitAfterMaxDigit < d {
			maxDigitAfterMaxDigit = d
		}
	}

	return maxDigit*10 + maxDigitAfterMaxDigit
}

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		fmt.Println("Could not open file!")
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	joltSum := 0
	for scanner.Scan() {
		jolt := maxJolt(scanner.Text())
		joltSum += jolt
	}
	fmt.Println("Total output joltage is", joltSum)

	if err := scanner.Err(); err != nil {
		fmt.Println(err)
	}
}
