package org.nathan.algsJ.dataStruc;

import org.jetbrains.annotations.NotNull;
import org.nathan.centralUtils.utils.LambdaUtils;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class BSTIterator<Node, Target> implements Iterator<Target> {
  private final Deque<Node> stack = new LinkedList<>();
  private Node ptr;
  private boolean poppedBefore = false;
  private boolean finish = false;
  @NotNull
  private final Function<Node, Target> getTarget;
  @NotNull
  private final Node sentinel;
  @NotNull
  private final Function<Node, Node> getLeft;
  @NotNull
  private final Function<Node, Node> getRight;
  @NotNull
  private final LambdaUtils.Gettable<Boolean> iterating;

  public BSTIterator(
          @NotNull Node sentinel,
          @NotNull Function<Node, Target> getTarget,
          @NotNull LambdaUtils.Gettable<Node> getRoot,
          @NotNull Function<Node, Node> getRight,
          @NotNull Function<Node, Node> getLeft,
          @NotNull LambdaUtils.Gettable<Boolean> getIterating) {
    this.getTarget = getTarget;
    this.sentinel = sentinel;
    ptr = getRoot.get();
    this.getRight = getRight;
    this.getLeft = getLeft;
    this.iterating = getIterating;
    if (ptr == sentinel) {
      finish = true;
    }
  }

  @Override
  public boolean hasNext() {
    if (!iterating.get()) {
      throw new IllegalStateException("concurrent modification");
    }
    return !finish && ptr != null;
  }

  @Override
  public Target next() {
    while (ptr != null) {
      if (getLeft.apply(ptr) != sentinel && !poppedBefore) {
        stack.push(ptr);
        ptr = getLeft.apply(ptr);
      }
      else {
        var t = ptr;
        if (getRight.apply(ptr) != sentinel) {
          ptr = getRight.apply(ptr);
          poppedBefore = false;
        }
        else {
          if (stack.size() != 0) {
            ptr = stack.pop();
            poppedBefore = true;
          }
          else {
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
