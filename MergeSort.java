
/* Java program for Merge Sort */
/* sequential code is contributed by Rajat Mishra */
/* on geeksforgeeks */
public class MergeSort extends Thread
{
    private int l, r;
    private int[] arr;
    
    public MergeSort(int[] arr, int l, int r){
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
    static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
 
    // Main function that sorts arr[l..r] using
    // merge()
    static void sort(int arr[], int l, int r)
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
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
        System.out.println();
    }


    static void concurrentSort(int arr[]) throws InterruptedException{
        MergeSort[] threads = new MergeSort[4];
        int threadSize = arr.length / 4;
        for (int i = 0; i < 4; i++){
            int low = i * threadSize;
            int high = (i +1)* threadSize - 1;
            if (high > arr.length)
                high = arr.length;

            threads[i] = new MergeSort(arr, low, high);
            threads[i].start();
        }

        for (int i = 0; i < 4; i++){
            threads[i].join();
        }

        int low = 0;
        int mid = threadSize;
        int high = 2 * threadSize - 1;
        merge(arr, low, mid, high);

        low = 2 *threadSize;
        mid = 3 * threadSize;
        high = arr.length - 1;
        merge(arr, low, mid, high);

        merge(arr, 0, low, high);

        
    }
    
    
}


