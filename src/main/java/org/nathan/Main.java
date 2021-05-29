package org.nathan;

import org.nathan.acm.ACM;
import org.nathan.centralUtils.utils.IOUtils;

import java.util.*;

import static org.nathan.centralUtils.utils.IOUtils.*;
import static org.nathan.centralUtils.utils.ArrayUtils.*;


class Main{
    public static void gitPush(String[] args){
        if(args.length == 1){
            var i = Integer.valueOf(args[0]);
            IOUtils.system(String.format("git_proxy \"git push\" %s", args[0]));
        }
    }

    public static void main(String[] args){
        gitPush(args);
    }


}
