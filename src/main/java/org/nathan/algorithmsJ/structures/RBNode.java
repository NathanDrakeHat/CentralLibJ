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

  void setColor(boolean color);
}
