import java.util.ArrayList;
import java.util.List;

/* Java program for Merge Sort */
/* sequential code is contributed by Rajat Mishra */
/* on geeksforgeeks */
public class MergeSort<T extends Comparable<? super T>> extends Thread
{
    private int l, r;
    private List<T> arr;
    
    public MergeSort(List<T> arr, int l, int r){
        this.arr = arr;
        this.l = l;
        this.r = r;
    }
    
    @Override
    public void run(){
        sort(arr, l, r);
    }
    
    // Merges two subarrays of arr[].
    // First subarray is arr[l..m]
    // Second subarray is arr[m+1..r]
    static <T extends Comparable<? super T>> void merge(List<T> arr, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        ArrayList<T> L =  new ArrayList<T>();
        ArrayList<T> R =  new ArrayList<T>();
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L.add(i, arr.get(l+i));
        for (int j = 0; j < n2; ++j)
            L.add(j, arr.get(m + 1 + j));
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L.get(i).compareTo(R.get(j)) <= 0) {
                arr.set(k, L.get(i));
                i++;
            }
            else {
                arr.set(k, R.get(j));
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr.set(k, L.get(i));
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr.set(k, R.get(j));
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    static <T extends Comparable<? super T>> void sort(List<T> arr, int l, int r)
    {
        if (l < r) {
            // Find the middle point
            int m =l+ (r-l)/2;
 
            // Sort first and second halves
            sort(arr, l, m);
            sort(arr, m + 1, r);
 
            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }
 
    /* A utility function to print array of size n */
    static <T extends Comparable<? super T>> void printArray(List<T> arr)
    {
        int n = arr.size();
        for (int i = 0; i < n; ++i)
            System.out.print(arr.get(i) + " ");
        System.out.println();
    }


    static <T extends Comparable<? super T>>void concurrentSort(List<T> arr) throws InterruptedException{
        MergeSort[] threads = new MergeSort[4];
        int threadSize = arr.size() / 4;
        for (int i = 0; i < 4; i++){
            int low = i * threadSize;
            int high = (i +1)* threadSize - 1;
            if (high > arr.size())
                high = arr.size();

            threads[i] = new MergeSort(arr, low, high);
            threads[i].start();
        }

        for (int i = 0; i < 4; i++){
            threads[i].join();
        }

        int low = 0;
        int mid = threadSize-1;
        int high = 2 * threadSize - 1;
        merge(arr, low, mid, high);

        low = 2 *threadSize;
        mid = 3 * threadSize-1;
        high = arr.size() - 1;
        merge(arr, low, mid, high);

        merge(arr, 0, low-1, high);

        
    }
    
    
}


