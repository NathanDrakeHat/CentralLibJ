package org.nathan.acm;

public class ACM{

    /**
     * a ^ b % m <br/>
     * b = 101... = c_k*2^(k-1) + ... <br/>
     * a^b = a^(2^(k-1)) * 1 * a^(2^(k-3)) * ...<br/>
     * @param a a
     * @param b b
     * @param m m
     * @return int
     */
    public static int fastPowerMod(int a, int b, int m){
        int ans = 1 % m;
        while(b != 0) {
            if((b & 1) == 1){
                ans = (int) ((long) a * ans % m);
            }
            a = (int) ((long) a * a);
            b = b >>> 1;
        }
        return ans;
    }

    /**
     * a * b % m <br/>
     * b = 101... = c_k*2^(k-1) + ... <br/>
     * a * b = 2^(k-1)*a + 0*a + 2^(k-3)*a+ ...
     * @param a a
     * @param b b
     * @param m m
     * @return int
     */
    public static long longFastMultiplyMod(long a, long b, long m){
        long ans = 0;
        while(b != 0) {
            if((b & 1) == 1){
                ans = (ans + a) % m;
            }
            a *= 2;
            b = b >>> 1;
        }
        return ans;
    }
}
