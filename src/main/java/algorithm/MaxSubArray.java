package algorithm;

public class MaxSubArray {
    // find max sum consequent sequence
    public static class Interval{
        public int start;
        public int end;
        public int max_sum;
        public Interval(int s, int e, int m){
            this.start = s;
            this.end = e;
            this.max_sum = m;
        }
    }

    public static Interval divideAndConquer(int[] array, int start, int end){
        if((end - start) > 1){
            int middle = (start + end) / 2;
            Interval left_sub = divideAndConquer(array, start, middle);
            Interval right_sub = divideAndConquer(array, middle, end);
            int max_left_sum = array[middle-1], max_right_sum = array[middle]; // for max_middle_sum
            int left_p = middle-1, right_p = middle;
            int acc_sum = max_left_sum;
            for(int i = left_p - 1; i >= start; i--){
                acc_sum += array[i];
                if(acc_sum > max_left_sum){
                    max_left_sum = acc_sum;
                    left_p = i;
                }
            }
            acc_sum = max_right_sum;
            for(int i = right_p + 1; i < end; i++){
                acc_sum += array[i];
                if(acc_sum > max_right_sum){
                    max_right_sum = acc_sum;
                    right_p = i;
                }
            }
            int middle_sum = max_left_sum + max_right_sum;
            int max_middle_sum = 0, middle_low = 0, middle_high = 0;
            if(middle_sum >= max_left_sum && middle_sum >= max_right_sum){
                middle_low = left_p;
                middle_high = right_p + 1;
                max_middle_sum = middle_sum;
            }else if(middle_sum <= max_left_sum && max_left_sum >= max_right_sum){
                middle_low = left_p;
                middle_high = middle;
                max_middle_sum = max_left_sum;
            }else{
                middle_low = middle;
                middle_high = right_p + 1;
                max_middle_sum = max_right_sum;
            }
            Interval middle_sub = new Interval(middle_low, middle_high, max_middle_sum);
            int m = middle_sub.max_sum;
            int l = left_sub.max_sum;
            int r = right_sub.max_sum;
            if(m >= l && m>= r) {
                return new Interval(middle_sub.start, middle_sub.end, middle_sub.max_sum);
            }
            if(l >= r) {
                return new Interval(left_sub.start, left_sub.end, left_sub.max_sum);
            }
            return new Interval(right_sub.start, right_sub.end, right_sub.max_sum);

        }
        return new Interval(start, end, array[start]);
    }
    public static Interval onlineMaxSub(int[] array, int len){
        int a = 0, b = 1, acc_sum = 0; // a and b indicate the acc_sum [a,b)
        int start = 0, end = 1;
        acc_sum = array[0];
        int max_sum = 0;
        for(int i = 1; i < len; i++){
            b = i + 1;
            acc_sum += array[i];
            if(acc_sum > max_sum){
                end = b;
                start = a;
                max_sum = acc_sum;
            }
            if(acc_sum < 0){ // throw if left sum is negative
                a = i + 1;
                b = i + 2;
                acc_sum = 0;
            }
        }
        return new Interval(start ,end, max_sum);
    }
}