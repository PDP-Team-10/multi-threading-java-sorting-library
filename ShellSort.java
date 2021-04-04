import java.util.concurrent.*;

public class ShellSort extends Thread {

    private int[] arr;
    private int gap;
    private int length;

    public ShellSort(int[] arr, int gap) { 
        this.arr = arr;
        this.gap = gap;
        this.length = arr.length;
    }

    @Override
    public void run() { gappedInsertionSort();}

    // Sequential insertion sort implementation
    public static void insertionSort(int arr[]) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int k = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > k) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = k;
        }
    }

    // Sequential shell sort implementation
    public static void shellSort(int[] arr) {
        int n = arr.length;
        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j = i;  
                while (j >= gap && arr[j - gap] > temp) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }

    // The main event: concurrent shell sort!
    public static void concurrentShellSort(int[] arr) throws InterruptedException{
        int n = arr.length;
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int gap = n/2; gap > 0; gap /= 2) {
            pool.execute(new ShellSort(arr, gap));
        }

        pool.shutdown();

        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        }
    }

    private void gappedInsertionSort() {
        for (int i = gap; i < length; i++) {
            int temp = arr[i];
            int j = i;  
            while (j >= gap && arr[j - gap] > temp) {
                arr[j] = arr[j - gap];
                j -= gap;
            }
            arr[j] = temp;
        }
    }

    private static void printArr(int[] arr) {
        for (Integer e : arr) System.out.print(e + " ");
        System.out.println();
    }

    /*
    public static void main (String[] args) throws InterruptedException{
        int[] test = {2,1,4,3,6,5,8,7,10,9,12,11,14,13,16,15};
        ShellSort.concurrentShellSort(test);
        ShellSort.printArr(test);

    } */
}
