package miscellaneous;

import tools.SimpleDate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ThreadLocalRandom;


public final class Sort {
    public static void mergeSort(int[] array){
        mergeSort(array, 0, array.length);
    }
    private static void mergeSort(int[] array, int start, int end){
        if ((end - start) > 1) {
            int middle = (start + end) / 2;
            mergeSort(array, start, middle); // divide
            mergeSort(array, middle, end);
            int len1 = middle - start , len2 =  end - middle ;  // merge
            int[] left = new int[len1] ;
            int[] right = new int[len2] ;  // copy to left and right
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

    private static void maxHeapify(int[] arr, int idx, int heap_size){
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
            int t = arr[max_idx];
            arr[max_idx] = arr[idx];
            arr[idx] = t;
            maxHeapify(arr, max_idx, heap_size);
        }
    }
    private static void buildMaxHeap(int[] arr){
        for(int i = arr.length/2 - 1; i >= 0; i--){
            maxHeapify(arr, i, arr.length);
        }
    }
    public static void heapSort(int[] a){
        buildMaxHeap(a);
        int heap_size = a.length;
        for(int i = a.length - 1; i >= 1; i--){
            int t = a[0];
            a[0] = a[i];
            a[i] = t;
            maxHeapify(a, 0, --heap_size);
        }
    }

    private static int partition(int[] a, int start, int end){ // base case (end -start)
        int pivot = a[end - 1];
        int i = start - 1;
        for(int j = start; j < end - 1; j++){ // larger than pivot in [i+1, j-1]
            if(a[j] <= pivot){
                int t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i; //pivot index
    }
    public static void quickSort(int[] a){
        quickSort(a, 0, a.length);
    }
    private static void quickSort(int[] a, int start, int end){
        if((end - start) > 1){
            int middle = partition(a, start, end);
            quickSort(a, start, middle);
            quickSort(a, middle, end);
        }
    }

    private static int randPartition(int[] a, int start, int end){ // base case (end - start)
        int pivot_idx = ThreadLocalRandom.current().nextInt(start,end);
        int pivot = a[pivot_idx];

        int temp = a[end - 1];
        a[end - 1] = pivot;
        a[pivot_idx] = temp;

        int i = start - 1;
        for(int j = start; j < end - 1; j++){ // larger than pivot in [i+1, j-1]
            if(a[j] <= pivot){
                int t = a[j];
                a[j] = a[++i];
                a[i] = t;
            }
        }
        a[end - 1] = a[++i];
        a[i] = pivot;
        return i; //pivot value position
    }
    public static void randQuickSort(int[] a){
        randQuickSort(a, 0, a.length);
    }
    private static void randQuickSort(int[] a, int start, int end){
        if((end - start) > 1){
            int middle = randPartition(a, start, end);
            randQuickSort(a, start, middle);
            randQuickSort(a, middle, end);
        }
    }

    public static void countingSort(int[] a){ //int data only
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