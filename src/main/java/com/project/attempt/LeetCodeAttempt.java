package com.project.attempt;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        //int[] output = new int[]{5,3,1,4,3,5,2,4,2};
        //System.out.println(checkSequence(output));
        System.out.println(Arrays.toString(constructTheLexicographicallyLargestValidSequence(3)));
        System.out.println(Arrays.toString(constructTheLexicographicallyLargestValidSequence(5)));

    }

    public static int[] constructTheLexicographicallyLargestValidSequence(int n) {

        int[] sequence = new int[1 + 2 * (n - 1)];
        LinkedList<Integer> numbers = new LinkedList<>();

        for (int i = 2; i <= n; i++) {
            numbers.add(i);
        }

        numbers.add(1);

        return fillSequence(sequence, numbers);

    }

    private static int[] fillSequence(int[] sequence, LinkedList<Integer> numbers) {

        int[] answerSequence = new int[sequence.length];

        LinkedList<Integer> newNumbers = new LinkedList<>(numbers);

        int currentNumber = newNumbers.get(0);
        newNumbers.remove(0);

        if (newNumbers.isEmpty()) {

            for (int i = 0; i < sequence.length; i++) {

                answerSequence[i] = sequence[i];
                if (sequence[i] == 0) { answerSequence[i] = 1; }

            }

            return answerSequence;

        }

        for (int i = 0; i < sequence.length - currentNumber; i++) {

            if (sequence[i] == 0) {

                if (sequence[i+currentNumber] != 0) { continue; }

                int[] newSequence = sequence.clone();
                newSequence[i] = currentNumber;
                newSequence[i+currentNumber] = currentNumber;
                newSequence = fillSequence(newSequence, newNumbers);
                if (checkSequence(newSequence)) { answerSequence = newSequence; }

            }

        }

        return answerSequence;

    }

    private static boolean checkSequence(int[] sequence) {

        LinkedList<Integer> sequenceList = new LinkedList<>();

        for (int i = 0; i < sequence.length; i++) {

            if (sequence[i] == 0) { return false; }
            sequenceList.add(sequence[i]);

            if (sequence[i] != 1) {

                boolean front = false;
                if (i + sequence[i] < sequence.length &&  sequence[i + sequence[i]] == sequence[i]) {
                    front = true;
                }

                boolean back = false;
                if (i - sequence[i] >= 0 &&  sequence[i - sequence[i]] == sequence[i]) {
                    back = true;
                }

                if (!front && !back) { return false; }

            }

        }

        Collections.sort(sequenceList);

        if (sequenceList.get(0) != 1) { return false; }

        for (int i = 1; i < sequenceList.size(); i++) {

            if (i % 2 == 1 && sequenceList.get(i) != sequenceList.get(i + 1)) {
                return false;
            }

        }

        return true;

    }

}

// [5,3,1,4,3,5,2,4,2]
// [5,3,4,0,3,5,4,0,0]

// [3,1,2,3,2]
// [0,0,0,0,0]
