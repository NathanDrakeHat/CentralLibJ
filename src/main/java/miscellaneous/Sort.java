package miscellaneous;

import tools.SimpleDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import static multiThread.ParalleledFor.*;


public final class Sort {
    public static void mergeSort(double[] array){ mergeSort(array, 0, array.length); }
    private static void mergeSort(double[] array, int start, int end){
        if ((end - start) > 1) {
            int middle = (start + end) / 2;
            mergeSort(array, start, middle);
            mergeSort(array, middle, end);
            int left_len = middle - start;
            int right_len =  end - middle ;
            var left_cache = new double[left_len] ;
            var right_cache = new double[right_len] ;
            System.arraycopy(array, start , left_cache, 0, left_len);
            System.arraycopy(array, middle , right_cache, 0, right_len);
            int right_idx = 0;
            int left_idx = 0;
            for(int i = start; (i<end)&&(right_idx<right_len)&&(left_idx<left_len); i++){
                if(left_cache[left_idx] <= right_cache[right_idx]){
                    array[i] = left_cache[left_idx++]; }
                else{
                    array[i] = right_cache[right_idx++]; }
            }
            if(left_idx < left_len){
                System.arraycopy(left_cache,left_idx,
                        array,start+left_idx+right_idx,
                        left_len-left_idx);}
            else if(right_idx < right_len){
                System.arraycopy(right_cache,right_idx,
                        array,start+left_idx+right_idx,
                        right_len-right_idx);}
        }
    }

    public static void iterateMergeSort(double[] array){
        Objects.requireNonNull(array);
        if(array.length <= 1) return;
        int exp_times = (int) Math.floor(Math.log(array.length)/Math.log(2));
        int group_size = 2;
        int sub_group_size = group_size / 2;
        int last_rest_len = 0;
        boolean not_exp_of_2 = (Math.pow(2, exp_times) != array.length);
        if(not_exp_of_2){
            last_rest_len = array.length % 2 == 0? 2 : 1; }
        for(int i = 0; i < exp_times; i++){
            int group_iter_times = array.length/group_size;
            int group_start = 0;
            double[] cache1 = new double[sub_group_size];
            double[] cache2 = new double[sub_group_size];
            for(int j = 0; j < group_iter_times; j++){
                mergeRegularPart(array, group_start, sub_group_size,group_size,cache1,cache2);
                group_start += group_size;
            }
            int current_rest_len = array.length - group_iter_times*group_size;
            if(current_rest_len > last_rest_len){
                mergeRestPart(array,array.length-current_rest_len,
                        current_rest_len-last_rest_len,last_rest_len);
                last_rest_len = current_rest_len;
            }
            group_size *= 2;
            sub_group_size *= 2;
        }
        if(not_exp_of_2){
            mergeRestPart(array, 0, sub_group_size, array.length - sub_group_size); }
    }
    private static void mergeRestPart(double[] array, int former_start, int former_len, int later_len){
        var cache1 = new double[former_len];
        var cache2 = new double[later_len];
        System.arraycopy(array, former_start, cache1, 0,former_len);
        System.arraycopy(array, former_start+former_len, cache2, 0,later_len);
        int cache1_idx = 0;
        int cache2_idx = 0;
        int for_len = former_start+former_len+later_len;
        for(int i = former_start; (i<for_len)&&(cache1_idx<former_len)&&(cache2_idx<later_len); i++){
            if(cache1[cache1_idx] <= cache2[cache2_idx]){
                array[i] = cache1[cache1_idx++]; }
            else {
                array[i]  =cache2[cache2_idx++]; }
        }
        if(cache1_idx != former_len){
            System.arraycopy(cache1, cache1_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    former_len-cache1_idx); }
        else if(cache2_idx != later_len){
            System.arraycopy(cache2, cache2_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    later_len-cache2_idx); }
    }
    private static void mergeRegularPart(double[] array, int former_start,
                                         int sub_group_size, int group_size,
                                         double[] cache1, double[] cache2){
        System.arraycopy(array, former_start, cache1, 0,sub_group_size);
        System.arraycopy(array, former_start+sub_group_size, cache2, 0,sub_group_size);
        int cache1_idx = 0;
        int cache2_idx = 0;
        int for_len = former_start+group_size;
        for(int i = former_start; (i<for_len)&&(cache1_idx<sub_group_size)&&(cache2_idx<sub_group_size); i++){
            if(cache1[cache1_idx] <= cache2[cache2_idx]){
                array[i] = cache1[cache1_idx++]; }
            else {
                array[i]  =cache2[cache2_idx++]; }
        }
        if(cache1_idx != sub_group_size){
            System.arraycopy(cache1, cache1_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    sub_group_size-cache1_idx); }
        else if(cache2_idx != sub_group_size){
            System.arraycopy(cache2, cache2_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    sub_group_size-cache2_idx); }
    }

    public static void parrallelIterativeMergeSort(double[] array){
        Objects.requireNonNull(array);
        if(array.length <= 1) return;
        int exp_times = (int) Math.floor(Math.log(array.length)/Math.log(2));
        int group_size = 2;
        int sub_group_size = group_size / 2;
        int last_rest_len = 0;
        boolean not_exp_of_2 = (Math.pow(2, exp_times) != array.length);
        if(not_exp_of_2){
            last_rest_len = array.length % 2 == 0? 2 : 1; }
        for(int i = 0; i < exp_times; i++){
            int group_iter_times = array.length/group_size;
//            int group_start = 0;
            double[] cache1 = new double[sub_group_size];
            double[] cache2 = new double[sub_group_size];
//            for(int j = 0; j < group_iter_times; j++){
//                mergeRegularPart(array, group_start, sub_group_size,group_size,cache1,cache2);
//                group_start += group_size;
//            }
            int final_group_size = group_size;
            int final_sub_group_size = sub_group_size;
            forParallel(0,group_iter_times,(j)->()->{
                int group_start = j* final_group_size;
                mergeRegularPart(array,group_start, final_sub_group_size,final_group_size,cache1,cache2);
            });
            int current_rest_len = array.length - group_iter_times*group_size;
            if(current_rest_len > last_rest_len){
                mergeRestPart(array,array.length-current_rest_len,
                        current_rest_len-last_rest_len,last_rest_len);
                last_rest_len = current_rest_len;
            }
            group_size *= 2;
            sub_group_size *= 2;
        }
        if(not_exp_of_2){
            mergeRestPart(array, 0, sub_group_size, array.length - sub_group_size); }
    }

    private static void maxHeapify(double[] arr, int idx, int heap_size){
        int l = 2 * (idx + 1);
        int l_idx = l - 1;
        int r = 2 * (idx + 1) + 1;
        int r_idx = r - 1;
        int max_idx = idx;
        if ((l_idx < heap_size) && (arr[l_idx] > arr[max_idx])) {
            max_idx = l_idx;
        }
        if ((r_idx < heap_size) && (arr[r_idx] > arr[max_idx])) {
            max_idx = r_idx;
        }
        if (max_idx != idx) {
            var t = arr[max_idx];
            arr[max_idx] = arr[idx];
            arr[idx] = t;
            maxHeapify(arr, max_idx, heap_size);
        }
    }
    private static void buildMaxHeap(double[] arr){
        for(int i = arr.length/2 - 1; i >= 0; i--){
            maxHeapify(arr, i, arr.length);
        }
    }
    public static void heapSort(double[] a){
        buildMaxHeap(a);
        int heap_size = a.length;
        for(int i = a.length - 1; i >= 1; i--){
            var t = a[0];
            a[0] = a[i];
            a[i] = t;
            maxHeapify(a, 0, --heap_size);
        }
    }

    private static int partition(double[] a, int start, int end){
        var pivot = a[end - 1];
        int i = start - 1;
        for(int j = start; j < end - 1; j++){
            if(a[j] <= pivot){
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i;
    }
    public static void quickSort(double[] a){ quickSort(a, 0, a.length); }
    private static void quickSort(double[] a, int start, int end){
        if((end - start) > 1){
            int middle = partition(a, start, end);
            quickSort(a, start, middle);
            quickSort(a, middle, end);
        }
    }

    public static void randQuickSort(double[] a){ randQuickSort(a, 0, a.length); }
    private static int randPartition(double[] a, int start, int end){
        int pivot_idx = ThreadLocalRandom.current().nextInt(start,end);
        var pivot = a[pivot_idx];

        var temp = a[end - 1];
        a[end - 1] = pivot;
        a[pivot_idx] = temp;

        int i = start - 1;
        for(int j = start; j < end - 1; j++){
            if(a[j] <= pivot){
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i;
    }
    private static void randQuickSort(double[] a, int start, int end){
        if((end - start) > 1){
            int middle = randPartition(a, start, end);
            randQuickSort(a, start, middle);
            randQuickSort(a, middle, end);
        }
    }

    //int data only, mean distribution
    public static void countingSort(int[] a){
        int[] b = new int[a.length];
        int min = a[0], max = a[0];
        for(int i = 1; i < a.length; i++){
            if(a[i] > max){ max = a[i]; }
            if(a[i] < min){ min = a[i]; }
        }
        int range = max - min + 1;
        int[] c = new int[range];
        for (int value : a) {
            c[(value - min)]++;
        }
        for(int i = 1; i < range; i++) { c[i] = c[i] + c[i - 1]; }
        for(int i = a.length - 1; i >=0; i--){
            b[c[(a[i] - min)] - 1] = a[i];
            c[(a[i] - min)]--;
        }
        System.arraycopy(b, 0, a,0,b.length);
    }

    //sort from smaller bit to bigger bit
    public static void radixSort(SimpleDate[] a){
        Arrays.sort(a, Comparator.comparing(SimpleDate::getDay));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getMonth));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getYear));
    }

    // mean distribution
    public static void bucketSort(double[] a){
        @SuppressWarnings("unchecked")
        SingleLinkedNode<Double>[] b = (SingleLinkedNode<Double>[])new SingleLinkedNode[a.length];
        for(int i=0; i < b.length; i++){ b[i] = new SingleLinkedNode<>(); }
        for (double v : a) {
            SingleLinkedNode<Double> handle = b[(int) (a.length * v)];
            while (handle.getParent() != null) {
                handle = handle.getParent();
            }
            SingleLinkedNode<Double> t = new SingleLinkedNode<>(v);
            handle.setParent(t);
        }
        for(int i=0; i < a.length; i++){
            var handle = b[i].getParent();
            if(handle == null) { continue; }
            var itr = handle.getParent();
            if (itr == null) { continue; }
            while(itr != null) {
                double min_value = handle.getContent();
                var min_node = handle;
                while (itr != null) {
                    if (itr.getContent() <= min_value) {
                        min_value = itr.getContent();
                        min_node = itr;
                    }
                    itr = itr.getParent();
                }
                double t = min_node.getContent();
                min_node.setContent(handle.getContent());
                handle.setContent(t);
                handle = handle.getParent();
                itr = handle.getParent();
            }
        }
        int idx = 0;
        for (var node : b) {
            if (node.getParent() == null) {
                continue;
            }
            var handle = node.getParent();
            while (handle != null) {
                a[idx++] = handle.getContent();
                handle = handle.getParent();
            }
        }
    }
    private static class SingleLinkedNode<V>  {
        private V val ;
        private SingleLinkedNode<V> parent;

        public SingleLinkedNode(){}
        public SingleLinkedNode(V val){
            this.val = val;
            this.parent = null;
        }

        public SingleLinkedNode<V> getParent(){ return this.parent;}
        public void setParent(SingleLinkedNode<V> p) { this.parent = p; }

        public V getContent(){ return this.val; }
        public void setContent(V val) { this.val = val; }
    }
}