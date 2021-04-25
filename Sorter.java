import java.util.concurrent.*;
import java.util.*;

public class Sorter {
    // Call sequential shell sort
    public static <T extends Comparable<? super T>> void shellSort(List<T> list) { ShellSort.shellSort(list); }

    // Call concurrent shell sort
    public static <T extends Comparable<? super T>> void concurrentShellSort(List<T> list) throws InterruptedException{ ShellSort.concurrentShellSort(list); }

    // Call sequential bucket sort
    public static void bucketSort(List<Integer> list) 
    {
        BucketSort b = new BucketSort();
        b.sortSequential(list);
    }

    // Call concurrent bucket sort
    public static void concurrentBucketSort(List<Integer> list) throws InterruptedException
    {
        BucketSort b = new BucketSort();
        b.sort(list);
    }

    // Check if list is sorted
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list)
    {       
        for (int i = 1; i < list.size(); i++) 
            if (list.get(i-1).compareTo(list.get(i)) > 0) return false;
        return true;
    }
}
