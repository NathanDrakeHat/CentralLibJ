package org.nathan.algorithmsJ.structures;


interface RBNode<Key>{
  boolean RED = false;
  boolean BLACK = true;

  Key getKey();

  RBNode<Key> getParent();

  void setParent(RBNode<Key> p);

  RBNode<Key> getLeft();

  void setLeft(RBNode<Key> l);

  RBNode<Key> getRight();

  void setRight(RBNode<Key> r);

  boolean getColor();

  default boolean isBlack(){
    return getColor() == BLACK;
  }

  default boolean isRed(){
    return getColor() == RED;
  }

  default void setRed(){
    setColor(RED);
  }

  default void setBlack(){
    setColor(BLACK);
  }

  void setColor(boolean color);
}
