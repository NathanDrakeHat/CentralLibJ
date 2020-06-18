package structures;

import java.lang.reflect.Array;
import java.util.*;

// dynamic minimum priority queue
// key must be a number
public final class FibonacciHeap<V> {
    private Node root_list = null;
    private int number = 0; // number of nodes
    void setNumber(int n) { this.number = n; }
    class Node implements Comparable<Node>{
        private double key;
        private V value;
        private Node parent = null;
        private Node childList = null; // int linked, circular list
        private Node left = this;
        private Node right = this;
        private int degree = 0; // number of children
        private boolean mark = false; // whether the node had lost a child when it be made another node's child

        private Node(double key, V val) {
            this.key = key;
            this.value = val;
        }

        public Node getLeft() { return left; }
        public Node getRight() { return right; }

        public double getKey() { return key; }
        private void setKey(double key) { this.key = key; }
        
        public V getValue() { return value; }
        private void setValue(V val) { this.value = val; }

        public Node getParent() { return parent; }
        private void setParent(Node p) { parent = p; }

        void setChildList(Node x){
            childList = x;
            if(x != null) x.parent = this;
        }

        public Node getChildList() { return childList; }

        @Override
        public int compareTo(Node other){
            var t =  (int) (key - other.key);
            if(t == 0) return value.equals(other.value)? 0 : value.hashCode() - other.value.hashCode();
            else return t;
        }

        @Override
        public int hashCode(){
            return Objects.hash(key,value);
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object other){
            if(other == null) return false;
            else if(getClass().equals(other.getClass())){
                return (key == ((Node) other).key) && (value.equals(((Node)other).value));
            }else return false;
        }

        Node(double key) { this.key = key;}
        Node(double key, boolean m){
            this.key = key;
            mark = m;
        }
        Node chainAdd(int t, boolean m){
            var x = new Node(t, m);
            var list_left = left;
            left = x;
            x.right = this;
            list_left.right = x;
            x.left = list_left;
            x.parent = this.parent;
            return this;
        }
        Node chainAdd(int t){
            return chainAdd(t, false);
        }
        void setDegree(int d) { this.degree = d; }
    }
    private final Map<V,Node> value_Node_map = new HashMap<>();

    public double minKey() { return root_list.getKey(); }
    public V minValue() { return root_list.getValue(); }

    private void insert(Node x){
        value_Node_map.put(x.getValue(),x);
        if(root_list == null){
            root_list = x;
        }else{
            addNodeToList(x, root_list); // add x to root list
            if(x.compareTo(root_list) < 0){
                root_list = x;
            }
        }
        number++;
    }
    public void insert(double key, V val){ insert(new Node(key, val)); }

    public V extractMin() {
        var z = rootList();
        if(z != null){
            var child_list = z.getChildList(); // add root_list's children list to root list
            var p = child_list;
            if(p != null) {
                do {
                    var t = p.right;
                    p.parent = null;
                    addNodeToList(p, root_list);
                    p = t;
                } while (p != child_list);
            }
            root_list = z.right;
            removeNodeFromList(z);
            if(z == root_list) root_list = null;
            else consolidate();
            number--;
        }else throw new NoSuchElementException();
        value_Node_map.remove(z.getValue());
        return z.getValue();
    }
    private void consolidate(){
        @SuppressWarnings("unchecked")
        Node[] A = (Node[])Array.newInstance(Node.class, upperBound()+1);
        var w = root_list;
        if(w == null) return;
        var dict = new HashSet<Node>(32);
        do{ // for w in root list start
            var x = w; // x current node
            var next = x.right;
            int d = x.degree;
            while(A[d] != null){
                var y = A[d]; // y stored node
                if(x.compareTo(y) > 0) { // exchange pointer
                    var t = x;
                    x = y;
                    y = t;
                }
                linkTo(y, x);
                A[d] = null;
                d++;
            }
            A[d] = x;
            // for w in root list end
            dict.add(w);
            w = next;
        }while(!dict.contains(w));
        dict.clear();
        root_list = null;
        for(int i = 0; i <= upperBound(); i++){
            var t = A[i];
            if(t != null){
                if(root_list == null){
                    t.right = t;
                    t.left = t;
                    root_list = t;
                }else{
                    addNodeToList(t, root_list);
                    if(t.compareTo(root_list) < 0)  root_list = t;
                }
            }
        }
    }

    public FibonacciHeap<V> union(FibonacciHeap<V> f1, FibonacciHeap<V> f2){
        var res = new FibonacciHeap<V>();
        res.root_list = f1.root_list;
        if(f1.root_list == null || f2.root_list == null){
            throw new IllegalArgumentException("Can't union null ");
        }
        var f1_right = f1.root_list.right; // concatenate two root list
        var f2_left = f2.root_list.left;
        f1.root_list.right = f2.root_list;
        f2.root_list.left = f1.root_list;
        f1_right.left = f2_left;
        f2_left.right = f1_right;

        if(f2.root_list.compareTo(f1.root_list) < 0){
            res.root_list = f2.root_list;
        }
        res.number = f1.number + f2.number;
        return res;
    }

    public void decreaseKey(V val, double new_key){
        var x = value_Node_map.get(val);
        if(new_key > x.getKey())
            throw new IllegalArgumentException("New key should smaller than old key.");
        else{
            x.setKey(new_key);
            var y = x.getParent();
            if(y != null){
                if(x.getKey() < (y.getKey())) {
                    cut(x, y);
                    cascadingCut(y);
                }
            }
            if(x.getKey() < minKey()){
                root_list = x;
            }
        }
    }
    void decreaseKey(Node x, double new_key){
        // move x to root list
        // set parent mark true if parent mark is false
        // else successively move true mark parents to root list
        if(x == null) throw new IllegalArgumentException("Null arg.");
        if(new_key > x.getKey())
            throw new IllegalArgumentException("New key should smaller than old key.");
        else{
            x.setKey(new_key);
            var y = x.getParent();
            if(y != null){
                if(x.getKey() < (y.getKey())) {
                    cut(x, y);
                    cascadingCut(y);
                }
            }
            if(x.getKey() < minKey()){
                root_list = x;
            }
        }
    }
    private void cut(Node a, Node b){
        if(a == null && b == null) throw new IllegalArgumentException("Two arg is null");
        else if(a == null) throw new IllegalArgumentException("First arg is null");
        else if(b == null ) throw new IllegalArgumentException("Second arg is null.");
        removeNodeFromList(a);
        b.degree--;
        addNodeToList(a, rootList());
        a.mark = false;
    }
    private void cascadingCut(Node y){
        if(y == null) throw new IllegalArgumentException("Null arg.");
        var z = y.getParent();
        if(z != null){
            if(!y.mark) y.mark = true;
            else{
                cut(y, z);
                cascadingCut(z);
            }
        }
    }

    private void delete(Node x){
        if(x == null) throw new IllegalArgumentException("Can not delete null.");
        decreaseKey(x, minKey() - 1);
        extractMin();
    }

    public boolean contains(V x) { return value_Node_map.containsKey(x); }
    public int length() { return number; }
    Node rootList(){ return root_list; }
    private int upperBound(){ return (int)(Math.log(number)/Math.log(2)); }

    private void addNodeToList(Node x, Node list){
        if(x == null && list == null) throw new IllegalArgumentException("Two arg is null");
        else if(x == null) throw new IllegalArgumentException("First arg is null");
        else if(list == null ) throw new IllegalArgumentException("Second arg is null.");

        x.parent = list.parent;
        var list_left = list.left;
        list.left = x;
        x.right = list;
        list_left.right = x;
        x.left = list_left;
    }
    private void removeNodeFromList(Node z){
        if(z == null) throw new IllegalArgumentException("Receive a null arg.");
        var z_right = z.right;
        var z_left = z.left;
        if(z.parent != null){
            if(z.parent.childList == z) {
                if (z.right != z) z.parent.childList = z.right;
                else {
                    z.parent.childList = null;
                    z.right = z;
                    z.left = z;
                    z.parent = null;
                    return;
                }
            }
        }
        z_right.left = z_left;
        z_left.right = z_right;

        z.right = z;
        z.left = z;
        z.parent = null;
    }
    private void linkTo(Node l, Node m){ // larger, minor
        removeNodeFromList(l);
        m.degree++;
        if(m.getChildList() == null) m.setChildList(l);
        else addNodeToList(l, m.getChildList());
        l.mark = false;
    }

    @Override
    public String toString(){
        if(root_list!=null)
            return String.format("Min key: %f, value: %s",root_list.getKey(),root_list.getValue().toString());
        else return "Null Heap";
    }
}