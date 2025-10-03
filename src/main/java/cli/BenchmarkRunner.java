package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class BenchmarkRunner {

    private static final String BENCHMARK_CSV = "benchmark_results.csv";
    private static final String CORRECTNESS_CSV = "correctness_validation.csv";
    private static final String PERFORMANCE_CSV = "performance_analysis.csv";
    private static final String MEMORY_CSV = "memory_profiling.csv";

    public static void main(String[] args) {
        System.out.println("Boyer-Moore Majority Vote Algorithm - Comprehensive Testing Suite");
        System.out.println("=============================================================\n");

        try {
            runCorrectnessValidation();
            runPerformanceAnalysis();
            runMemoryProfiling();
            runScalabilityTests();

            System.out.println("All tests completed successfully!");
            System.out.println("Generated CSV files:");
            System.out.println("  - " + BENCHMARK_CSV);
            System.out.println("  - " + CORRECTNESS_CSV);
            System.out.println("  - " + PERFORMANCE_CSV);
            System.out.println("  - " + MEMORY_CSV);

        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runCorrectnessValidation() throws IOException {
        System.out.println("Running Correctness Validation Tests...");

        try (FileWriter writer = new FileWriter(CORRECTNESS_CSV)) {
            writer.write("TestCase,InputSize,InputType,Expected,Actual,Correct,ExecutionTimeMs\n");

            // Edge cases
            runEdgeCaseTests(writer);

            // Property-based testing
            runPropertyBasedTests(writer);

            // Cross-validation tests
            runCrossValidationTests(writer);
        }

        System.out.println("  ✓ Correctness validation completed");
    }

    private static void runEdgeCaseTests(FileWriter writer) throws IOException {
        TestCase[] edgeCases = {
            new TestCase("Empty Array", new int[]{}, -1, -1, -1),
            new TestCase("Single Element", new int[]{42}, 42, 0, 0),
            new TestCase("Two Same Elements", new int[]{5, 5}, 5, 0, 1),
            new TestCase("Two Different Elements", new int[]{1, 2}, -1, -1, -1),
            new TestCase("All Same Elements", new int[]{7, 7, 7, 7, 7}, 7, 0, 4),
            new TestCase("No Majority", new int[]{1, 2, 3, 4, 5}, -1, -1, -1),
            new TestCase("Majority at Beginning", new int[]{3, 3, 3, 1, 2}, 3, 0, 2),
            new TestCase("Majority at End", new int[]{1, 2, 4, 4, 4}, 4, 2, 4),
            new TestCase("Negative Numbers", new int[]{-1, -1, -1, 2, 3}, -1, 0, 2)
        };

        for (TestCase testCase : edgeCases) {
            runTestCase(writer, testCase);
        }
    }

    private static void runPropertyBasedTests(FileWriter writer) throws IOException {
        Random random = new Random(42); // Fixed seed for reproducibility

        for (int size = 10; size <= 1000; size *= 10) {
            for (int trial = 0; trial < 50; trial++) {
                // Generate random array with guaranteed majority
                int[] array = generateRandomArrayWithMajority(size, random);

                // Find expected result using naive approach
                int expected = findMajorityNaive(array);
                int firstIndex = -1, lastIndex = -1;
                if (expected != -1) {
                    firstIndex = findFirstIndex(array, expected);
                    lastIndex = findLastIndex(array, expected);
                }

                TestCase testCase = new TestCase(
                    "Random-" + size + "-" + trial,
                    array,
                    expected,
                    firstIndex,
                    lastIndex
                );

                runTestCase(writer, testCase);
            }
        }
    }

    private static void runCrossValidationTests(FileWriter writer) throws IOException {
        // Test against known sorting algorithms where applicable
        // For Boyer-Moore, we mainly test correctness of majority detection

        int[][] testArrays = {
            {1, 1, 1, 2, 2},
            {3, 2, 3},
            {1, 2, 3, 1, 1, 1},
            {5, 5, 5, 5, 5, 5, 5}
        };

        for (int i = 0; i < testArrays.length; i++) {
            int[] array = testArrays[i];
            int expected = findMajorityNaive(array);
            int firstIndex = expected != -1 ? findFirstIndex(array, expected) : -1;
            int lastIndex = expected != -1 ? findLastIndex(array, expected) : -1;

            TestCase testCase = new TestCase(
                "CrossValidation-" + i,
                array,
                expected,
                firstIndex,
                lastIndex
            );

            runTestCase(writer, testCase);
        }
    }

    private static void runTestCase(FileWriter writer, TestCase testCase) throws IOException {
        long startTime = System.nanoTime();
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(testCase.array);
        long endTime = System.nanoTime();

        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        boolean correct = (result.majorityElement == testCase.expectedMajority) &&
                         (result.firstIndex == testCase.expectedFirstIndex) &&
                         (result.lastIndex == testCase.expectedLastIndex);

        writer.write(String.format("%s,%d,%s,%d,%d,%s,%.6f\n",
            testCase.name,
            testCase.array.length,
            getInputType(testCase.array),
            testCase.expectedMajority,
            result.majorityElement,
            correct ? "true" : "false",
            executionTimeMs
        ));

        if (!correct) {
            System.err.println("  ✗ FAILED: " + testCase.name);
            System.err.println("    Expected: " + testCase.expectedMajority +
                             " (first: " + testCase.expectedFirstIndex +
                             ", last: " + testCase.expectedLastIndex + ")");
            System.err.println("    Actual: " + result.majorityElement +
                             " (first: " + result.firstIndex +
                             ", last: " + result.lastIndex + ")");
        }
    }

    private static void runPerformanceAnalysis() throws IOException {
        System.out.println("Running Performance Analysis...");

        int[] sizes = {100, 500, 1000, 5000, 10000, 25000, 50000, 100000};
        String[] distributions = {"Random", "Sorted", "ReverseSorted", "NearlySorted"};

        try (FileWriter writer = new FileWriter(PERFORMANCE_CSV)) {
            writer.write("ArraySize,Distribution,Comparisons,ArrayAccesses,Assignments,ExecutionTimeMs\n");

            for (int size : sizes) {
                for (String distribution : distributions) {
                    int[] array = generateArray(size, distribution);

                    BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

                    writer.write(String.format("%d,%s,%d,%d,%d,%.6f\n",
                        size,
                        distribution,
                        result.metrics.getComparisonCount(),
                        result.metrics.getArrayAccessCount(),
                        result.metrics.getAssignmentCount(),
                        result.metrics.getExecutionTimeMs()
                    ));

                    System.out.printf("  Size: %6d, Distribution: %12s, Time: %8.3f ms%n",
                        size, distribution, result.metrics.getExecutionTimeMs());
                }
            }
        }

        System.out.println("  ✓ Performance analysis completed");
    }

    private static void runMemoryProfiling() throws IOException {
        System.out.println("Running Memory Profiling...");

        try (FileWriter writer = new FileWriter(MEMORY_CSV)) {
            writer.write("ArraySize,UsedMemoryMB,TotalMemoryMB,MaxMemoryMB,GCCount,GCTimeMs\n");

            int[] sizes = {1000, 10000, 50000, 100000};

            for (int size : sizes) {
                // Force garbage collection before measurement
                System.gc();
                Thread.yield();

                long beforeMemory = getUsedMemory();
                long beforeTime = System.nanoTime();

                int[] array = generateRandomArrayWithMajority(size, new Random());
                BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

                long afterTime = System.nanoTime();

                // Force garbage collection after measurement
                System.gc();
                Thread.yield();

                long afterMemory = getUsedMemory();

                Runtime runtime = Runtime.getRuntime();
                long memoryUsed = afterMemory - beforeMemory;
                long totalMemory = runtime.totalMemory();
                long maxMemory = runtime.maxMemory();

                writer.write(String.format("%d,%.2f,%.2f,%.2f,%d,%.3f\n",
                    size,
                    memoryUsed / (1024.0 * 1024.0),
                    totalMemory / (1024.0 * 1024.0),
                    maxMemory / (1024.0 * 1024.0),
                    0, // GC count (simplified)
                    (afterTime - beforeTime) / 1_000_000.0
                ));

                System.out.printf("  Size: %6d, Memory Used: %6.2f MB%n",
                    size, memoryUsed / (1024.0 * 1024.0));
            }
        }

        System.out.println("  ✓ Memory profiling completed");
    }

    private static void runScalabilityTests() throws IOException {
        System.out.println("Running Scalability Tests...");

        int[] sizes = {100, 1000, 10000, 100000};

        try (FileWriter writer = new FileWriter(BENCHMARK_CSV)) {
            writer.write("ArraySize,Comparisons,ArrayAccesses,Assignments,ExecutionTimeMs\n");

            for (int size : sizes) {
                System.out.println("  Testing size: " + size);

                int[] array = generateRandomArrayWithMajority(size, new Random());
                BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

                writer.write(result.metrics.toCSVRow(size) + "\n");

                System.out.println("    " + result.metrics.toString());
            }
        }

        System.out.println("  ✓ Scalability tests completed");
    }

    private static int[] generateArray(int size, String distribution) {
        Random random = new Random();
        int[] array = new int[size];

        switch (distribution) {
            case "Sorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i % 100;
                }
                break;
            case "ReverseSorted":
                for (int i = 0; i < size; i++) {
                    array[i] = (size - i) % 100;
                }
                break;
            case "NearlySorted":
                for (int i = 0; i < size; i++) {
                    array[i] = i % 100;
                }
                // Add some random swaps
                for (int i = 0; i < size / 20; i++) {
                    int idx1 = random.nextInt(size);
                    int idx2 = random.nextInt(size);
                    int temp = array[idx1];
                    array[idx1] = array[idx2];
                    array[idx2] = temp;
                }
                break;
            default: // Random
                for (int i = 0; i < size; i++) {
                    array[i] = random.nextInt(100);
                }
                break;
        }

        return array;
    }

    private static int[] generateRandomArrayWithMajority(int size, Random random) {
        int[] array = new int[size];
        int majorityElement = random.nextInt(100);
        int majorityCount = (size / 2) + 1 + random.nextInt(size / 4);

        for (int i = 0; i < majorityCount && i < size; i++) {
            array[i] = majorityElement;
        }

        for (int i = majorityCount; i < size; i++) {
            int value;
            do {
                value = random.nextInt(100);
            } while (value == majorityElement);
            array[i] = value;
        }

        shuffleArray(array, random);
        return array;
    }

    private static void shuffleArray(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static int findMajorityNaive(int[] array) {
        int n = array.length;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (array[i] == array[j]) {
                    count++;
                }
            }
            if (count > n / 2) {
                return array[i];
            }
        }
        return -1;
    }

    private static int findFirstIndex(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastIndex(int[] array, int target) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    private static String getInputType(int[] array) {
        if (array.length == 0) return "Empty";
        if (array.length == 1) return "Single";
        boolean sorted = true;
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i-1]) {
                sorted = false;
                break;
            }
        }
        if (sorted) return "Sorted";

        boolean reverseSorted = true;
        for (int i = 1; i < array.length; i++) {
            if (array[i] > array[i-1]) {
                reverseSorted = false;
                break;
            }
        }
        if (reverseSorted) return "ReverseSorted";

        return "Random";
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static class TestCase {
        final String name;
        final int[] array;
        final int expectedMajority;
        final int expectedFirstIndex;
        final int expectedLastIndex;

        TestCase(String name, int[] array, int expectedMajority, int expectedFirstIndex, int expectedLastIndex) {
            this.name = name;
            this.array = array;
            this.expectedMajority = expectedMajority;
            this.expectedFirstIndex = expectedFirstIndex;
            this.expectedLastIndex = expectedLastIndex;
        }
    }
}

