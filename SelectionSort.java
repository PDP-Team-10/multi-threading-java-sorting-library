import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SelectionSort implements Callable<ArrayList<Integer>>
{
    private List<Integer> list;
    private int low;
    private int high;

    public SelectionSort(List<Integer> list, int low, int high) {
        this.list = list;
        this.low = low;
        this.high = high;
    }

    public ArrayList<Integer> call() {
        ArrayList<Integer> values = new ArrayList<>();
        // Find all values in the range
        for (int v: list) {
            if (v >= low && v < high) values.add(v);
        }

        // Sort the values
        selectionSort(values);
        return values;
    }

    public static void selectionSort(List<Integer> list)
    {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
        {
            int min = i;
            
            for (int j = i+1; j < n; j++)
                if (list.get(j) < list.get(min))
                    min = j;
                    
            int temp = list.get(min);
            list.set(min, list.get(i));
            list.set(i, temp);
        }
    }

    public static void concurrentSelectionSort(List<Integer> list) {
        int n = list.size();
        int k = 4;  // Number of threads

        // Find range of values
        int minValue = list.get(0);
        int maxValue = list.get(0);
        for (int v: list) {
            if (v < minValue) minValue = v;
            if (v > maxValue) maxValue = v;
        }

        // Divide subranges among threads
        ExecutorService executor = Executors.newFixedThreadPool(k);
        List<Future<ArrayList<Integer>>> futures = new ArrayList<>();
        int threadRange = (maxValue - minValue) / k;
        for (int i = 0; i < k; i++) {
            // Remainder values designated to last thread
            SelectionSort task = new SelectionSort(list, minValue + i * threadRange, (i == k - 1) ? maxValue + 1 : minValue + (i + 1) * threadRange);
            futures.add(executor.submit(task));
        }
        executor.shutdown();

        // Get result of each thread
        List<ArrayList<Integer>> sortedSubRanges = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            try {
                sortedSubRanges.add(futures.get(i).get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // Stitch sorted subarrays into single array
        List<Integer> sortedList = sortedSubRanges.get(0);
        for (int i = 1; i < k; i++) {
            sortedList.addAll(sortedSubRanges.get(i));
        }
        // Override original array
        for (int i = 0; i < n; i++) {
            list.set(i, sortedList.get(i));
        }
    }
}