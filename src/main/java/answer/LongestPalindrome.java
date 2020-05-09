package answer;

public class LongestPalindrome {
    //dynamic programming
    public static String solve(String s) {
        int len = s.length();
        if(len == 0){
            return s;
        }
        int max_len = 1;
        int res_start = 0, res_end = 0; // zero index, close interval
        boolean[][] S = new boolean[len][len];
        //one
        for(int i = 0; i < len; i++){
            S[i][i] = true;
        }
//        two
        for(int i = 0; i + 1 < len; i++){
            if(s.charAt(i) == s.charAt(i+1)){
                S[i][i+1] = true;
                if(max_len < 2) {
                    max_len = 2;
                    res_start = i;
                    res_end = i + 1;
                }
            }
        }
        // other
        for(int l = 3; l <= len; l++){
            for(int i = 0; i + l - 1 < len; i++){
                if(S[i + 1][i + l - 2]){
                    if(s.charAt(i) == s.charAt(i+l-1)){
                        S[i][i + l - 1] = true;
                        if(max_len < l){
                            max_len = l;
                            res_start = i;
                            res_end = i + l - 1;
                        }
                    }
                }
            }
        }
        return s.substring(res_start, res_end + 1);
    }
}
