import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

// Value Object: Contains an integer and lock
class Value
{
    public Integer value;

    Value ()
    {
        this.value = 0;
    }

    public String toString()
    {
        return "(" + this.value + ")";
    }
}

public class BucketSort extends Thread
{
    private Integer start, end;
    private static Integer min, max;
    private static int [] array;
    private static Value [] bucket;

    BucketSort ()
    {
        this.min = Integer.MAX_VALUE;
        this.max = Integer.MIN_VALUE;
    }

    BucketSort (Integer start, Integer end)
    {   
        this.start = start;
        this.end = end;
    }

    public void run()
    {
        for (int i = this.start; i < this.end; i++)
        {
            int k = this.array[i] - this.min;
            this.bucket[k].value++;
        }
    }

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

    public void maxmin(int [] array)
    {
        for (int i = 0; i < array.length; i++)
        {
            if (array[i] > this.max)
            {
                this.max = array[i];
            }

            if (array[i] < this.min)
            {
                this.min = array[i];                
            }
        }
    }

    public void sortSequential(int [] array)
    {
        maxmin(array);

        int bucketSize = this.max - this.min + 1;
        
        this.bucket = new Value[bucketSize];

        for (int i = 0; i < bucketSize; i++)
        {
            this.bucket[i] = new Value();
        }

        for (int i = 0; i < array.length; i++)
        {
            int k = array[i] - min;
            this.bucket[k].value++;
        }

        int j = 0;
        
        for (int i = 0; i < this.bucket.length; i++)
        {
            while (this.bucket[i].value > 0)
            {
                this.bucket[i].value--;
                array[j] = i + min;
                j++;
            }
        }
    }

    public void sort(int [] array) throws InterruptedException
    {
        this.array = array;
        maxmin(array);

        int bucketSize = this.max - this.min + 1;

        this.bucket = new Value[bucketSize];
        
        for (int i = 0; i < this.bucket.length; i++)
        {
            this.bucket[i] = new Value();
        }
        
        int numThreads = Runtime.getRuntime().availableProcessors();
        BucketSort [] threads = null;
        int begin = 0;
        int finish = 0;
        
        if (array.length <= numThreads)
        {
            threads = new BucketSort[array.length];
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new BucketSort(i, i + 1);
                threads[i].start();
            }
        }
        else
        {
            threads = new BucketSort[numThreads];
            int partition = array.length / numThreads;

            for (int i = 0; i < threads.length; i++)
            {
                finish = (i < threads.length - 1) ? finish + partition : array.length;
                threads[i] = new BucketSort(begin, finish);
                threads[i].start();
                begin = finish;
            }
        }

        for (BucketSort thread: threads)
        {
            thread.join();
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

}
