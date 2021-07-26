package org.nathan.centralibj.utils;

import org.nathan.centralibj.utils.misc.Ref;

import java.util.Arrays;

public class ExceptionUtils{

  /**
   * get ith frame of stack trace
   * @param idx index from 0
   * @return ith frame of stack trace
   */
  public static StackWalker.StackFrame frameOf(int idx){
    if(idx < 0){
      throw new IllegalArgumentException();
    }

    var w = StackWalker.getInstance();
    Ref<StackWalker.StackFrame> refSf = Ref.none();
    w.walk(s -> {
      s.skip(1 + idx).limit(1).forEach(sf -> refSf.deRef = sf);
      return null;
    });

    return refSf.deRef;
  }

  public static final class CEStripper extends RuntimeException {
    private CEStripper(Exception e) {
      super(e);
    }

    @Override
    public String toString() {
      var str = super.toString();
      return str.substring(str.indexOf("\n") + 1);
    }

    public static <R> R raise(Exception e) throws RuntimeException {
      var cause_t = e.getStackTrace();
      var trace = Arrays.copyOfRange(cause_t, 0, cause_t.length - 1);
      trace[trace.length - 1] = cause_t[cause_t.length - 1];
      e.setStackTrace(trace);

      var ce = new CEStripper(e);
      var ce_trace = ce.getStackTrace();
      ce.setStackTrace(Arrays.copyOfRange(ce_trace, 2, ce_trace.length));
      throw ce;
    }
  }

  public static class ContextFreeException extends RuntimeException{
    public final String Message;

    public ContextFreeException(){
      Message = null;

    }

    @Override
    @Deprecated
    public Throwable fillInStackTrace(){
      return this;
    }

    public ContextFreeException(String s){
      Message = s;
    }
  }
}
