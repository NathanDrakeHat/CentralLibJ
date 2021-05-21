package org.nathan.acm;

import java.nio.ByteBuffer;

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
     * @param n number of vertices
     * @param weights undirected graph weights
     * @return shortest path length
     */
    public static double solve_hamilton(int n, double[][] weights){
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
}
