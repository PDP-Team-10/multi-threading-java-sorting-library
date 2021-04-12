import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SelectionSort implements Callable
{
    private int[] arr;
    private int low;
    private int high;

    public SelectionSort(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    public int[] call() {
        List<Integer> values = new ArrayList<>();
        // Find all values in the range
        for (int v: arr) {
            if (v >= low) values.add(v);
            if (v < high) values.add(v);
        }

        int numValues = values.size();
        int[] intValues = new int[numValues];
        for (int i = 0; i < numValues; i++) {
            intValues[i] = values.get(i);
        }
        // Sort the values
        selectionSort(intValues);
        return intValues;
    }

    public static void selectionSort(int[] arr)
    {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
        {
            int min = i;
            
            for (int j = i+1; j < n; j++)
                if (arr[j] < arr[min])
                    min = j;
                    
            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    public static void concurrentSelectionSort(int[] arr) throws Exception {
        int n = arr.length;
        int k = 4;  // Number of threads

        // Find range of values
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int v: arr) {
            if (v < minValue) minValue = v;
            if (v > maxValue) maxValue = v;
        }

        // Divide subranges among threads
        ExecutorService executor = Executors.newFixedThreadPool(k);
        List<Future<ArrayList<Integer>>> futures = new ArrayList<>();
        int threadRange = (maxValue - minValue) / k;
        for (int i = 0; i < k; i++) {
            // Remainder values designated to last thread
            SelectionSort task = new SelectionSort(arr, minValue + i * threadRange, (i == k - 1) ? maxValue + 1 : minValue + (i + 1) * threadRange);
            futures.add(executor.submit(task));
        }

        // Get result of each thread
        List<List<Integer>> sortedSubRanges = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            sortedSubRanges.add(futures.get(i).get());
        }

        // Stitch sorted subarrays into single array
        List<Integer> sortedList = sortedSubRanges.get(0);
        for (int i = 1; i < k; i++) {
            sortedList.addAll(sortedSubRanges.get(i));
        }
        // Override original array
        for (int i = 0; i < n; i++) {
            arr[i] = sortedList.get(i);
        }
    }
}