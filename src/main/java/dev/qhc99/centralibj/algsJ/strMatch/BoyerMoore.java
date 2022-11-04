package dev.qhc99.centralibj.algsJ.strMatch;



public class BoyerMoore{

  public static int search( String txt,  String pat){
    return search(txt, pat, 256);
  }

  public static int search( String txt,  String pat, int radix){
    int[] right = new int[radix];
    for(int c = 0; c < radix; c++){
      right[c] = -1;
    }
    for(int j = 0; j < pat.length(); j++){
      right[pat.charAt(j)] = j;
    }

    int N = txt.length();
    int M = pat.length();
    int skip;
    for(int i = 0; i <= N - M; i += skip){
      skip = 0;
      for(int j = M - 1; j >= 0; j--){
        if(pat.charAt(j) != txt.charAt(i + j)){
          skip = Math.max(1, j - right[txt.charAt(i + j)]);
          break;
        }
      }
      if(skip == 0){ return i; }
    }
    return N;
  }
}
