package org.example;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

class Searching {

    /**
     * @return returns the index of the element numToFind in array array.
     * -1 if element not found.
     * @param array array, sorted or not, containing integers
     * @param numToFind integer to find in array
     * @precondition none
     * @postcondition index of numToFind has been returned. -1 has been
     * returned if element not found.
     */
    public static int linearSearch(int[] array, int numToFind){
        for(int i = 0; i< array.length; i++)
          if(array[i]==numToFind) return i;

        return -1;
    }

    /**
     * @return returns the index of the element numToFind in array array.
     * -1 if element not found.
     * @param array array, sorted or not, containing integers
     * @param numToFind integer to find in array
     * @precondition none
     * @postcondition index of numToFind has been returned. -1 has been
     * returned if element not found.
     */
    public static int binarySearch(int[] array, int numToFind){
        int left = 0;
        int right = array.length-1;
        int middle = 0;

        while(left <= right) {
          middle = (left + right)/2;
          if(numToFind < array[middle])
            right = middle - 1;
          else if( numToFind > array[middle])
            left = middle + 1;
          else return middle;
        }


        return -1;
    }
    /**
     * @return returns the index of the element numToFind in array array.
     * -1 if element not found.
     * @param array array, sorted or not, containing integers
     * @param numToFind integer to find in array
     * @param start the starting point of the array
     * @param end the ending point of the array
     * @precondition none
     * @postcondition index of numToFind has been returned. -1 has been
     * returned if element not found.
     */
    public static int binarySearch(int[] array, int numToFind, int start, int end) {
        int middle = (start + end)/2;

        if(end < start) return -1;
        else if (numToFind < array[middle])
            return binarySearch(array, numToFind,start, middle-1);
        else if (numToFind > array[middle])
            return binarySearch(array, numToFind,middle + 1, end);
        else return middle;
    }

    public static void main(String[] args) {
        //final long runs = 1000;
        final long runs = 1000000;

      int[] testarray = {1,2,3,4,5,6,7,8};
      System.out.println(linearSearch(testarray, 7));
      System.out.println(binarySearch(testarray, 7));
      System.out.println(binarySearch(testarray, 7,0,testarray.length -1 ));
//No reason to change anything below this line, but feel free to browse


        System.out.format("Number of trials: %,d\n", runs);
        //unsorted array to search
        final int size = 100000;
        int[] unsortedArray = new int[size];
        for (int i = 0; i < size; i++)
            unsortedArray[i] = (int) ((Math.random() * 99) + 1);

        int[] sortedArray = new int[size];
        for (int i = 0; i < size; i++)
            sortedArray[i] = i;
        //for timing
        long startTime = 0;
        long endTime = 0;
        ArrayList<Long> linearSearchRunTimesSorted = new ArrayList<Long>();
        ArrayList<Long> binarySearchRunTimesSorted = new ArrayList<Long>();
        ArrayList<Long> RbinarySearchRunTimesSorted = new ArrayList<Long>();
        ArrayList<Long> linearSearchRunTimesUnsorted = new ArrayList<Long>();
        long counter = 0;
        int average = 0;

        for (int i = 0; i < runs; i++) {
            startTime = System.nanoTime();
            linearSearch(unsortedArray, size+1);
            endTime = System.nanoTime();
            linearSearchRunTimesUnsorted.add((endTime - startTime));
        }
        counter = 0;
        for (Long value : linearSearchRunTimesUnsorted) {
            counter += value;
        }
        average = (int) (counter / runs);
        System.out.println("\tLinear Search average runtime for unsorted array:     " + average + " nanoseconds");

        for (int i = 0; i < runs; i++) {
            startTime = System.nanoTime();
            linearSearch(sortedArray, size + 1);
            endTime = System.nanoTime();
            linearSearchRunTimesSorted.add((endTime - startTime));
        }
        counter = 0;
        for (Long value : linearSearchRunTimesSorted) {
            counter += value;
        }
        average = (int) (counter / runs);
        System.out.println("\tLinear Search average runtime for sorted array:       " + average + " nanoseconds");


        for (int i = 0; i < runs; i++) {
            startTime = System.nanoTime();
            binarySearch(sortedArray, size + 1);
            endTime = System.nanoTime();
            binarySearchRunTimesSorted.add((endTime - startTime));
        }

        counter = 0;
        for (Long value : binarySearchRunTimesSorted) {
            counter += value;
        }
        average = (int) (counter / runs);
        System.out.println("\tBinary Search average runtime for sorted array:       " + average + " nanoseconds");

        for (int i = 0; i < runs; i++) {
            startTime = System.nanoTime();
            binarySearch(sortedArray, size + 1,0,size-1);
            endTime = System.nanoTime();
            RbinarySearchRunTimesSorted.add((endTime - startTime));
        }

        counter = 0;
        for (Long value : RbinarySearchRunTimesSorted) {
            counter += value;
        }
        average = (int) (counter / runs);
        System.out.println("\tRecur Binary Search average runtime for sorted array: " + average + " nanoseconds");

        //Write out the arrays to a csv for later analysis
        String fileName = "runtimes.csv";
        try (FileWriter writer = new FileWriter(fileName);
             BufferedWriter csv = new BufferedWriter(writer)) {
            csv.append("\"Linear Search Sorted\",\"Linear Search Unsorted\",\"Binary Search Sorted\"\n");
            for (int i = 0; i < runs; i++) {
                csv.append(String.valueOf(linearSearchRunTimesSorted.get(i))).append(",");
                csv.append(String.valueOf(linearSearchRunTimesUnsorted.get(i))).append(",");
                csv.append(String.valueOf(binarySearchRunTimesSorted.get(i)));
                csv.append("\n");
csv.append(String.valueOf(RbinarySearchRunTimesSorted.get(i)));
                csv.append("\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}