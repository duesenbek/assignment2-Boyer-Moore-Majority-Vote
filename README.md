# 🧠 Boyer–Moore Majority Vote Algorithm

## 📊 Performance Analysis Summary

### ⏱️ Time Complexity — **O(n)** ✅ *Confirmed*

| Array Size | Time (ms) | Comparisons | Array Accesses | Assignments |
|-------------|-----------|-------------|----------------|-------------|
| 100         | 0.032     | 297         | 206            | 273         |
| 1,000       | 0.019     | 2,989       | 2,002          | 2,581       |
| 10,000      | 0.104     | 29,980      | 20,002         | 25,614      |
| 100,000     | 0.923     | 300,003     | 200,005        | 261,382     |
<img width="570" height="364" alt="Снимок экрана 2025-10-04 210005" src="https://github.com/user-attachments/assets/01d39b06-83cd-4634-a753-43a05bcd82f8" />

**Average operations per element:**
- Comparisons: ~3.0  
- Array Accesses: ~2.0  
- Assignments: ~2.6  

---

### 💾 Space Complexity — **O(1)** ✅ *Confirmed*

| Array Size | Used Memory | Total Memory | GC Time |
|-------------|-------------|---------------|----------|
| 1,000       | -0.04 MB    | 8.00 MB       | 0.066 ms |
| 10,000      | 0.04 MB     | 8.00 MB       | 0.377 ms |
| 50,000      | 0.19 MB     | 8.00 MB       | 1.472 ms |
| 100,000     | 0.38 MB     | 8.00 MB       | 4.170 ms |
<img width="496" height="664" alt="Снимок экрана 2025-10-04 210106" src="https://github.com/user-attachments/assets/cea0a085-3250-4e0c-b9f2-ff1214788379" />
---


### 📈 Performance Across Data Distributions (10,000 elements)

| Distribution | Time (ms) | Comparisons | Array Accesses |
|--------------|-----------|-------------|----------------|
| Random       | 0.412     | 25,042      | 20,000         |
| Sorted       | 0.358     | 25,000      | 20,000         |
| Reverse Sorted | 0.341   | 25,000      | 20,000         |
| Nearly Sorted | 0.038    | 25,009      | 20,000         |
<img width="563" height="663" alt="Снимок экрана 2025-10-04 210227" src="https://github.com/user-attachments/assets/46d8f3a3-07c4-4b86-abe0-02ccce5ed091" />

---

## 🧪 Validation Results

**✅ Correctness: 100% (All Tests Passed)**  
- 13/13 unit tests — edge case coverage  
- 200+ property-based tests — random input validation  
- 4 cross-validation tests — algorithm consistency  

**Edge Cases Handled:**
- Empty or null arrays  
- Single element  
- No majority element  
- Negative numbers  
- All identical elements  
- Majority at start/end  

---

### 🧍‍♂️ Large Scale Testing

- Processed arrays up to **100,000 elements**  
- Maintained **100% accuracy**  
- Verified **linear scalability**

---

## 🏗️ Project Structure
```
assignment2-Boyer-Moore-Majority-Vote/
├── src/main/java/
│ ├── algorithms/BoyerMooreMajorityVote.java
│ ├── metrics/PerformanceTracker.java
│ └── cli/BenchmarkRunner.java
├── src/test/java/algorithms/BoyerMooreMajorityVoteTest.java
├── docs/performance-plots/
│ ├── benchmark_results.csv
│ ├── correctness_validation.csv
│ ├── memory_profiling.csv
│ └── performance_analysis.csv
└── pom.xml
```

---

## 🚀 Quick Start

```bash
# Compile project
mvn compile

# Run unit tests
mvn test

# Run benchmarks
mvn exec:java

# Generate reports
java -cp target/classes cli.BenchmarkRunner
