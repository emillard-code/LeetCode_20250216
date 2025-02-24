package com.project;

import com.project.attempt.LeetCodeAttempt;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeAttemptTest {

    @Test
    public void constructTheLexicographicallyLargestValidSequenceTest() {

        int[] sequence1 = new int[]{3, 1, 2, 3, 2};
        assertArrayEquals(sequence1, LeetCodeAttempt.constructTheLexicographicallyLargestValidSequence(3));

        int[] sequence2 = new int[]{5, 3, 1, 4, 3, 5, 2, 4, 2};
        assertArrayEquals(sequence2, LeetCodeAttempt.constructTheLexicographicallyLargestValidSequence(5));

    }

}
