import java.util.concurrent.*;
import java.util.*;

public class ShellSort extends Thread {

    private List<Integer> arr;

    public ShellSort(ArrayList<Integer> arr) { 
        this.arr = arr;
    }

    @Override
    public void run() { concurrentShellSortHelper();}

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

    private void concurrentShellSortHelper() {
        int n = arr.size();
        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = arr.get(i);
                int j = i;  
                while (j >= gap && arr.get(j - gap) > temp) {
                    arr.set(j, arr.get(j - gap));
                    j -= gap;
                }
                arr.set(j, temp);
            }
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

    // The main event: concurrent shell sort
    public static void concurrentShellSort(List<Integer> arr) throws InterruptedException{
        //int n = arr.size();
        final int numThreads = Runtime.getRuntime().availableProcessors();
        //ExecutorService pool = Executors.newCachedThreadPool();
       // List<Callable<Object>> calls = new ArrayList<Callable<Object>>();
        ExecutorService pool = Executors.newFixedThreadPool(numThreads); //Runtime.getRuntime().availableProcessors()
        List<ArrayList<Integer>> partitions = new ArrayList<ArrayList<Integer>>(numThreads);
        int partition = (arr.size() + numThreads - 1) / numThreads;
        for (int i = 0; i < numThreads; i++) {
            int start = i * partition;
            int end = Math.min(start + partition, arr.size());
            partitions.add(new ArrayList<Integer>(arr.subList(start,end)));
            pool.execute(new ShellSort(partitions.get(i)));
            //calls.add(Executors.callable(new ShellSort(partitions.get(i))));
        }

       /* for (int gap = n/2; gap > 0; gap /= 2) {
           // pool.execute(new ShellSort(arr, gap, length));
            for (int i = 0; i < gap; i++)
                calls.add(Executors.callable(new ShellSort(arr, gap, n - i)));
               // pool.execute(new ShellSort(arr, gap, n - i));
            List<Future<Object>> futures = pool.invokeAll(calls);
            //calls.clear();
        }*/
        

        pool.shutdown();

        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
        } finally {
            ShellSort.merge(arr, partitions);
        }
    }

    private static void merge(List<Integer> arr, List<ArrayList<Integer>> partitions) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        for (ArrayList<Integer> p : partitions) {
            pq.add(new Node(p,0));
        }
        int i = 0;
        //List<Integer> result = new ArrayList<Integer>(arr.size());

        while (!pq.isEmpty()) {
            Node n = pq.poll();
            arr.set(i, n.arr.get(n.index));
            i++;

            if (n.hasNext()) {
                pq.add(new Node(n.arr, n.index+1));
            }
        }
    }

    public static void printArr(int[] arr) {
        for (Integer e : arr) System.out.print(e + " ");
        System.out.println();
    }

    public static boolean isSorted(List<Integer> list)
    {
        boolean sorted = true;        
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i)) > 0) sorted = false;
        }

        return sorted;
    }


    
    public static void main (String[] args) throws InterruptedException{
        //int[] test = {2,1,4,3,6,5,8,7,10,9,12,11,14,13,16,15};
        System.out.println("Testing...");
        final int testNum = 5000;
        ArrayList<Integer> test = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < testNum; i++) {
            test.add(rand.nextInt(testNum));
        }
    
        ShellSort.concurrentShellSort(test);
        //ShellSort.shellSort(test);
       // System.out.println(test);
        System.out.println(ShellSort.isSorted(test));

    } 
}

// Source: Interviewdojo.com
class Node implements Comparable<Node> {
    List<Integer> arr;
    int index;

    public Node(List<Integer> arr, int index) {
        this.arr = arr;
        this.index = index;
    }

    public boolean hasNext() {
        return this.index < this.arr.size() - 1;
    }

    @Override
    public int compareTo(Node o) {
        return arr.get(index) - o.arr.get(o.index);//arr[index] - o.arr[o.index];
    }
}
