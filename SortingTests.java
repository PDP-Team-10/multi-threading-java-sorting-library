import java.util.Arrays;
import java.util.Random;

public class SortingTests {
    
    public static void main ( String[] args ) {

        // Create data to be sorted
        int arraySize = (int)300;    
        if (args.length > 0) {
            try {
                arraySize = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Could not parse int from \"" + args[0] + "\"");
                return;
            }
        }
        int[] ascendingArray = new int[arraySize];
        int[] shuffledArray = new int[arraySize];
        int[] descendingArray = new int[arraySize];
        
        for (int i = 0; i < arraySize; i++) {
            ascendingArray[i] = i;
        }
        
        Random rand = new Random();
        for (int i = 0; i < arraySize; i ++) {
            shuffledArray[i] = rand.nextInt(arraySize);
        }
        
        for (int i = 0; i < arraySize; i++) {
            descendingArray[i] = arraySize - i - 1;
        }
        
        // Record start and end times
        long startTime;
        long endTime;
        
        int[] arrayCopy;
        
        System.out.println("Comparison of execution time for sequential and multi-threaded sorting algorithms");
        
        // Merge sort
        System.out.println("[ Merge sort ]");
        // Sequential
        System.out.println("-\tSequential:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        MergeSort.sort(arrayCopy, 0, arrayCopy.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        MergeSort.sort(arrayCopy, 0, arrayCopy.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        MergeSort.sort(arrayCopy, 0, arrayCopy.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Parallel
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        try{
            MergeSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        try{
            MergeSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        try{
            MergeSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Quick sort
        System.out.println("[ Quick sort ]");
        // Sequential
        System.out.println("-\tSequential:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(arrayCopy, 0, arrayCopy.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(arrayCopy, 0, arrayCopy.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(arrayCopy, 0, arrayCopy.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

        // Parallel
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        //sort it
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        //sort it
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        //sort it
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

       
        // Bubble sort
        System.out.println("[Bubble Sort]");
        System.out.println("-\tSequential:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.bubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.bubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.bubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Parallel Bubble
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.concurrentBubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.concurrentBubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        BubbleSort.concurrentBubbleSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        
        // Insertion sort

        // Selection sort
        System.out.println("[Selection Sort]");
        System.out.println("-\tSequential:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.selectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.selectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.selectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Parallel
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.concurrentSelectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.concurrentSelectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        SelectionSort.concurrentSelectionSort(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Bucket sort
        System.out.println("[Bucket Sort]");
        System.out.println("-\tSequential:");
        BucketSort b = new BucketSort();

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        b.sortSequential(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        b.sortSequential(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        b.sortSequential(arrayCopy);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Parallel
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        // sort the array
        try
        {
            b.sort(arrayCopy);
        }
        catch (InterruptedException e)
        {
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        // sort the array
        try
        {
            b.sort(arrayCopy);
        }
        catch (InterruptedException e)
        {
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        // sort the array
        try
        {
            b.sort(arrayCopy);
        }
        catch (InterruptedException e)
        {
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        
    }

    public static void checkSortedList(int[] original, int[] candidate) {
        if (original.length != candidate.length) {
            System.err.println("Error: length of resulting array differs from original array: ");
            for (int i : candidate) {
                System.err.print(i + " ");
            }
            System.err.println();
            return;
        }

        int[] model = original.clone();
        Arrays.sort(model);

        // Compare the two arrays
        for (int i = 0; i < model.length; i++) {
            if (model[i] != candidate[i]) {
                System.err.println("Error: resulting array differs from result of Arrays.sort(): ");
                System.err.print("Expected: ");
                for (int j : model) {
                    System.err.print(j + " ");
                }
                System.err.println();
                System.err.print("Received: ");
                for (int j : candidate) {
                    System.err.print(j + " ");
                }
                System.err.println();
                return;
            }
        }
    }
}
