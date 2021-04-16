import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.rmi.RemoteException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

// Value Object: Contains an integer and lock
class Value
{
    public Integer value;
    public ReentrantLock lock;

    Value ()
    {
        this.value = 0;
        this.lock = new ReentrantLock();
    }

    public String toString()
    {
        return "(" + this.value + ")";
    }
}


public class BucketSort implements Callable<Integer>
{
    public static int maximum(int [] array)
    {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] > max)
            {
                max = array[i];
            }
        }

        return max;
    }

    public static int minimum(int [] array)
    {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] < min)
            {
                min = array[i];                
            }
        }

        return min;
    }

    public static void sortSequential(int [] array)
    {
        // Both O(n)
        int max = maximum(array);
        int min = minimum(array);

        int bucketSize = max - min + 1;
        
        // O(m)
        Value [] bucket = new Value[bucketSize];

        for (int i = 0; i < bucketSize; i++)
        {
            bucket[i] = new Value();
        }

        // O(n)
        for (int i = 0; i < array.length; i++)
        {
            int k = array[i] - min;
            bucket[k].value++;
        }

        int j = 0;
        
        // O(m)
        for (int i = 0; i < bucket.length; i++)
        {
            while (bucket[i].value > 0)
            {
                bucket[i].value--;
                array[j] = i + min;
                j++;
            }
        }
    }

    public static void sort(int [] array)
    {
        // Both O(n)
        int max = maximum(array);
        int min = minimum(array);

        int bucketSize = max - min + 1;
        
        // O(m)
        Value [] bucket = new Value[bucketSize];

        for (int i = 0; i < bucketSize; i++)
        {
            bucket[i] = new Value();
        }

        // O(n)
        for (int i = 0; i < array.length; i++)
        {
            int k = array[i] - min;
            bucket[k].value++;
        }

        int j = 0;
        
        // O(m)
        for (int i = 0; i < bucket.length; i++)
        {
            while (bucket[i].value > 0)
            {
                bucket[i].value--;
                array[j] = i + min;
                j++;
            }
        }
    }

    public Integer call()
    {
        return 0;
    }

    public static void main(String [] args) 
    {
        int [] array = new int[(int)2e8];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = (int)(Math.random() * 10) + 1;
        }

        // System.out.println(Arrays.toString(array));

        long start = System.currentTimeMillis();
        // sortSequential(array);
        sort(array);
        long finish = System.currentTimeMillis();
        System.out.println("Finished: " + ((finish - start)) + " ms");

        // System.out.println(Arrays.toString(array));
    }
}
