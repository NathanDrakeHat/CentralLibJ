package dev.qhc99.centralibj.algsJ.dataStruc;

import dev.qhc99.centralibj.utils.LambdaUtils;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class BSTIterator<Node, Format> implements Iterator<Format>{
  private final Deque<Node> stack = new LinkedList<>();
  private Node ptr;
  private boolean poppedBefore = false;
  private boolean finish = false;
  
  private final Function<Node, Format> getTarget;
  
  private final Node sentinel;
  
  private final Function<Node, Node> getLeft;
  
  private final Function<Node, Node> getRight;
  
  private final LambdaUtils.Gettable<Boolean> iterating;

  public BSTIterator(
           Node sentinel,
           Function<Node, Format> getTarget,
           LambdaUtils.Gettable<Node> getRoot,
           Function<Node, Node> getRight,
           Function<Node, Node> getLeft,
           LambdaUtils.Gettable<Boolean> getIterating){
    this.getTarget = getTarget;
    this.sentinel = sentinel;
    ptr = getRoot.get();
    this.getRight = getRight;
    this.getLeft = getLeft;
    this.iterating = getIterating;
    if(ptr == sentinel){
      finish = true;
    }
  }

  @Override
  public boolean hasNext(){
    if(!iterating.get()){
      throw new IllegalStateException("concurrent modification");
    }
    return !finish && ptr != null;
  }

  @Override
  public Format next(){
    while(ptr != null) {
      if(getLeft.apply(ptr) != sentinel && !poppedBefore){
        stack.push(ptr);
        ptr = getLeft.apply(ptr);
      }
      else{
        var t = ptr;
        if(getRight.apply(ptr) != sentinel){
          ptr = getRight.apply(ptr);
          poppedBefore = false;
        }
        else{
          if(stack.size() != 0){
            ptr = stack.pop();
            poppedBefore = true;
          }
          else{
            ptr = null;
          }
        }
        return getTarget.apply(t);
      }
    }
    finish = true;
    throw new NoSuchElementException("Iterate finish.");
  }
}
