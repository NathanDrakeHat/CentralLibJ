package org.nathan.centralibj.utils.misc;

import java.util.Arrays;

public final class CEStripper extends RuntimeException {
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
