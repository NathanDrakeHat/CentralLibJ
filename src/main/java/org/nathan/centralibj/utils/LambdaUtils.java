package org.nathan.centralibj.utils;


import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.nathan.centralibj.utils.ExceptionUtils.CEStripper.raise;

public class LambdaUtils {

  @FunctionalInterface
  public interface TriConsumer<T1, T2, T3> {
    void accept(T1 t1, T2 t2, T3 t3);
  }

  @FunctionalInterface
  public interface QuaConsumer<T1, T2, T3, T4> {
    void accept(T1 t1, T2 t2, T3 t3, T4 t4);
  }


  @FunctionalInterface
  public interface TriadConsumer<T1, T2, T3, T4, T5> {
    void accept(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
  }

  @FunctionalInterface
  public interface TriFunction<T1, T2, T3, R> {
    R apply(T1 t1, T2 t2, T3 t3);
  }

  @FunctionalInterface
  public interface QuaFunction<T1, T2, T3, T4, R> {
    R apply(T1 t1, T2 t2, T3 t3, T4 t4);
  }

  @FunctionalInterface
  public interface TradFunction<T1, T2, T3, T4, T5, R> {
    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
  }

  @FunctionalInterface
  public interface CERunnable {
    void run() throws Exception;
  }

  @FunctionalInterface
  public interface Gettable<Item> {
    Item get();
  }

  /**
   * call on single statement
   *
   * @param runnable run
   */
  public static void stripCE(CERunnable runnable) {
    try {
      runnable.run();
    } catch (Exception e) {
      raise(e);
    }
  }

  /**
   * call on single statement
   *
   * @param runnable run
   * @param cat      catch lambda
   */
  public static void stripCE(CERunnable runnable, Consumer<Exception> cat) {
    try {
      runnable.run();
    } catch (Exception e) {
      cat.accept(e);
    }
  }

  /**
   * call on single statement
   *
   * @param callable call
   * @param <R>      return type
   * @return return of call
   */
  public static <R> R stripCE(Callable<R> callable) {
    try {
      return callable.call();
    } catch (Exception e) {
      return raise(e);
    }
  }

  /**
   * call on single statement
   *
   * @param callable call
   * @param cat      catch lambda
   * @param <R>      return type of call
   * @return return of call
   */
  public static <R> R stripCE(Callable<R> callable, Function<Exception, R> cat) {
    try {
      return callable.call();
    } catch (Exception e) {
      return cat.apply(e);
    }
  }


  public static <E1, E2, E3> E3 chain(E1 input, Function<E1, E2> func1, Function<E2, E3> func2) {
    return func2.apply(func1.apply(input));
  }

  public static <E1, E2, E3, E4> E4 chain(E1 input, Function<E1, E2> func1, Function<E2, E3> func2, Function<E3, E4> func3) {
    return func3.apply(func2.apply(func1.apply(input)));
  }

  public static <E1, E2, E3, E4, E5> E5 chain(
          E1 input,
          Function<E1, E2> func1,
          Function<E2, E3> func2,
          Function<E3, E4> func3,
          Function<E4, E5> func4) {
    return func4.apply(func3.apply(func2.apply(func1.apply(input))));
  }

}
