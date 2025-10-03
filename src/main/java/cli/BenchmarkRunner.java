package cli;

import algorithms.BoyerMooreMajorityVote;
import metrics.PerformanceTracker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class BenchmarkRunner {

    public static void main(String[] args) {
        System.out.println("Boyer-Moore Majority Vote Algorithm Benchmark");
        System.out.println("==============================================\n");

        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};
        String csvFileName = "benchmark_results.csv";

        try (FileWriter writer = new FileWriter(csvFileName)) {
            writer.write("ArraySize,Comparisons,ArrayAccesses,Assignments,ExecutionTimeMs\n");

            for (int size : sizes) {
                System.out.println("Running benchmark for array size: " + size);

                int[] array = generateArrayWithMajority(size);
                BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);

                System.out.println("  Majority Element: " + result.majorityElement);
                System.out.println("  First Index: " + result.firstIndex);
                System.out.println("  Last Index: " + result.lastIndex);
                System.out.println("  " + result.metrics.toString());
                System.out.println();

                writer.write(result.metrics.toCSVRow(size) + "\n");
            }

            System.out.println("Results saved to: " + csvFileName);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static int[] generateArrayWithMajority(int size) {
        Random random = new Random();
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
}
