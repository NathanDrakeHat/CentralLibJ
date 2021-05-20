package org.nathan.acm;

import java.nio.ByteBuffer;

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
            a = (int) ((long) a * a);
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
            a *= 2;
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
    public static int kthBinaryDigit(int num, int k){
        return num >>> k & 1;
    }

    /**
     * @param num num
     * @param k   start from 0
     * @return int
     */
    public static int lastKBinaryDigits(int num, int k){
        return num & ((1 << k) - 1);
    }

    /**
     * @param num num
     * @param k   start from 0
     * @return int
     */
    public static int notKthBinaryDigits(int num, int k){
        return num ^ (1 << k);
    }

    /**
     * @param num num
     * @param k   start from 0
     * @return int
     */
    public static int setKthBinaryDigitOne(int num, int k){
        return num | (1 << k);
    }

    public static int setKthBinaryDigitZero(int num, int k){
        return num & (~(1 << k));
    }
}
