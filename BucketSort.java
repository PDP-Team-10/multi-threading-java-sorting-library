import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.Runtime;
import java.util.concurrent.TimeUnit;

import javax.xml.validation.ValidatorHandler;

// Value Object: Contains an integer and lock
// class Value
// {
//     public Integer value;

//     Value ()
//     {
//         this.value = 0;
//     }

//     public String toString()
//     {
//         return "(" + this.value + ")";
//     }
// }

class Max extends Thread
{
    private int [] array; 
    public int max;

    Max (int [] array)
    {
        this.array = array;
        this.max = Integer.MIN_VALUE;
    }

    public void run()
    {
        for (int i = 0; i < this.array.length; i++)
        {
            if (this.array[i] > this.max)
            {
                this.max = this.array[i];
            }
        }
    }
}

class Min extends Thread
{
    private int [] array; 
    public int min;

    Min (int [] array)
    {
        this.array = array;
        this.min = Integer.MAX_VALUE;
    }

    public void run()
    {
        for (int i = 0; i < this.array.length; i++)
        {
            if (this.array[i] < this.min)
            {
                this.min = this.array[i];                
            }
        }
    }
}

public class BucketSort extends Thread
{
    private int min, max;
    private int [] bucket;
    private int [] array;

    public void run()
    {

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

    public void sort(int [] array) throws InterruptedException
    {
        Max tmax = new Max(array);
        Min tmin = new Min(array);
        tmax.start();
        tmin.start();
        tmax.join();
        tmin.join();

        
    }

    public static void main(String [] args) throws InterruptedException
    {
        int [] array = new int[(int)3];

        for (int i = 0; i < array.length; i++)
        {
            array[i] = (int)(Math.random() * 10) + 1;
        }

        System.out.println(Arrays.toString(array));

        long start = System.currentTimeMillis();
        BucketSort b = new BucketSort();
        b.sort(array);
        long finish = System.currentTimeMillis();
        
        System.out.println("Finished: " + ((finish - start)) + " ms");
    }
}
