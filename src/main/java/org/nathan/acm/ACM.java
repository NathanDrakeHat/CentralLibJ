package org.nathan.acm;


import org.nathan.centralUtils.utils.ArrayUtils;

import java.util.List;


@SuppressWarnings("unused")
public class ACM{

    /**
     * a ^ b % m <br/>
     * b = 101... = c_k*2^(k-1) + ... <br/>
     * a^b = a^(2^(k-1)) * 1 * a^(2^(k-3)) * ...<br/>
     *
     * @param a a
     * @param b b >= 0
     * @param m m
     * @return int
     */
    public static int fastPowerMod(int a, int b, int m){
        int ans = 1 % m;
        while(b != 0) {
            if((b & 1) == 1){
                ans = (int) ((long) a * ans % m);
            }
            a = (int) ((long) a * a % m);
            b = b >>> 1;
        }
        return ans;
    }

    /**
     * a * b % m <br/>
     * b = 101... = c_k*2^(k-1) + ... <br/>
     * a * b = 2^(k-1)*a + 0*a + 2^(k-3)*a+ ...
     *
     * @param a a
     * @param b b >= 0
     * @param m m
     * @return int
     */
    public static long longFastMultiplyMod1(long a, long b, long m){
        long ans = 0;
        while(b != 0) {
            if((b & 1) == 1){
                ans = (ans + a) % m;
            }
            a *= 2 % m;
            b = b >>> 1;
        }
        return ans;
    }

    /**
     * a*b%m = a*b - floor(a*b/p)*p
     *
     * @param a a
     * @param b b
     * @param m m
     * @return long
     */
    public static long longFastMultiplyMod2(long a, long b, long m){
        a %= m;
        b %= m;
        long c = (long) ((double) a * b / m);
        long ans = a * b - c * m;
        if(ans < 0){
            ans += m;
        }
        else if(ans >= m){
            ans -= m;
        }
        return ans;
    }

    /**
     * @param num num
     * @param k   start from 0
     * @return byte
     */
    public static int kthBinDigit(int num, int k){
        return num >>> k & 1;
    }

    public static int lastKBinDigits(int num, int k){
        return num & ((1 << k) - 1);
    }

    public static int notKthBinDigits(int num, int k){
        return num ^ (1 << k);
    }

    public static int setKthBinDigitOne(int num, int k){
        return num | (1 << k);
    }

    public static int setKthBinDigitZero(int num, int k){
        return num & (~(1 << k));
    }

    /**
     * shortest undirected path
     *
     * @param n       number of vertices
     * @param weights undirected graph weights
     * @return shortest path length
     */
    public static double solve_hamilton(int n, double[][] weights){
        if(!ArrayUtils.isMatrix(weights)){
            throw new IllegalArgumentException();
        }
        int len = weights.length;
        double[][] path_vert_dist = new double[1 << len][len];
        for(var r : path_vert_dist){
            for(int i = 0; i < len; i++){
                r[i] = Double.POSITIVE_INFINITY;
            }
        }
        path_vert_dist[1][0] = 0;
        for(int path = 1; path < (1 << n); path++){
            for(int v_a = 0; v_a < n; v_a++){
                if(kthBinDigit(path, v_a) == 1){
                    for(int v_b = 0; v_b < n; v_b++){
                        if(kthBinDigit(path, v_b) == 1){
                            double dist = path_vert_dist[notKthBinDigits(path, v_a)][v_b] + weights[v_a][v_b];
                            path_vert_dist[path][v_a] = Math.min(path_vert_dist[path][v_a], dist);
                        }
                    }
                }
            }
        }

        return path_vert_dist[(1 << n) - 1][4];
    }

    public static int strangeSwitch(String[][] switches){
        if(!ArrayUtils.isMatrix(switches)){
            throw new IllegalArgumentException();
        }
        return recursiveSolveStrangeSwitch(switches, 0, 0, 0);
    }

    private static int recursiveSolveStrangeSwitch(String[][] switches, int r, int c, int push_count){
        if(r >= switches.length){
            for(var item : switches[switches.length - 1]){
                if(!item.equals("x")){
                    return -1;
                }
            }
            return push_count;
        }

        int nc = c + 1;
        int nr = r;
        if(nc >= switches[0].length){
            nc = 0;
            nr += 1;
        }

        if(r == 0){
            int left_min, right_min;
            flipNeighbor(switches, r, c);
            left_min = recursiveSolveStrangeSwitch(switches, nr, nc, push_count + 1);
            flipNeighbor(switches, r, c);

            right_min = recursiveSolveStrangeSwitch(switches, nr, nc, push_count);

            if(left_min < 0 && right_min < 0){
                return -1;
            }
            else if(left_min < 0){
                return right_min;
            }
            else if(right_min < 0){
                return left_min;
            }
            else{ return Math.min(left_min, right_min); }
        }
        else{
            if(switches[r - 1][c].equals("o")){
                flipNeighbor(switches, r, c);
                int res = recursiveSolveStrangeSwitch(switches, nr, nc, push_count + 1);
                flipNeighbor(switches, r, c);
                return res;
            }
            else{
                return recursiveSolveStrangeSwitch(switches, nr, nc, push_count);
            }
        }
    }

    private static void flipSingle(String[][] switches, int r, int c){
        if(switches[r][c].equals("x")){
            switches[r][c] = "o";
        }
        else if(switches[r][c].equals("o")){
            switches[r][c] = "x";
        }
        else{ throw new RuntimeException("input format error."); }
    }

    private static void flipNeighbor(String[][] switches, int r, int c){
        if((r - 1) >= 0 && (r - 1) < switches.length){
            flipSingle(switches, r - 1, c);
        }
        if((c - 1) >= 0 && (c - 1) < switches[0].length){
            flipSingle(switches, r, c - 1);
        }

        if((r + 1) >= 0 && (r + 1) < switches.length){
            flipSingle(switches, r + 1, c);
        }
        if((c + 1) >= 0 && (c + 1) < switches[0].length){
            flipSingle(switches, r, c + 1);
        }
        flipSingle(switches, r, c);
    }

    public static int laserBomb(int[][] targets, int radius){
        if(!ArrayUtils.isMatrix(targets)){
            throw new IllegalArgumentException();
        }

        int[][] sums = new int[targets.length][targets.length];

        for(int r = 0; r < targets.length; r++){
            int rSum = 0;
            for(int c = 0; c < targets.length; c++){
                rSum += targets[r][c];
                if(r >= 1){
                    sums[r][c] = rSum + sums[r - 1][c];
                }
                else{
                    sums[r][c] = rSum;
                }
            }
        }

        if(radius > targets.length){
            return sums[sums.length - 1][sums.length - 1];
        }

        int max = 0;

        for(int r = radius - 1; r < targets.length; r++){
            for(int c = radius - 1; c < targets.length; c++){
                int cost;
                if(r - radius >= 0 && c - radius >= 0){
                    cost = sums[r][c] - sums[r - radius][c] - sums[r][c - radius] + sums[r - radius][c - radius];
                }
                else if(r - radius >= 0){
                    cost = sums[r][c] - sums[r - radius][c];
                }
                else if(c - radius >= 0){
                    cost = sums[r][c] - sums[r][c - radius];
                }
                else {
                    cost = sums[r][c];
                }
                max = Math.max(cost, max);
            }
        }

        return max;
    }

    public static <Num> Num extremum(List<Num> nums){
        throw new RuntimeException();
    }

    public static int sumDiv(int a, int b){
        throw new RuntimeException();
    }
}
