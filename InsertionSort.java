import java.util.ArrayList;
import java.util.Random;
public class InsertionSort extends Thread {
    private int[] arr;
    private int start, end;

    public InsertionSort(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    public int[] getArr() { return arr;}

    private static void printArr(int[] arr) {
        for (Integer e : arr) System.out.print(e + " ");
        System.out.println();
    }

    @Override
    public void run() { insertionSortHelper();}

    public static void concurrentInsertionSort(int[] arr) throws InterruptedException{
       // int processors = Math.min(Runtime.getRuntime().availableProcessors(), arr.length);
        int processors = Runtime.getRuntime().availableProcessors();
        int workload = (arr.length + processors - 1) / processors;
        //InsertionSort[] pool = new InsertionSort[processors];
        ArrayList<InsertionSort> pool = new ArrayList<InsertionSort>();

        for (int i = 0; i < processors; i++) {
            int low = i * workload;
            int high = Math.min(low + workload, arr.length);

            if (low > high) break;

            pool.add(i, new InsertionSort(arr, low, high));
            pool.get(i).start();
           // pool.get(i).join();
        }

        // *Improve this by using a proper thread pool
        for (InsertionSort is : pool) is.join();
        

        // Testing parallel insertion sort
        //for (InsertionSort is : pool) printArr(is.getArr()); 

        /* Consider using a minheap to merge the arrays */
    }

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

    private void insertionSortHelper() {
        
        for (int i = start + 1; i < end; i++) {
            int k = arr[i];
            int j = i - 1;

            while (j >= start && arr[j] > k) {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = k;
        }
    }

    /*public static void main(String[] args) throws InterruptedException{
        // int[] test = {2,1,4,3,6,5,8,7,10,9,12,11,14,13,16,15};
        int[] test = new int[50];
        Random rand = new Random();
        for (int i = 0; i < test.length; i++) test[i] = rand.nextInt(50);
        printArr(test);
        System.out.println();
        InsertionSort.concurrentInsertionSort(test);
    }*/
}
