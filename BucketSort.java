import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.lang.reflect.Array;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class BucketSort extends Thread
{
    private Integer start, end;
    private static Integer min, max;
    private static List<Integer> array;
    private static int [] bucket;

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
            int k = this.array.get(i) - this.min;
            this.bucket[k]++;
        }
    }

    public void maxmin()
    {
        for (int i = 0; i < this.array.size(); i++)
        {
            if (this.array.get(i) > this.max)
            {
                this.max = this.array.get(i);
            }

            if (this.array.get(i) < this.min)
            {
                this.min = this.array.get(i);                
            }
        }
    }

    public void sortSequential(List<Integer> arr)
    {
        this.array = arr;
        maxmin();

        int bucketSize = this.max - this.min + 1;
        
        this.bucket = new int[bucketSize];

        for (int i = 0; i < this.array.size(); i++)
        {
            int k = this.array.get(i) - this.min;
            this.bucket[k]++;
        }

        int j = 0;
        
        for (int i = 0; i < this.bucket.length; i++)
        {
            while (this.bucket[i] > 0)
            {
                this.bucket[i]--;
                this.array.set(j, i + min);
                j++;
            }
        }
    }

    public void sort(List<Integer> arr) throws InterruptedException
    {
        this.array = arr;
        maxmin();

        int bucketSize = this.max - this.min + 1;

        this.bucket = new int[bucketSize];
        
        int numThreads = Runtime.getRuntime().availableProcessors();
        BucketSort [] threads = null;
        int begin = 0;
        int finish = 0;
        
        if (this.array.size() <= numThreads)
        {
            threads = new BucketSort[this.array.size()];
            for (int i = 0; i < threads.length; i++)
            {
                threads[i] = new BucketSort(i, i + 1);
                threads[i].start();
            }
        }
        else
        {
            threads = new BucketSort[numThreads];
            int partition = this.array.size() / numThreads;

            for (int i = 0; i < threads.length; i++)
            {
                finish = (i < threads.length - 1) ? finish + partition : this.array.size();
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
            while (bucket[i] > 0)
            {
                bucket[i]--;
                this.array.set(j, i + min);
                j++;
            }
        }
    }
}
