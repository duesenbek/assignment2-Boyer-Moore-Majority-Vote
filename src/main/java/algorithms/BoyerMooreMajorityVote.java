package algorithms;

import metrics.PerformanceTracker;

public class BoyerMooreMajorityVote {

    public static class Result {
        public final int majorityElement;
        public final int firstIndex;
        public final int lastIndex;
        public final PerformanceTracker metrics;

        public Result(int majorityElement, int firstIndex, int lastIndex, PerformanceTracker metrics) {
            this.majorityElement = majorityElement;
            this.firstIndex = firstIndex;
            this.lastIndex = lastIndex;
            this.metrics = metrics;
        }
    }

    public static Result findMajorityElement(int[] array) {
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.startTimer();

        if (array == null || array.length == 0) {
            tracker.stopTimer();
            return new Result(-1, -1, -1, tracker);
        }

        int candidate = findCandidate(array, tracker);

        if (!verifyCandidate(array, candidate, tracker)) {
            tracker.stopTimer();
            return new Result(-1, -1, -1, tracker);
        }

        int firstIndex = findFirstIndex(array, candidate, tracker);
        int lastIndex = findLastIndex(array, candidate, tracker);

        tracker.stopTimer();
        return new Result(candidate, firstIndex, lastIndex, tracker);
    }

    private static int findCandidate(int[] array, PerformanceTracker tracker) {
        int candidate = array[0];
        tracker.incrementArrayAccessCount();
        tracker.incrementAssignmentCount();

        int count = 1;
        tracker.incrementAssignmentCount();

        for (int i = 1; i < array.length; i++) {
            tracker.incrementArrayAccessCount();
            int current = array[i];
            tracker.incrementAssignmentCount();

            tracker.incrementComparisonCount();
            if (count == 0) {
                candidate = current;
                tracker.incrementAssignmentCount();
                count = 1;
                tracker.incrementAssignmentCount();
            } else {
                tracker.incrementComparisonCount();
                if (candidate == current) {
                    count++;
                    tracker.incrementAssignmentCount();
                } else {
                    count--;
                    tracker.incrementAssignmentCount();
                }
            }
        }

        return candidate;
    }

    private static boolean verifyCandidate(int[] array, int candidate, PerformanceTracker tracker) {
        int count = 0;
        tracker.incrementAssignmentCount();

        for (int i = 0; i < array.length; i++) {
            tracker.incrementArrayAccessCount();
            tracker.incrementComparisonCount();
            if (array[i] == candidate) {
                count++;
                tracker.incrementAssignmentCount();
            }
        }

        tracker.incrementComparisonCount();
        return count > array.length / 2;
    }

    private static int findFirstIndex(int[] array, int candidate, PerformanceTracker tracker) {
        for (int i = 0; i < array.length; i++) {
            tracker.incrementArrayAccessCount();
            tracker.incrementComparisonCount();
            if (array[i] == candidate) {
                return i;
            }
        }
        return -1;
    }

    private static int findLastIndex(int[] array, int candidate, PerformanceTracker tracker) {
        for (int i = array.length - 1; i >= 0; i--) {
            tracker.incrementArrayAccessCount();
            tracker.incrementComparisonCount();
            if (array[i] == candidate) {
                return i;
            }
        }
        return -1;
    }
}
