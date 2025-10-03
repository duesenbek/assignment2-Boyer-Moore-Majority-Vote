package metrics;

public class PerformanceTracker {
    private long comparisonCount;
    private long arrayAccessCount;
    private long assignmentCount;
    private long startTime;
    private long endTime;
    private long executionTime;

    public PerformanceTracker() {
        this.comparisonCount = 0;
        this.arrayAccessCount = 0;
        this.assignmentCount = 0;
        this.startTime = 0;
        this.endTime = 0;
        this.executionTime = 0;
    }

    public void incrementComparisonCount() {
        comparisonCount++;
    }

    public void incrementArrayAccessCount() {
        arrayAccessCount++;
    }

    public void incrementAssignmentCount() {
        assignmentCount++;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
        executionTime = endTime - startTime;
    }

    public long getComparisonCount() {
        return comparisonCount;
    }

    public long getArrayAccessCount() {
        return arrayAccessCount;
    }

    public long getAssignmentCount() {
        return assignmentCount;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public double getExecutionTimeMs() {
        return executionTime / 1_000_000.0;
    }

    public void reset() {
        comparisonCount = 0;
        arrayAccessCount = 0;
        assignmentCount = 0;
        startTime = 0;
        endTime = 0;
        executionTime = 0;
    }

    @Override
    public String toString() {
        return String.format(
            "Comparisons: %d, Array Accesses: %d, Assignments: %d, Time: %.3f ms",
            comparisonCount, arrayAccessCount, assignmentCount, getExecutionTimeMs()
        );
    }

    public String toCSVHeader() {
        return "ArraySize,Comparisons,ArrayAccesses,Assignments,ExecutionTimeMs";
    }

    public String toCSVRow(int arraySize) {
        return String.format("%d,%d,%d,%d,%.6f",
            arraySize, comparisonCount, arrayAccessCount, assignmentCount, getExecutionTimeMs()
        );
    }
}
