package miscellaneous;

import tools.SimpleDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;


public final class Sort {
    public static void mergeSort(double[] array){ mergeSort(array, 0, array.length); }
    private static void mergeSort(double[] array, int start, int end){
        if ((end - start) > 1) {
            int middle = (start + end) / 2;
            mergeSort(array, start, middle);
            mergeSort(array, middle, end);
            int len1 = middle - start , len2 =  end - middle ;
            var left = new double[len1] ;
            var right = new double[len2] ;
            System.arraycopy(array, start , left, 0, len1);
            System.arraycopy(array, middle , right, 0, len2);

            boolean left_end = false;
            boolean right_end = false;
            int l_idx = 0, r_idx = 0;
            int t = 0;
            for (int i = 0; i < (end - start); i++) {
                if (left[l_idx] < right[r_idx]) {
                    array[start + i] = left[l_idx++];
                    if (l_idx == len1) {
                        left_end = true;
                        t = i+1;
                        break;
                    }
                }else {
                    array[start + i] = right[r_idx++];
                    if (r_idx == len2) {
                        right_end = true;
                        t = i+1;
                        break;
                    }
                }
            }
            if (left_end) {
                for(; t < (end-start);t++){
                    array[start + t] = right[r_idx++];
                }
            }else if(right_end){
                for(; t < (end-start);t++){
                    array[start + t] = left[l_idx++];
                }
            }
        }
    }

    public static void iterateMergeSort(double[] array){
        int exp_times = (int) Math.floor(Math.log(array.length)/Math.log(2));
        int group_size = 2;
        int sub_group_size = group_size / 2;
        int last_rest_len = 0;
        boolean not_exp_of_2 = (Math.pow(2, exp_times) != array.length);
        if(not_exp_of_2){ last_rest_len = array.length % 2 == 0? 2 : 1; }
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
        if(not_exp_of_2){ mergeRestPart(array, 0, sub_group_size, array.length - sub_group_size); }
    }
    private static void mergeRestPart(double[] array, int former_start, int former_len, int later_len){
        var cache1 = new double[former_len];
        var cache2 = new double[later_len];
        System.arraycopy(array, former_start, cache1, 0,former_len);
        System.arraycopy(array, former_start+former_len, cache2, 0,later_len);
        int cache1_idx = 0;
        int cache2_idx = 0;
        for(int i = former_start; (i<former_start+former_len+later_len)&&(cache1_idx<former_len)&&(cache2_idx<later_len); i++){
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
                                         int len, int group_size,
                                         double[] cache1, double[] cache2){
        System.arraycopy(array, former_start, cache1, 0,len);
        System.arraycopy(array, former_start+len, cache2, 0,len);
        int cache1_idx = 0;
        int cache2_idx = 0;
        for(int i = former_start; (i<former_start+group_size)&&(cache1_idx<len)&&(cache2_idx<len); i++){
            if(cache1[cache1_idx] <= cache2[cache2_idx]){
                array[i] = cache1[cache1_idx++]; }
            else {
                array[i]  =cache2[cache2_idx++]; }
        }
        if(cache1_idx != len){
            System.arraycopy(cache1, cache1_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    len-cache1_idx); }
        else if(cache2_idx != len){
            System.arraycopy(cache2, cache2_idx,
                    array, former_start+cache2_idx+cache1_idx,
                    len-cache2_idx); }
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

    private static int partition(double[] a, int start, int end){ // base case (end -start)
        var pivot = a[end - 1];
        int i = start - 1;
        for(int j = start; j < end - 1; j++){ // larger than pivot in [i+1, j-1]
            if(a[j] <= pivot){
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i; //pivot index
    }
    public static void quickSort(double[] a){ quickSort(a, 0, a.length); }
    private static void quickSort(double[] a, int start, int end){
        if((end - start) > 1){
            int middle = partition(a, start, end);
            quickSort(a, start, middle);
            quickSort(a, middle, end);
        }
    }

    private static int randPartition(double[] a, int start, int end){ // base case (end - start)
        int pivot_idx = ThreadLocalRandom.current().nextInt(start,end);
        var pivot = a[pivot_idx];

        var temp = a[end - 1];
        a[end - 1] = pivot;
        a[pivot_idx] = temp;

        int i = start - 1;
        for(int j = start; j < end - 1; j++){ // larger than pivot in [i+1, j-1]
            if(a[j] <= pivot){
                var t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i; //pivot value position
    }
    public static void randQuickSort(double[] a){ randQuickSort(a, 0, a.length); }
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
        } //uniform
        for(int i = 1; i < range; i++) { c[i] = c[i] + c[i - 1]; }
        for(int i = a.length - 1; i >=0; i--){
            b[c[(a[i] - min)] - 1] = a[i];
            c[(a[i] - min)]--;
        }
        System.arraycopy(b, 0, a,0,b.length);
    }

    public static void radixSort(SimpleDate[] a){
        //sort from smaller bit to bigger bit
        Arrays.sort(a, Comparator.comparing(SimpleDate::getDay));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getMonth));
        Arrays.sort(a, Comparator.comparing(SimpleDate::getYear));
    }

    // mean distribution
    public static void bucketSort(double[] a){
        @SuppressWarnings("unchecked")
        SingleLinkedNode<Double>[] b = (SingleLinkedNode<Double>[])new SingleLinkedNode[a.length];
        for(int i=0; i < b.length; i++){ b[i] = new SingleLinkedNode<>(); } // initialization
        for (double v : a) { //build bucket
            SingleLinkedNode<Double> handle = b[(int) (a.length * v)];
            while (handle.getParent() != null) {
                handle = handle.getParent();
            }
            SingleLinkedNode<Double> t = new SingleLinkedNode<>(v);
            handle.setParent(t);
        }
        //sort
        for(int i=0; i < a.length; i++){
            var handle = b[i].getParent();
            if(handle == null) { continue; } // zero elem
            var itr = handle.getParent();
            if (itr == null) { continue; } // one elem already sorted
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
        //get result
        int idx = 0;
        for (var node : b) {
            if (node.getParent() == null) {
                continue;
            } // zero elem continue
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