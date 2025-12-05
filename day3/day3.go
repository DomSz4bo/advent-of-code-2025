package main

import (
	"bufio"
	"fmt"
	"os"
)

func fillArray[T any](arr []T, value T, fromIndex int) {
	for i := fromIndex; i < len(arr); i++ {
		arr[i] = value
	}
}

func digitsToInt(digits []int) int {
	result := 0
	for _, d := range digits {
		result = result*10 + d
	}
	return result
}

func maxJolt12(battery string) int {
	numOfDigits := 12
	maxDigits := make([]int, numOfDigits)
	fillArray(maxDigits, -1, 0)
	batteryLength := len(battery)

	for i, x := range battery {
		d := int(x - '0')
		for j := 0; j < numOfDigits; j++ {
			if maxDigits[j] < d && i < batteryLength-11+j {
				maxDigits[j] = d
				fillArray(maxDigits, -1, j+1)
				break
			}
		}
	}

	fmt.Println(maxDigits)
	return digitsToInt(maxDigits)
}

func maxJolt(battery string) int {
	maxDigit := -1
	maxDigitAfterMaxDigit := -1
	batteryLength := len(battery)

	for i, x := range battery {
		d := int(x - '0')
		if maxDigit < d && i < batteryLength-1 {
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
		jolt := maxJolt12(scanner.Text())
		joltSum += jolt
	}
	fmt.Println("Total output joltage is", joltSum)

	if err := scanner.Err(); err != nil {
		fmt.Println(err)
	}
}
