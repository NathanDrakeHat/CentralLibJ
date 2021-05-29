package org.nathan;

import org.nathan.acm.ACM;
import org.nathan.centralUtils.utils.IOUtils;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;

import static org.nathan.centralUtils.utils.IOUtils.*;


class Main{
    public static void main(String[] args){
        if(args.length == 1){
            var i = Integer.valueOf(args[0]);
            IOUtils.system(String.format("git_proxy \"git push\" %s", args[0]));
        }
    }
}
