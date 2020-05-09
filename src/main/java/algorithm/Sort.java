package algorithm;

import tool.SimpleDate;

import java.util.Random;

public class Sort {
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
        var rand = new Random();
        int pivot_idx = rand.nextInt(end - start) + start;
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
        for(int i=0; i < a.length; i++){
            int min_day = a[i].day;
            var min_date = a[i];
            for(int j=i+1; j < a.length; j++){
                if(a[j].day <= min_day){
                    min_day = a[j].day;
                    min_date = a[j];
                }
            }
            var temp = min_date;
            min_date = a[i];
            a[i] = temp;
        }

        for(int i=0; i < a.length; i++){
            int min_month = a[i].month;
            var min_date = a[i];
            for(int j=i+1; j < a.length; j++){
                if(a[j].month <= min_month){
                    min_month = a[j].month;
                    min_date = a[j];
                }
            }
            var temp = min_date;
            min_date = a[i];
            a[i] = temp;
        }

        for(int i=0; i < a.length; i++){
            int min_year = a[i].year;
            var min_date = a[i];
            for(int j=i+1; j < a.length; j++){
                if(a[j].year <= min_year){
                    min_year = a[j].year;
                    min_date = a[j];
                }
            }
            var temp = min_date;
            min_date = a[i];
            a[i] = temp;
        }
    }

    private static class SingleLinkedNode{
        private double val ;
        private SingleLinkedNode next;

        SingleLinkedNode(){}

        SingleLinkedNode(double val){
            this.val = val;
            this.next = null;
        }

        public SingleLinkedNode next(){ return this.next;}

        public void setNext(SingleLinkedNode n){ this.next = n; }

        public double value(){ return this.val; }

        public void setValue(double val) { this.val = val; }

    }
    public static void bucketSort(double[] a){
        SingleLinkedNode[] b = new SingleLinkedNode[a.length];
        for(int i=0; i < b.length; i++){ b[i] = new SingleLinkedNode(); } // initialization
        for (double v : a) { //build bucket
            SingleLinkedNode handle = b[(int) (a.length * v)];
            while (handle.next() != null) {
                handle = handle.next();
            }
            SingleLinkedNode t = new SingleLinkedNode(v);
            handle.setNext(t);
        }
        //sort
        for(int i=0; i < a.length; i++){
            SingleLinkedNode handle = b[i].next();
            if(handle == null) { continue; } // zero elem
            SingleLinkedNode itr = handle.next();
            if (itr == null) { continue; } // one elem already sorted
            while(itr != null) {
                double min_value = handle.value();
                SingleLinkedNode min_node = handle;
                while (itr != null) {
                    if (itr.value() <= min_value) {
                        min_value = itr.value();
                        min_node = itr;
                    }
                    itr = itr.next();
                }
                double t = min_node.value();
                min_node.setValue(handle.value());
                handle.setValue(t);
                handle = handle.next();
                itr = handle.next();
            }
        }
        //get result
        int idx = 0;
        for (SingleLinkedNode node : b) {
            if (node.next() == null) {
                continue;
            } // zero elem continue
            SingleLinkedNode handle = node.next();
            while (handle != null) {
                a[idx++] = handle.value();
                handle = handle.next();
            }
        }
    }

}