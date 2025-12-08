import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    static List<Range> readRanges(Scanner scanner) {
        List<Range> ranges = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            ranges.add(Range.parseRange(line));
        }
        return ranges;
    }

    static int countFreshIngredients(String databaseFile) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(new File(databaseFile));
            var ranges = readRanges(scanner);

            while (scanner.hasNextLong()) {
                var ingredientID = scanner.nextLong();
                boolean isFresh = ranges.stream().anyMatch(
                        r -> r.valueIsInRange(ingredientID)
                );
                if (isFresh) count++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return count;
    }

    static void main() {
        int result = countFreshIngredients("input.txt");
        System.out.println("There are " + result + " fresh ingredients in the database.");
    }
}

record Range(long start, long end) {
    boolean valueIsInRange(long value) {
        return value >= start && value <= end;
    }

    static Range parseRange(String rangeString) {
        int dividerIndex = rangeString.indexOf('-');
        long start = Long.parseLong(rangeString.substring(0, dividerIndex));
        long end = Long.parseLong(rangeString.substring(dividerIndex + 1));
        return new Range(start, end);
    }
}
