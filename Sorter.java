import java.util.concurrent.*;
import java.util.*;

public class Sorter {
    // Call sequential shell sort
    public static <T extends Comparable<? super T>> void shellSort(List<T> list) { ShellSort.shellSort(list); }

    // Call concurrent shell sort
    public static <T extends Comparable<? super T>> void concurrentShellSort(List<T> list) throws InterruptedException{ ShellSort.concurrentShellSort(list); }

    // Check if list is sorted
    public static <T extends Comparable<? super T>> boolean isSorted(List<T> list)
    {       
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i-1).compareTo(list.get(i)) > 0) return false;
        }
        return true;
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

        Sorter.concurrentShellSort(test);
       // Sorter.shellSort(test);
        //Sorter.shellSort(test);
        //System.out.println(test);
        System.out.println(Sorter.isSorted(test));

    } */
}
