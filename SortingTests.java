import java.util.*; 
import java.io.IOException;

public class SortingTests {
    public static void main ( String[] args ) throws IOException, InterruptedException{

        // Create data to be sorted
        int arraySize = (int)500000;  
        
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Enter list size (default 50,000)");
            arraySize = in.nextInt();
        } while (arraySize < 0);
   
        System.out.println("Comparison of execution time for sequential and multi-threaded sorting algorithms\n");
        //ShellSort.testShellSort(arraySize);
        BucketSort.testBucketSort(arraySize);
        in.close();
    }
}
