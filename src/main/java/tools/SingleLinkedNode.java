package tools;

public class SingleLinkedNode<V>  {
    private V val ;
    private SingleLinkedNode<V> parent;

    public SingleLinkedNode(){}
    public SingleLinkedNode(V val){
        this.val = val;
        this.parent = null;
    }

    public SingleLinkedNode<V> getParent(){ return this.parent;}
    public void setParent(SingleLinkedNode<V> p) { this.parent = p; }

    public V getContent(){ return this.val; }
    public void setContent(V val) { this.val = val; }
}
