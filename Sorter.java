import java.util.concurrent.*;
import java.util.*;

public class Sorter {
    // Call sequential shell sort
    public static <T extends Comparable<? super T>> void shellSort(List<T> list) { ShellSort.shellSort(list); }

    // Call concurrent shell sort
    public static <T extends Comparable<? super T>> void concurrentShellSort(List<T> list) throws InterruptedException{ ShellSort.concurrentShellSort(list); }

    // Call concurrent bubble sort
    public static <T extends Comparable<? super T>> void concurrentBubbleSort(List<T> list) { BubbleSort.concurrentBubbleSort(list); }

    // Check if list is sorted
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list)
    {       
        for (int i = 1; i < list.size(); i++) 
            if (list.get(i-1).compareTo(list.get(i)) > 0) return false;    
        return true;
    }

}
