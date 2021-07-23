package org.nathan.centralibj.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

import java.util.Arrays;

public class NativeUtils {
  private interface CLibrary extends Library {
    CLibrary INSTANCE = Native.load((Platform.isWindows() ? "msvcrt" : "c"),
            CLibrary.class);

    @SuppressWarnings("UnusedReturnValue")
    int system(String cmd);
  }

  public static void GitProxy(String[] args) {
    if (args.length == 2) {
      GitProxy(args[0], args[1]);
    }
    else {
      System.err.printf("cmd args error:{%s}\n", Arrays.toString(args));
    }
  }

  static void GitProxy(String cmd, String port) {
    var sb = new StringBuilder();
    sb.append("git config --global http.proxy http://127.0.0.1:");
    sb.append(port);
    CLibrary.INSTANCE.system(sb.toString());

    sb = new StringBuilder();
    sb.append("git config --global https.proxy https://127.0.0.1:");
    sb.append(port);
    CLibrary.INSTANCE.system(sb.toString());

    CLibrary.INSTANCE.system(cmd);

    CLibrary.INSTANCE.system("git config --global --unset http.proxy");
    CLibrary.INSTANCE.system("git config --global --unset https.proxy");
  }
}
