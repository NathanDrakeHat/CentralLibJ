package tools;

public class SingleLinkedNode<V> implements SingleLink<SingleLinkedNode<V>>, Vessel<V> {
    private V val ;
    private SingleLinkedNode<V> parent;

    public SingleLinkedNode(){}
    public SingleLinkedNode(V val){
        this.val = val;
        this.parent = null;
    }

    @Override public SingleLinkedNode<V> getParent(){ return this.parent;}
    @Override public void setParent(SingleLinkedNode<V> p) { this.parent = p; }

    @Override public V getContent(){ return this.val; }
    @Override public void setContent(V val) { this.val = val; }
}
