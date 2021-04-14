import java.lang.Thread;

class ThreadSort extends Thread 
{
    public void run() 
    {
        System.out.println("Hello");
    }
}

public class BucketSort 
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

    public static void sort(int [] array)
    {
        // Both O(n)
        int max = maximum(array);
        int min = minimum(array);

        int bucketSize = max - min + 1;
        
        // O(m)
        int [] bucket = new int[bucketSize];

        // O(n)
        for (int i = 0; i < array.length; i++)
        {
            int k = array[i] - min;
            bucket[k]++;
        }

        int j = 0;
        
        // O(m)
        for (int i = 0; i < bucket.length; i++)
        {
            while (bucket[i] > 0)
            {
                bucket[i]--;
                array[j] = i + min;
                j++;
            }
        }
    }
}
