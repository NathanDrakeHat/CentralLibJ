package org.nathan.algorithmsJava.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Euler tools
 */
public class Euler{
    /**
     *
     * @param limit upper limit
     * @return list of primes below limit
     */
    public static List<Long> SieveOfAtkin(int limit){

        List<Long> res = new ArrayList<>();
        if(limit > 2){
            res.add(2L);
        }

        if(limit > 3){
            res.add(3L);
        }

        boolean[] sieve = new boolean[limit];

        for(int i = 0; i < limit; i++)
            sieve[i] = false;

        for(int x = 1; x * x < limit; x++){
            for(int y = 1; y * y < limit; y++){

                int n = (4 * x * x) + (y * y);
                if(n <= limit && (n % 12 == 1 || n % 12 == 5)){ sieve[n] ^= true; }

                n = (3 * x * x) + (y * y);
                if(n <= limit && n % 12 == 7){ sieve[n] ^= true; }

                n = (3 * x * x) - (y * y);
                if(x > y && n <= limit && n % 12 == 11){ sieve[n] ^= true; }
            }
        }

        for(int r = 5; r * r < limit; r++){
            if(sieve[r]){
                for(int i = r * r; i < limit;
                    i += r * r)
                    sieve[i] = false;
            }
        }

        for(int a = 5; a < limit; a++){
            if(sieve[a]){
                res.add((long)a);
            }
        }

        return res;
    }
}
