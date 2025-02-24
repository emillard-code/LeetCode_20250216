package com.project.attempt;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        System.out.println(Arrays.toString(constructTheLexicographicallyLargestValidSequence(3)));
        System.out.println(Arrays.toString(constructTheLexicographicallyLargestValidSequence(5)));

    }

    // This method returns the lexicographically largest sequence
    // that fulfills all the conditions of the challenge specifications.
    public static int[] constructTheLexicographicallyLargestValidSequence(int n) {

        // Since there are two copies of every number between 2 and n, and a single
        // copy of 1, we set the size of int[] sequence to reflect those constraints.
        int[] sequence = new int[1 + 2 * (n - 1)];

        // We then create a LinkedList that contains every number between 1 to n.
        // The purpose of this LinkedList is for use in our later recursive function.
        LinkedList<Integer> numbers = new LinkedList<>();

        for (int i = n; i > 0; i--) {
            numbers.add(i);
        }

        // We then call a helper method that will return the lexicographically largest sequence that
        // fulfills all the conditions of the challenge specifications, and return that sequence.
        return fillSequence(sequence, numbers);

    }

    // This method will fill in the actual values that lead to the lexicographically largest sequence.
    // It uses recursion to test all viable possibilities and returns the sequence as an int array.
    // int[] sequence will initially be an array of 0s that each represent an 'empty' space that has
    // not been filled yet. The recursive calls will gradually attempt filling the indexes with valid
    // values between 1 and n in order to create a valid sequence. LinkedList numbers will be used to
    // keep track of what numbers between 1 and n have been used so far for filling out the sequence.
    // Each recursive call will remove one value from the LinkedList to indicate that a number has
    // been used, and the recursion will continue until the LinkedList is finally empty which would
    // indicate that the sequence has been properly filled according to the challenge specifications.
    private static int[] fillSequence(int[] sequence, LinkedList<Integer> numbers) {

        // We will store the answer in int[] answerSequence.
        int[] answerSequence = new int[sequence.length];

        // We create a new LinkedList copy of numbers to avoid issues due to recursive calls.
        LinkedList<Integer> newNumbers = new LinkedList<>(numbers);

        // In each recursive call, we will look at one number at a time, and attempt to
        // place it somewhere (in two spots for numbers other than 1) in the sequence.
        int currentNumber = newNumbers.get(0);

        // We remove that number from the LinkedList so that the next recursive call
        // will not attempt to place it somewhere in the sequence again.
        newNumbers.remove(0);

        // If the LinkedList became empty as a result of the above removal,
        // then that means that 1 was removed as LinkedList numbers was specifically
        // instantiated in such a way that 1 would be placed at the very end of the list.
        if (newNumbers.isEmpty()) {

            // At this point, we will copy over the entire sequence to int[] answerSequence.
            // There should only be one index that has a value of 0 left. That index will be
            // instantiated with a value of 1 as that's the only space left.
            for (int i = 0; i < sequence.length; i++) {

                answerSequence[i] = sequence[i];
                if (sequence[i] == 0) { answerSequence[i] = 1; }

            }

            // We then return int[] answerSequence as it is a properly filled sequence
            // that follows the conditions outlined by the challenge specifications.
            return answerSequence;

        }

        // Otherwise, we will look at every valid position that the first instance of the number
        // could be placed in. Since the challenge specifications clearly specified that the second
        // instance of some number n must be placed n indexes away from it, we only loop through
        // indexes that would not cause the second instance of the number to be placed out of bounds.
        for (int i = 0; i < sequence.length - currentNumber; i++) {

            // If the value at an index is 0, that means the index
            // is 'empty' and we can place some value into it.
            if (sequence[i] == 0) {

                // We then check if the index that we'd have to place the second instance
                // of the number in is 'empty' or not. If it is not, we skip the current loop.
                if (sequence[i+currentNumber] != 0) { continue; }

                // If both indexes are empty, then we create a new int array of the sequence
                // so that we can pass it as an argument into a recursive call without issues.
                int[] newSequence = sequence.clone();

                // We then place that number into the valid position at both indexes.
                newSequence[i] = currentNumber;
                newSequence[i+currentNumber] = currentNumber;

                // We then make a recursive call that will take this new sequence and reduced list.
                // The recursive call will attempt to fill out the rest of the 'empty' indexes in
                // the sequence, while going through the LinkedList until all values have been
                // accounted for and there is no number left within said LinkedList.
                newSequence = fillSequence(newSequence, newNumbers);

                // Since the recursive call will not always return a sequence that has filled out
                // all the indexes with no 'empty' indexes remaining, we have a helper method that
                // will check whether this sequence fulfills all the conditions that were outlined
                // by the challenge specifications to be a lexicographically valid sequence or not.
                // If it is a valid sequence, then we call another helper method will compare the
                // current sequence with any previous sequence already obtained earlier in this
                // same loop (which would be stored in int[] answerSequence). This comparison
                // helper method will help to check which sequence is lexicographically larger,
                // and will return the sequence that is greater than the other (or just return
                // either of them if they happen to be equal, which shouldn't ever happen).
                // int[] answerSequence will then be set to the value of the greater sequence.
                if (checkSequence(newSequence)) {
                    answerSequence = compareSequence(answerSequence, newSequence);
                }

            }



        }

        // We then return answerSequence once we have performed all the loops.
        return answerSequence;

    }

    // A helper method that checks whether a sequence is valid as per challenge specifictions.
    private static boolean checkSequence(int[] sequence) {

        // We will have a LinkedList that will be used to make sure that there's two
        // of every number, except for 1 which there should only be one of.
        LinkedList<Integer> sequenceList = new LinkedList<>();

        // We will then loop through the sequence.
        for (int i = 0; i < sequence.length; i++) {

            // If at any point there is a 0, it means there is
            // an 'empty' index and the sequence is thus invalid.
            if (sequence[i] == 0) { return false; }

            // Otherwise we add the number to the LinkedList.
            sequenceList.add(sequence[i]);

            // If the number is not 1, then there should be two of that number in the sequence.
            // For every number n, the second instance of that number should be n indexes away.
            if (sequence[i] != 1) {

                // We will first check if there is a second instance of that number
                // further ahead in the same sequence in the appropriate position.
                // If yes, we set boolean forward to true.
                boolean forward = false;
                if (i + sequence[i] < sequence.length &&  sequence[i + sequence[i]] == sequence[i]) {
                    forward = true;
                }

                // We will then check if there is a second instance of that number
                // somewhere behind in the same sequence in the appropriate position.
                // If yes, we set boolean backward to true.
                boolean backward = false;
                if (i - sequence[i] >= 0 &&  sequence[i - sequence[i]] == sequence[i]) {
                    backward = true;
                }

                // If neither boolean turns out to be true, we return false.
                if (!forward && !backward) { return false; }

            }

        }

        // Once all numbers have been added to the LinkedList, we then apply the sort method to it.
        // This should put the numbers in the LinkedList in ascending order.
        Collections.sort(sequenceList);

        // The first value should absolutely be 1. If it isn't, return false.
        if (sequenceList.get(0) != 1) { return false; }

        // For every odd-positioned index (index 1, index 3, etc...) we check if the value
        // at that index, and the index immediately following it are the same or not.
        // If they are not the same, return false, as the LinkedList has been sorted and should
        // now have repeats of every number next to each other except for 1.
        for (int i = 1; i < sequenceList.size(); i++) {

            if (i % 2 == 1 && sequenceList.get(i) != sequenceList.get(i + 1)) {
                return false;
            }

        }

        // If the sequence passed all the checks so far, it is a valid sequence, so we return true.
        return true;

    }

    // This helper method helps to check and compare which sequence is lexicographically larger.
    private static int[] compareSequence(int[] sequence1, int[] sequence2) {

        // We go through every index sequentially, and we return the sequence that has
        // the first index with a larger value than the other sequence in the same index.
        for (int i = 0; i < sequence1.length; i++) {

            if (sequence1[i] > sequence2[i]) { return sequence1; }
            if (sequence1[i] < sequence2[i]) { return sequence2; }

        }

        // If both sequences are equal, we simply return int[] sequence1 as there is no difference.
        return sequence1;

    }

}
