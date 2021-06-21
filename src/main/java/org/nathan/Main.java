package org.nathan;

import org.nathan.centralUtils.utils.LambdaUtils;
import org.nathan.centralUtils.utils.NativeUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicIntegerArray;


class Main {
  static class GitPush {
    public static void gitPush(String[] args) {
      NativeUtils.GitProxy(args);
    }
  }


  public static void main(String[] args) {
    if (args.length > 0) {
      GitPush.gitPush(args);
    }
    LambdaUtils.stripCE(Main::exceptionMethod);
  }

  public static void exceptionMethod() throws IOException {
    System.out.println("run");
    another();
    throw new IOException();
  }

  public static void another() throws IOException {
    System.out.println("another");
    throw new IOException();
  }
}
