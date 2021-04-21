import java.util.concurrent.*;
import java.util.*;

public class ShellSort <T extends Comparable<? super T>> extends Thread {

    private List<T> arr;

    public ShellSort(List<T> arr) { 
        this.arr = arr;
    }

    @Override
    public void run() { concurrentShellSortHelper();}

    private void concurrentShellSortHelper() {
        int n = arr.size();
        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                T key = arr.get(i);
                int j = i;  
                while (j >= gap && arr.get(j - gap).compareTo(key) > 0) {
                    arr.set(j, arr.get(j - gap));
                    j -= gap;
                }
                arr.set(j, key);
            }
        }
    }

    // Sequential generic shell sort implementation
    public static <T extends Comparable<? super T>> void shellSort(List<T> list) {
        int n = list.size();
        for (int gap = n/2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                T key = list.get(i);
                int j = i;
                while (j >= gap && list.get(j - gap).compareTo(key) > 0) {
                    list.set(j, list.get(j - gap));
                    j -= gap;
                }
                list.set(j, key);
            }
        }
    }

    public static <T extends Comparable<? super T>> void concurrentShellSort(List<T> list) throws InterruptedException{
        final int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numThreads); 
        ArrayList<List<T>> partitions = new ArrayList<List<T>>(numThreads);
        int partitionSize = (list.size() + numThreads - 1) / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * partitionSize;
            int end = Math.min(start + partitionSize, list.size());
            partitions.add(new ArrayList<T>(list.subList(start, end)));
            pool.execute(new ShellSort(partitions.get(i)));
        }

        pool.shutdown();

        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) 
                pool.shutdownNow();     
        } catch (InterruptedException e) {
            pool.shutdownNow();
        } finally {
            ShellSort.merge(list, partitions);
        }
    }

    // Repurposed from Interviewdojo.com
    public static <T extends Comparable<? super T>> void merge(List<T> list, ArrayList<List<T>> partitions) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        for (List<T> p : partitions) //List<T> p : partitions
            pq.add(new Node(p, 0));
        
        int i = 0;

        while (!pq.isEmpty()) {
            Node n = pq.poll();
            list.set(i, (T) n.arr.get(n.index));
            i++;

            if (n.hasNext()) {
                pq.add(new Node(n.arr, n.index + 1));
            }
        }
    }

    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list)
    {
        boolean sorted = true;        
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i)) > 0) sorted = false;
        }

        return sorted;
    }


    
   /* public static void main (String[] args) throws InterruptedException{
        //int[] test = {2,1,4,3,6,5,8,7,10,9,12,11,14,13,16,15};
        System.out.println("Testing...");
        final int testNum = 5000000;
        ArrayList<Integer> test = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < testNum; i++) {
            test.add(rand.nextInt(testNum));//test.add(rand.nextInt(testNum));
        }

        ShellSort.concurrentShellSort(test);
       // ShellSort.shellSort(test);
        //ShellSort.shellSort(test);
        //System.out.println(test);
        System.out.println(ShellSort.isSorted(test));

    } */
}

// Source: Interviewdojo.com
class Node <T extends Comparable<? super T>> implements Comparable<Node> { //T extends Comparable<? super T>
    List<T> arr;
    int index;

    public Node(List<T> arr, int index) {
        this.arr = arr;
        this.index = index;
    }

    public boolean hasNext() {
        return this.index < this.arr.size() - 1;
    }

    @Override
    public int compareTo(Node o) {
        return arr.get(index).compareTo((T) o.arr.get(o.index));//arr[index] - o.arr[o.index];
    }
}
