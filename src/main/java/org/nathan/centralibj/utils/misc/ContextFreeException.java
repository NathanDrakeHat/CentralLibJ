package org.nathan.centralibj.utils.misc;

public class ContextFreeException extends RuntimeException{
  public final String Message;

  public ContextFreeException(){
    Message = null;
  }

  @Override
  public Throwable fillInStackTrace(){
    return this;
  }

  public ContextFreeException(String s){
    Message = s;
  }
}
