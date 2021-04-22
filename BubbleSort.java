import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Sort implements Runnable
{   int arr[];
    int mid;
    boolean direction;
    public Sort(int arr[], int mid, boolean direction)
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
    static void bubbleSort(int arr[]) 
    { 
        //System.out.println(Arrays.toString(arr));
        int n = arr.length; 
        for (int i = 0; i < n-1; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j] > arr[j+1]) 
                { 
                    
                    int temp = arr[j]; 
                    arr[j] = arr[j+1]; 
                    arr[j+1] = temp; 
                } 
                //System.out.println(Arrays.toString(arr));
    }

    static void directionalBubbleSort(int arr[], int mid, boolean direction) 
    { 
        int n = arr.length;
        if(direction){
            for (int i = 0; i < mid; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j] > arr[j+1]) 
                   { 
                   
                       int temp = arr[j]; 
                       arr[j] = arr[j+1]; 
                      arr[j+1] = temp; 
                  } 
            
        }
        else{
            for (int i = 0; i < mid; i++) 
            for (int j = 0; j < n-i-1; j++) 
                if (arr[j] < arr[j+1]) 
                   { 
                   
                       int temp = arr[j]; 
                       arr[j] = arr[j+1]; 
                      arr[j+1] = temp; 
                  } 

        }
    }

    
    

    static void concurrentBubbleSort(int arr[])
    {   
        //System.out.println(Arrays.toString(arr));
        ExecutorService executor = Executors.newFixedThreadPool(2);
        int length = arr.length;
        int mid = length / 2;
        int arr1[] = Arrays.copyOfRange(arr,0,length);
        int arr2[] = Arrays.copyOfRange(arr, 0, length);
        executor.submit(new Sort(arr1, mid, true));
        executor.submit(new Sort(arr2, length - mid, false));
        
        //System.out.println(Arrays.toString(arr1));
        //System.out.println(Arrays.toString(arr2));

        //Collections.reverse(Arrays.asList(arr2));
        //System.out.println(Arrays.toString(arr2));
        
        for(int i = 0; i < length - mid - 1; i++)
            arr[i] = arr2[length - i - 1];

        for(int i = length - mid; i < length; i++)
        arr[i] = arr1[i];

        //System.out.println(Arrays.toString(arr));




    }
}