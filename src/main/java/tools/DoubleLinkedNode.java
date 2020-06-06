package tools;

public class DoubleLinkedNode<V> implements DoubleLinks<DoubleLinkedNode<V>>, Vessel<V> {
    private DoubleLinkedNode<V> left;
    private DoubleLinkedNode<V> right;
    private V content;

    @Override public DoubleLinkedNode<V> getLeft(){return this.left;}
    @Override public void setLeft(DoubleLinkedNode<V> l) { this.left = l; }

    @Override public DoubleLinkedNode<V> getRight(){return this.right;}
    @Override public void setRight(DoubleLinkedNode<V> r) { this.right = r; }

    @Override public V getContent(){return this.content;}
    @Override public void setContent(V c) { this.content = c; }
}
