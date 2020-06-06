package tool;

public class SingleLinkedNode<V> implements SingleLink<SingleLinkedNode<V>>, Vessel<V> {
    private V val ;
    private SingleLinkedNode<V> next;

    public SingleLinkedNode(){}
    public SingleLinkedNode(V val){
        this.val = val;
        this.next = null;
    }

    @Override public SingleLinkedNode<V> getParent(){ return this.next;}
    @Override public void setParent(SingleLinkedNode<V> vSingleLinkedNode) { this.next = vSingleLinkedNode; }

    @Override public V getContent(){ return this.val; }
    @Override public void setContent(V val) { this.val = val; }
}
