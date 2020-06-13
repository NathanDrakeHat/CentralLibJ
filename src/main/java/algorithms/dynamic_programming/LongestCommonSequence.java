package algorithms.dynamic_programming;

public final class LongestCommonSequence { // longest common sequence problem, biological problem
    // dynamic programming

    public static char[] solve(char[] x, char[] y){
        return solve(x, y, false);
    }
    private static char[] solve(char[] x, char[] y, boolean print){
        int m = x.length;
        int n = y.length;
        char[][] b = new char[m][n]; // result
        int[][] c = new int[m+1][n+1]; // length
        for(int i = 1; i <= m; i++){
            c[i][0] = 0;
        }
        for(int j = 0; j <= n; j++){
            c[0][j] = 0;
        }
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(x[i - 1] == y[j - 1]){
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i - 1][j - 1] = '\\'; //left-up
                }else if(c[i - 1][j] >= c[i][j - 1]){
                    c[i][j] = c[i - 1][j];
                    b[i - 1][j - 1] = '|'; // up
                }else{
                    c[i][j] = c[i][j - 1];
                    b[i - 1][j - 1] = '-'; // left
                }
            }
        }
        if(print) {
            printLCS(b, x, x.length, y.length);
        }

        int count = getCount(b, x, x.length, y.length, 0);
        char[] res = new char[count];
        getResult(b, x, x.length, y.length, res, count - 1);
        return res;

    }

    private static void printLCS(char[][] b, char[] x, int i, int j){
        if(i == 0 | j == 0){
            return;
        }
        if(b[i - 1][j - 1] == '\\'){
            printLCS(b, x, i - 1, j - 1);
            System.out.printf("%c ", x[i - 1]);
        }else if(b[i - 1][j - 1] == '|'){
            printLCS(b, x, i - 1, j);
        }else{ // b[i][j] == '-'
            printLCS(b, x, i, j - 1);
        }
    }
    private static int getCount(char[][] b, char[] x, int i, int j, int count){
        if(i == 0 | j == 0){
            return count;
        }
        if(b[i - 1][j - 1] == '\\'){
            return getCount(b, x, i - 1, j - 1, count + 1);
        }else if(b[i - 1][j - 1] == '|'){
            return getCount(b, x, i - 1, j, count);
        }else{ // b[i][j] == '-'
            return getCount(b, x, i, j - 1, count);
        }
    }
    private static void getResult(char[][] b, char[] x, int i, int j, char[] res, int index){
        if(i == 0 || j == 0){
            return;
        }
        if(b[i - 1][j - 1] == '\\'){
            res[index] = x[i - 1];
            getResult(b, x, i - 1, j - 1, res, index - 1);
        }else if(b[i - 1][j - 1] == '|'){
            getResult(b, x, i - 1, j, res, index);
        }else{ // b[i][j] == '-'
            getResult(b, x, i, j - 1, res, index);
        }
    }
}
