import java.util.*;
import java.io.FileWriter;  
import java.io.IOException;

public class SortingTests {
    
    static void testShellSort(int arraySize) throws IOException, InterruptedException{
        ArrayList<Integer> ascendingArray = new ArrayList<Integer>();
        ArrayList<Integer> descendingArray = new ArrayList<Integer>();
        ArrayList<Integer> shuffledArray = new ArrayList<Integer>();

        FileWriter sequentialWriter = new FileWriter("sequential-shell-sort-results.txt");
        FileWriter concurrentWriter = new FileWriter("concurrent-shell-sort-results.txt");
        sequentialWriter.write("size\tascending(MS)\tdescending(MS)\tshuffled(MS)\n");
        concurrentWriter.write("size\tascending(MS)\tdescending(MS)\tshuffled(MS)\n");

        // Record start and end times
        long startTime;
        long endTime;
        long ascendingTime;
        long descendingTime;
        long shuffledTime;
        
        Random rand = new Random();

        for (int iter = 0; iter < 10; iter++) {
            for (int i = 0; i < arraySize * (iter + 1); i++) 
                ascendingArray.add(i);
        
            for (int i = 0; i < arraySize * (iter + 1); i ++) 
                shuffledArray.add(rand.nextInt(arraySize * (iter + 1)));
            
            
            for (int i = 0; i < arraySize * (iter + 1); i++) 
                descendingArray.add(arraySize * (iter + 1) - i - 1);
            
        
            System.out.println("Testing sequential shell sort...");

            // Test sequential
            ArrayList arrayCopy = (ArrayList) ascendingArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.shellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            ascendingTime = endTime - startTime;

            arrayCopy = (ArrayList) descendingArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.shellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            descendingTime = endTime - startTime;

            arrayCopy = (ArrayList) shuffledArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.shellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            shuffledTime = endTime - startTime;

            sequentialWriter.write(arraySize + "\t" + ascendingTime + "\t" + descendingTime + "\t" + shuffledTime + "\n");
            System.out.println("testing concurrent shell sort...");

            // Test concurrent
            arrayCopy = (ArrayList) ascendingArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.concurrentShellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            ascendingTime = endTime - startTime;

            arrayCopy = (ArrayList) descendingArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.concurrentShellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            descendingTime = endTime - startTime;

            arrayCopy = (ArrayList) shuffledArray.clone();
            startTime = System.currentTimeMillis();
            Sorter.concurrentShellSort(arrayCopy);
            endTime = System.currentTimeMillis();
            shuffledTime = endTime - startTime;

            concurrentWriter.write(arraySize + "\t" + ascendingTime + "\t" + descendingTime + "\t" + shuffledTime + "\n");

            ascendingArray.clear();
            descendingArray.clear();
            shuffledArray.clear();
            

        }    
        sequentialWriter.close();
        concurrentWriter.close();
        System.out.println("Results written to files.");
    }
    public static void main ( String[] args ) throws IOException, InterruptedException{

        // Create data to be sorted
        int arraySize = (int)300;  
        
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Enter list size (default 300)");
            arraySize = in.nextInt();
        } while (arraySize < 0);

        
        
        System.out.println("Comparison of execution time for sequential and multi-threaded sorting algorithms");
        testShellSort(arraySize);
        in.close();
/*        
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
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(arrayCopy, 0, arrayCopy.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        QuickSort.quickSort(arrayCopy, 0, arrayCopy.length-1);
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

        // Parallel
        System.out.println("-\tParallel:");

        arrayCopy = ascendingArray.clone();
        startTime = System.currentTimeMillis();
        try{
            QuickSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(ascendingArray, arrayCopy);

        arrayCopy = shuffledArray.clone();
        startTime = System.currentTimeMillis();
        try{
            QuickSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        checkSortedList(shuffledArray, arrayCopy);

        arrayCopy = descendingArray.clone();
        startTime = System.currentTimeMillis();
        try{
            QuickSort.concurrentSort(arrayCopy);
        }catch (InterruptedException e){
            System.err.println(e);
        }
        endTime = System.currentTimeMillis();
        System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        checkSortedList(descendingArray, arrayCopy);

       
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
        // System.out.println("[Bucket Sort]");
        // System.out.println("-\tSequential:");
        // BucketSort b = new BucketSort();

        // arrayCopy = ascendingArray.clone();
        // startTime = System.currentTimeMillis();
        // b.sortSequential(arrayCopy);
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(ascendingArray, arrayCopy);

        // arrayCopy = shuffledArray.clone();
        // startTime = System.currentTimeMillis();
        // b.sortSequential(arrayCopy);
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(shuffledArray, arrayCopy);

        // arrayCopy = descendingArray.clone();
        // startTime = System.currentTimeMillis();
        // b.sortSequential(arrayCopy);
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(descendingArray, arrayCopy);

        // // Parallel
        // System.out.println("-\tParallel:");

        // arrayCopy = ascendingArray.clone();
        // startTime = System.currentTimeMillis();
        // // sort the array
        // try
        // {
        //     b.sort(arrayCopy);
        // }
        // catch (InterruptedException e)
        // {
        //     System.err.println(e);
        // }
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(ascendingArray, arrayCopy);

        // arrayCopy = shuffledArray.clone();
        // startTime = System.currentTimeMillis();
        // // sort the array
        // try
        // {
        //     b.sort(arrayCopy);
        // }
        // catch (InterruptedException e)
        // {
        //     System.err.println(e);
        // }
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(shuffledArray, arrayCopy);

        // arrayCopy = descendingArray.clone();
        // startTime = System.currentTimeMillis();
        // // sort the array
        // try
        // {
        //     b.sort(arrayCopy);
        // }
        // catch (InterruptedException e)
        // {
        //     System.err.println(e);
        // }
        // endTime = System.currentTimeMillis();
        // System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");
        // checkSortedList(descendingArray, arrayCopy);

        */
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
