import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.List;

class Sort <T extends Comparable<? super T>> implements Runnable
{   List<T> arr;
    int mid;
    boolean direction;
    public Sort(List<T> arr, int mid, boolean direction)
    {
        this.arr = arr;
        this.mid = mid;
        this.direction = direction;
    }

    @Override
    public void run()
    {
        BubbleSort.directionalBubbleSort(arr, mid, direction);
    }
}

public class BubbleSort 
{ 
    public static <T extends Comparable<? super T>> void bubbleSort(List<T> arr) 
    { 
        //System.out.println(Arrays.toString(arr));
        int n = arr.size(); 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr.get(j).compareTo(arr.get(j+1)) > 0) 
                { 
                    
                    T temp = arr.get(j); 
                    arr.set(j, arr.get(j+1));
                    arr.set(j+1, temp);
                } 
                //System.out.println(Arrays.toString(arr));
    }

    static <T extends Comparable<? super T>> directionalBubbleSort(List<T> arr, int mid, boolean direction) 
    { 
        int n = arr.size();
        if(direction){
            for (int i = 0; i < mid; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr.get(j).compareTo(arr.get(j+1)) > 0) 
                   { 
                   
                        T temp = arr.get(j); 
                        arr.set(j, arr.get(j+1));
                        arr.set(j+1, temp);
                  } 
            
        }
        else{
            for (int i = 0; i < mid; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr.get(j).compareTo(arr.get(j+1)) < 0) 
                   { 
                   
                    T temp = arr.get(j); 
                    arr.set(j, arr.get(j+1));
                    arr.set(j+1, temp);
                  } 

        }
    }

    
    

    public static <T extends Comparable<? super T>> void concurrentBubbleSort(List<T> arr)
    {   
        //System.out.println(Arrays.toString(arr));
        ExecutorService executor = Executors.newFixedThreadPool(2);
        int length = arr.size();
        int mid = length / 2;
        List<T> arr1 = arr.subList(0, length);//List<T> arr1 = arr.clone();
        List<T> arr2 = arr.subList(0, length);//List<T> arr2 = arr.clone();
        executor.submit(new Sort(arr1, mid, true));
        executor.submit(new Sort(arr2, length - mid, false));
        
        //System.out.println(Arrays.toString(arr1));
        //System.out.println(Arrays.toString(arr2));

        //Collections.reverse(Arrays.asList(arr2));
        //System.out.println(Arrays.toString(arr2));
        
        executor.shutdown();

        try {
            if (!executor.awaitTermination(60000, TimeUnit.SECONDS)) 
                executor.shutdownNow();     
        } catch (InterruptedException e) {
            executor.shutdownNow();
        } finally {
            for(int i = 0; i < length - mid - 1; i++)
                arr.set(i,arr2.get(length - i - 1));

            for(int i = length - mid; i < length; i++)
                arr.set(i,arr1.get(i));
        }
        

        //System.out.println(Arrays.toString(arr));

    }
}