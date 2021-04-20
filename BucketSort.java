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


public class BucketSort
{
    public int index = 0;
    public Value [] bucket;
    public Integer min, max;

    public void sleep(int seconds)
    {
        try 
        {
            TimeUnit.SECONDS.sleep(seconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    public void maximum(int [] array)
    {
        max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] > max)
            {
                max = array[i];
            }
        }
    }

    public void minimum(int [] array)
    {
        min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] < min)
            {
                min = array[i];                
            }
        }
    }

    public void sortSequential(int [] array)
    {
        // Both O(n)
        maximum(array);
        minimum(array);

        int bucketSize = max - min + 1;
        
        // O(m)
        bucket = new Value[bucketSize];

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

    public void sort(int [] array)
    {
        maximum(array);
        minimum(array);

        int bucketSize = max - min + 1;

        bucket = new Value[bucketSize];

        for (int i = 0; i < bucketSize; i++)
        {
            bucket[i] = new Value();
        }

        Callable<Value []> cache = () ->
        {
            int key = array[index] - min;

            bucket[key].lock.lock();

            try
            {
                bucket[key].value++;
            }
            finally
            {
                bucket[key].lock.unlock();
                index++;
                return bucket;
            }
        };

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        ArrayList<Future<Value []>> futures = new ArrayList<>();


        for (int i = 0; i < array.length; i++)
        {
            futures.add(executor.submit(cache));
        }

        executor.shutdown();

        for (Future<Value []> f: futures)
        {
            try 
            {
                bucket = f.get();
            }
            catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }

        int j = 0;

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

    public static void main(String [] args) 
    {
        int [] array = new int[(int)3];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = (int)(Math.random() * 10) + 1;
        }

        // System.out.println(Arrays.toString(array));

        long start = System.currentTimeMillis();
        
        BucketSort b = new BucketSort();
        b.sort(array);
        // b.sortSequential(array);
        
        long finish = System.currentTimeMillis();
        
        System.out.println("Finished: " + ((finish - start)) + " ms");
        

        // System.out.println(Arrays.toString(array));
    }
}
