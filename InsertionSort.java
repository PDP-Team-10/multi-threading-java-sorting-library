public class InsertionSort extends Thread
{

    private int[] arr;
    private int start, end;

    public InsertionSort(int[] arr, int start, int end)
    {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run()
    {   
        insertionSortHelper(arr, start, end);
    }

    public static void insertionSort(int arr[])
    {
        int n = arr.length;
        for (int i = 1; i < n; i++)
        {
            int k = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > k)
            {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = k;
        }
    }

    public static void insertionSortHelper(int[] arr, int start, int end)
    {
        for (int i = start + 1; i < end; i++)
        {
            int k = arr[i];
            int j = i - 1;

            while (j >= start && arr[j] > k)
            {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = k;
        }
    }

    public static void concurrentInsertionSort(int[] arr, int start, int end) throws InterruptedException
    {
        //arr = {0,0,0,0,0,0,0,0,0,0};
        int processors = Runtime.getRuntime().availableProcessors();
        int workload = (arr.length + processors - 1) / processors;
        for (int i = 0; i < processors; i++) 
        {
            int low = i * workload;
            int high = Math.min(start + workload, arr.length);
            InsertionSort is = new InsertionSort(arr, low, high);
            is.start();
        }
        // Next step: join remaining threads before merging
        // Change implementatino to use threadpool
    }
}
