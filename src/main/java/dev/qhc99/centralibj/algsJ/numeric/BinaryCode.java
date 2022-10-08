package dev.qhc99.centralibj.algsJ.numeric;

public class BinaryCode {
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
}
