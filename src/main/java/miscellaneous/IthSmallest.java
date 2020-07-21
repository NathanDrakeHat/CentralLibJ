package miscellaneous;

import java.util.concurrent.ThreadLocalRandom;

public final class IthSmallest
{
    private static int randPartition(int[] a, int start, int end)
    { // base case (end -start)
        int pivot_idx = ThreadLocalRandom.current().nextInt(start, end);
        int pivot = a[pivot_idx];

        int temp = a[end - 1];
        a[end - 1] = pivot;
        a[pivot_idx] = temp;

        int i = start - 1;
        for (int j = start; j < end - 1; j++)
        {
            if (a[j] <= pivot)
            {
                int t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i; //pivot idx
    }

    // select ith smallest element in array
    private static int randomSelect(int[] a, int start, int end, int ith)
    {
        if ((start - end) == 1)
        {
            return a[start];
        }
        int pivot_idx = randPartition(a, start, end);
        int left_total = pivot_idx - start;
        if (ith == left_total)
        {
            return a[pivot_idx];
        }
        else if (ith < left_total + 1)
        {
            return randomSelect(a, start, pivot_idx, ith);
        }
        else
        {
            return randomSelect(a, pivot_idx + 1, end, ith - left_total - 1);
        }
    }

    public static int randomSelect(int[] a, int ith)
    {
        return randomSelect(a, 0, a.length, ith);
    }
}