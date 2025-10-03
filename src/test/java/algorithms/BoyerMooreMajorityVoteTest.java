package algorithms;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoyerMooreMajorityVoteTest {

    @Test
    public void testSimpleMajority() {
        int[] array = {3, 3, 4, 2, 3, 3, 3};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(3, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(6, result.lastIndex);
    }

    @Test
    public void testAllSameElements() {
        int[] array = {5, 5, 5, 5, 5};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(5, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(4, result.lastIndex);
    }

    @Test
    public void testNoMajority() {
        int[] array = {1, 2, 3, 4, 5};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(-1, result.majorityElement);
        assertEquals(-1, result.firstIndex);
        assertEquals(-1, result.lastIndex);
    }

    @Test
    public void testSingleElement() {
        int[] array = {7};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(7, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(0, result.lastIndex);
    }

    @Test
    public void testTwoElements() {
        int[] array = {1, 1};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(1, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(1, result.lastIndex);
    }

    @Test
    public void testEmptyArray() {
        int[] array = {};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(-1, result.majorityElement);
        assertEquals(-1, result.firstIndex);
        assertEquals(-1, result.lastIndex);
    }

    @Test
    public void testNullArray() {
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(null);
        
        assertEquals(-1, result.majorityElement);
        assertEquals(-1, result.firstIndex);
        assertEquals(-1, result.lastIndex);
    }

    @Test
    public void testMajorityAtEnd() {
        int[] array = {1, 2, 3, 4, 4, 4, 4};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(4, result.majorityElement);
        assertEquals(3, result.firstIndex);
        assertEquals(6, result.lastIndex);
    }

    @Test
    public void testMajorityAtBeginning() {
        int[] array = {2, 2, 2, 2, 1, 3, 4};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(2, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(3, result.lastIndex);
    }

    @Test
    public void testMetricsTracking() {
        int[] array = {1, 1, 1, 2, 2};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertNotNull(result.metrics);
        assertTrue(result.metrics.getComparisonCount() > 0);
        assertTrue(result.metrics.getArrayAccessCount() > 0);
        assertTrue(result.metrics.getAssignmentCount() > 0);
        assertTrue(result.metrics.getExecutionTime() >= 0);
    }

    @Test
    public void testLargeArray() {
        int[] array = new int[10001];
        for (int i = 0; i < 10001; i++) {
            array[i] = (i < 5001) ? 42 : i;
        }
        
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(42, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(5000, result.lastIndex);
    }

    @Test
    public void testNegativeNumbers() {
        int[] array = {-1, -1, -1, 2, 3};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(-1, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(2, result.lastIndex);
    }

    @Test
    public void testAlternatingPattern() {
        int[] array = {1, 2, 1, 2, 1, 2, 1};
        BoyerMooreMajorityVote.Result result = BoyerMooreMajorityVote.findMajorityElement(array);
        
        assertEquals(1, result.majorityElement);
        assertEquals(0, result.firstIndex);
        assertEquals(6, result.lastIndex);
    }
}
