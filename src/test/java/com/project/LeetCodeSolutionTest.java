package com.project;

import com.project.solution.LeetCodeSolution;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeSolutionTest {

    @Test
    public void constructTheLexicographicallyLargestValidSequenceTest() {

        int[] sequence1 = new int[]{3, 1, 2, 3, 2};
        assertArrayEquals(sequence1, LeetCodeSolution.constructDistancedSequence(3));

        int[] sequence2 = new int[]{5, 3, 1, 4, 3, 5, 2, 4, 2};
        assertArrayEquals(sequence2, LeetCodeSolution.constructDistancedSequence(5));

    }

}
