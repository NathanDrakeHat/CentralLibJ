package tool;

public class TripleLinkedNode<V> implements TripleLinks<TripleLinkedNode<V>>, Vessel<V>{
    private V content;
    private TripleLinkedNode<V> left;
    private TripleLinkedNode<V> right;
    private  TripleLinkedNode<V> parent;

    @Override public TripleLinkedNode<V> getLeft() { return left; }
    @Override public void setLeft(TripleLinkedNode<V> l){ left = l; }

    @Override public TripleLinkedNode<V> getRight() { return right; }
    @Override public void setRight(TripleLinkedNode<V> r) { right = r; }

    @Override public TripleLinkedNode<V> getParent() { return parent; }
    @Override public void setParent(TripleLinkedNode<V> p) { parent = p; }

    @Override public V getContent() { return content; }
    @Override public void setContent(V c) { this.content = c;}
}
