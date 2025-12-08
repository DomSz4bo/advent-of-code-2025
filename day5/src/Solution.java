import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Solution {
    private static List<Range> readRanges(Scanner scanner) {
        List<Range> ranges = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            ranges.add(Range.parseRange(line));
        }
        return ranges;
    }

    static int countAvailableFreshIngredients(String databaseFile) {
        int count = 0;
        try (Scanner scanner = new Scanner(new File(databaseFile))) {
            var ranges = readRanges(scanner);

            while (scanner.hasNextLong()) {
                var ingredientID = scanner.nextLong();
                boolean isFresh = ranges.stream().anyMatch(
                        range -> range.valueIsInRange(ingredientID)
                );
                if (isFresh) count++;
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            count = -1;
        }
        return count;
    }

    static long countFreshIngredientIDs(String databaseFile) {
        try (var scanner = new Scanner(new File(databaseFile))) {
            var ranges = readRanges(scanner);
            if (ranges.isEmpty()) return 0;
            ranges.sort(Comparator.comparingLong(Range::start));
            var mergedRanges = mergeRanges(ranges);
            return mergedRanges.stream().mapToLong(Range::getSize).sum();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return -1;
    }

    private static ArrayList<Range> mergeRanges(List<Range> ranges) {
        var mergedRanges = new ArrayList<Range>();
        var currentRange = ranges.getFirst();

        for (int i = 1; i < ranges.size(); i++) {
            var nextRange = ranges.get(i);
            if (currentRange.valueIsInRange(nextRange.start())) {
                currentRange = currentRange.merge(nextRange);
            } else  {
                mergedRanges.add(currentRange);
                currentRange = nextRange;
            }
        }

        mergedRanges.add(currentRange);
        return mergedRanges;
    }

    static void main() {
        var input = "input.txt";
        var result = countAvailableFreshIngredients(input);
        System.out.println("There are " + result + " available fresh ingredients.");

        var result2 = countFreshIngredientIDs(input);
        System.out.println("There are " + result2 + " fresh ingredient IDs in the database.");
    }
}

record Range(long start, long end) {
    Range {
        if (end < start)
            throw new IllegalArgumentException("End must be greater or equal to start.");
    }

    boolean valueIsInRange(long value) {
        return value >= start && value <= end;
    }

    Range merge(Range other) {
        if (start > other.start) return other.merge(this);
        if (!valueIsInRange(other.start))
            throw new IllegalArgumentException("Can't merge disjoint ranges.");
        return new Range(start, Math.max(end, other.end));
    }

    long getSize() {
        return end - start + 1;
    }

    static Range parseRange(String rangeString) {
        int dividerIndex = rangeString.indexOf('-');
        long start = Long.parseLong(rangeString.substring(0, dividerIndex));
        long end = Long.parseLong(rangeString.substring(dividerIndex + 1));
        return new Range(start, end);
    }
}
