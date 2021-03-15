import java.util.Random;

public class SortingTests {
    
    public static void main ( String[] args ) {

        // Create data to be sorted
        int arraySize = 100;
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

            arrayCopy = shuffledArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

            arrayCopy = descendingArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

            // Parallel
            System.out.println("-\tParallel:");

            arrayCopy = ascendingArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");

            arrayCopy = shuffledArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

            arrayCopy = descendingArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

        // Quick sort

        // Bubble sort
        System.out.println("[Bubble Sort]");
            System.out.println("-\tSequential:");

            arrayCopy = ascendingArray.clone();
            startTime = System.currentTimeMillis();
            BubbleSort.bubbleSort(arrayCopy);
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");

            arrayCopy = shuffledArray.clone();
            startTime = System.currentTimeMillis();
            BubbleSort.bubbleSort(arrayCopy);
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

            arrayCopy = descendingArray.clone();
            startTime = System.currentTimeMillis();
            BubbleSort.bubbleSort(arrayCopy);
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

            // Parallel
            System.out.println("-\tParallel:");

            arrayCopy = ascendingArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted ascending array in\t" + (endTime - startTime) + " ms");

            arrayCopy = shuffledArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted shuffled array in\t" + (endTime - startTime) + " ms");

            arrayCopy = descendingArray.clone();
            startTime = System.currentTimeMillis();
            // sort the array
            endTime = System.currentTimeMillis();
            System.out.println("\tSorted descending array in\t" + (endTime - startTime) + " ms");

        // Insertion sort

        // Selection sort

    }
}
