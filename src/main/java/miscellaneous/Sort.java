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
            mergeSort(array, start, middle); // divide
            mergeSort(array, middle, end);
            int len1 = middle - start , len2 =  end - middle ;  // merge
            var left = new double[len1] ;
            var right = new double[len2] ;  // copy to left and right
            System.arraycopy(array, start , left, 0, len1);
            System.arraycopy(array, middle , right, 0, len2);

            boolean left_end = false;
            boolean right_end = false;
            int l_idx = 0, r_idx = 0;
            int t = 0;  //relay the following i
            for (int i = 0; i < (end - start); i++) { // select the smallest from left and right
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
            }else if(right_end){  //right end
                for(; t < (end-start);t++){
                    array[start + t] = left[l_idx++];
                }
            }
        }// else end, because only one element
    }

    public static void iterateMergeSort(double[] array){
        int expand_iter_times = (int) Math.floor(Math.log(array.length)/Math.log(2));
        int group_size = 2;
        int sub_group_size = group_size / 2;
        int last_rest_len = 0;
        if(Math.pow(2, expand_iter_times) != array.length){
            last_rest_len = 1; }
        // overall 2*k part:
        for(int i = 0; i < expand_iter_times; i++){
            int group_iter_times = array.length/group_size;
            int group_start = 0;
            double[] cache1 = new double[sub_group_size];
            double[] cache2 = new double[sub_group_size];
            // 2*k part:
            for(int j = 0; j < group_iter_times; j++){
//                System.arraycopy(array,group_start,cache1,0,sub_group_size);
//                System.arraycopy(array,group_start+sub_group_size,cache2,0,sub_group_size);
//                int cache1_idx = 0;
//                int cache2_idx = 0;
//                for(int k = group_start;(k<group_size)&&(cache1_idx<sub_group_size)&&(cache2_idx<sub_group_size); k++){
//                    if(cache1[cache1_idx] <= cache2[cache2_idx]){
//                        array[k] = cache1[cache1_idx++]; }
//                    else {
//                        array[k]  =cache2[cache2_idx++]; }
//                }
//                if(cache1_idx != sub_group_size){
//                    System.arraycopy(cache1, cache1_idx,
//                            array, group_start+cache2_idx+cache1_idx,
//                            sub_group_size-cache1_idx); }
//                else if(cache2_idx != sub_group_size){
//                    System.arraycopy(cache2, cache2_idx,
//                            array, group_start+cache2_idx+cache1_idx,
//                            sub_group_size-cache2_idx); }
                mergeWithCache(array, group_start, sub_group_size,group_size,cache1,cache2);
                group_start += group_size;
            }
            // rest part:
            int current_rest_len = array.length - group_iter_times*group_size;
            if(current_rest_len > last_rest_len){
                mergeRestPart(array,array.length-current_rest_len,
                        current_rest_len-last_rest_len,last_rest_len);
                last_rest_len = current_rest_len;
            }

            group_size *= 2;
            sub_group_size /= 2;
        }
        // final rest part:
        mergeRestPart(array, 0, group_size, array.length - group_size);
    }
    private static void mergeRestPart(double[] array, int former_start, int former_len, int later_len){
        var cache1 = new double[former_len];
        var cache2 = new double[later_len];
        System.arraycopy(array, former_start, cache1, 0,former_len);
        System.arraycopy(array, former_start+former_len, cache2, 0,later_len);
        int cache1_idx = 0;
        int cache2_idx = 0;
        for(int i = former_start; (i<former_len+later_len)&&(cache1_idx<former_len)&&(cache2_idx<later_len); i++){
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
    private static void mergeWithCache(double[] array, int former_start, int len, int group_size, double[] cache1, double[] cache2){
        System.arraycopy(array, former_start, cache1, 0,len);
        System.arraycopy(array, former_start+len, cache2, 0,len);
        int cache1_idx = 0;
        int cache2_idx = 0;
        for(int i = former_start; (i<group_size)&&(cache1_idx<len)&&(cache2_idx<len); i++){
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
    // 2 : [0,1)[1,2)  [2,3)[3,4)  [4,5)[5,6)  [6,7)[7,8) [8,9)[9,10)
    // 4 : [0,2)[2,4)  [4,6)[6,8) | [8,10)
    // 8 : [0,4)[4,8) | [8,10)

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
    public static void quickSort(double[] a){
        quickSort(a, 0, a.length);
    }
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
    public static void randQuickSort(double[] a){
        randQuickSort(a, 0, a.length);
    }
    private static void randQuickSort(double[] a, int start, int end){
        if((end - start) > 1){
            int middle = randPartition(a, start, end);
            randQuickSort(a, start, middle);
            randQuickSort(a, middle, end);
        }
    }

    //int data only
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

}