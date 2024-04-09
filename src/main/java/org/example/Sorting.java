package org.example;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

class Sorting {

  /**
   * @return returns the array array that is sorted from least to greatest.
   * @param array array containing integers to sort
   * @precondition none
   * @postcondition array in the parameter will be sorted
   */
  public static int[] selectionSort(int[] array) {
    for (int i = 0; i < array.length; i++) {
      int minIndex = i;
      for (int j = i + 1; j < array.length; j++) {
        if (array[j] < array[minIndex])
          minIndex = j;
      }
      int temp = array[i];
      array[i] = array[minIndex];
      array[minIndex] = temp;
    }
    return array;
  }

  /**
   * @return returns the array array that is sorted from least to greatest.
   * @param array array containing integers to sort
   * @precondition none
   * @postcondition array in the parameter will be sorted
   */
  public static int[] insertionSort(int[] array) {
    int j; // number of items sorted so far
    int i; // starting point of unsorted array
    int key; // the item to be inserted
    for (j = 1; j < array.length; j++) // start with 1 since the first item is sorted
    {
      key = array[j];// find item to be sorted
      for (i = j - 1; ((i >= 0) && (array[i] > key)); i--) // move smaller values DOWN from key to where key should go
      {
        // move i down one
        array[i + 1] = array[i];
      }
      array[i + 1] = key; // Put "key" in position

    }
    return array;
  }

  public static int[] mergeSort(int[] array) {
    int[] left;
    int[] right;

    //base case
    if(array.length == 1) return array;

    //Split the array
    left = new int[array.length /2];
    right = new int[array.length - left.length];

    //copy Lower half to left
    System.arraycopy(array , 0 , left , 0 , left.length);

    //copy Upper half to right
    System.arraycopy(array , left.length , right , 0 , right.length);


    //Sort left and right
    mergeSort(left);
    mergeSort(right);

    /*System.out.println("Left:");
    for(int x : left) System.out.print(x + " ");
    System.out.println("\nRight:");
    for(int x : right) System.out.print(x + " ");
    System.out.println();*/

    //Merge Arrays
    int l = 0;
    int r = 0;

    for(int i = 0; i < array.length; i++){
      if (l >= left.length) {
        array[i] = right[r];
        r++;
      } else if (r >= right.length) {
        array[i] = left[l];
        l++;
      } else if (left[l] < right[r]) {
        array[i] = left[l];
        l++;
      } else {
        array[i] = right [r];
        r++;
      }
    }
    return array;
  }

  public static void main(String[] args) {
    //final long runs = 1000;
    final long runs = 1000000;
    int[] temp = { 36, 24, 10, 6, 12 };
    // selectionSort(temp);
    // insertionSort(temp);
    mergeSort(temp);
    for (int element : temp)
      System.out.println(element);

    // No reason to change anything below this line, but feel free to browse
    System.out.format("Number of trials: %,d\n", runs);
    // unsorted array to search
    final int size = 1000;
    int[] unsortedArray = new int[size];
    for (int i = 0; i < size; i++)
      unsortedArray[i] = (int) ((Math.random() * 99) + 1);
    int[] unsortedArray_copy = new int[size];
    // for timing
    long startTime = 0;
    long endTime = 0;
    ArrayList<Long> selectionSortRunTimes = new ArrayList<>();
    ArrayList<Long> insertionSortRunTimes = new ArrayList<>();
    ArrayList<Long> mergeSortRunTimes = new ArrayList<>();

    long counter = 0;
    int average;

    for (int i = 0; i < runs; i++) {
      // copy first, don't want to include copy in the time
        System.arraycopy(unsortedArray , 0 , unsortedArray_copy , 0 , size);
      startTime = System.nanoTime();
      selectionSort(unsortedArray_copy);
      endTime = System.nanoTime();
      selectionSortRunTimes.add((endTime - startTime));
    }
      for (Long value : selectionSortRunTimes) {
      counter += value;
    }
    average = (int) (counter / runs);
    System.out.println("\tSelection Sort average runtime: " + average + " nanoseconds");

    for (int i = 0; i < runs; i++) {
      // copy first, don't want to include copy in the time
        System.arraycopy(unsortedArray , 0 , unsortedArray_copy , 0 , size);
      startTime = System.nanoTime();
      insertionSort(unsortedArray_copy);
      endTime = System.nanoTime();
      insertionSortRunTimes.add((endTime - startTime));
    }
    counter = 0;
    for (Long value : insertionSortRunTimes) {
      counter += value;
    }
    average = (int) (counter / runs);
    System.out.println("\tInsertion Sort average runtime: " + average + " nanoseconds");

    for (int i = 0; i < runs; i++) {
      // copy first, don't want to include copy in the time
        System.arraycopy(unsortedArray , 0 , unsortedArray_copy , 0 , size);
      startTime = System.nanoTime();
      mergeSort(unsortedArray_copy);
      endTime = System.nanoTime();
      mergeSortRunTimes.add((endTime - startTime));
    }
    counter = 0;
    for (Long value : mergeSortRunTimes) {
      counter += value;
    }
    average = (int) (counter / runs);
    System.out.println("\tMerge Sort average runtime:     " + average + " nanoseconds");

    // Write out the arrays to a csv for later analysis
    String fileName = "runtimessorting.csv";
    try (FileWriter writer = new FileWriter(fileName); BufferedWriter csv = new BufferedWriter(writer)) {
      csv.append("\"Selection Sort\",\"Insertion Sort\"\n");
      for (int i = 0; i < runs; i++) {
        csv.append(String.valueOf(selectionSortRunTimes.get(i))).append(",");
        csv.append(String.valueOf(insertionSortRunTimes.get(i)));
        csv.append(String.valueOf(mergeSortRunTimes.get(i)));

        csv.append("\n");
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }
}