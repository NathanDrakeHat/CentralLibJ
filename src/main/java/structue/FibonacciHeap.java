package structue;

import java.util.HashSet;

import tool.GenericArray;
import tool.IntegerPair;

public class FibonacciHeap<V> {
    private Node root_list = null;
    private int number = 0; // number of nodes
    private class Node implements Comparable<Node>{
        private final IntegerPair<V> content;
        private Node parent = null;
        private Node childList = null; // int linked, circular list
        private Node left = this;
        private Node right = this;
        private int degree = 0; // number of children
        private boolean mark = false; // whether the node had lost a child when it be made another node's child

        private Node(int key, V val) { content = new IntegerPair<>(key, val); }
        
        private Node(int key) { content = new IntegerPair<V>(key);}

        private Node(int key, boolean m){
            this.content = new IntegerPair<V>(key);
            mark = m;
        }

        public Node getLeft() { return left; }

        public Node getRight() { return right; }

        public int getKey() { return content.getKey(); }

        private void setKey(int key) { content.setKey(key); }
        
        public V getValue() { return content.getValue(); }

        public Node getParent() { return parent; }

        private void setParent(Node p) { parent = p; }

        private void setValue(V val) { content.setValue(val); }

        private Node chainAdd(int t, boolean m){
            var x = new Node(t, m);
            var list_left = left;
            left = x;
            x.right = this;
            list_left.right = x;
            x.left = list_left;
            x.parent = this.parent;
            return this;
        }

        private Node chainAdd(int t){
            return chainAdd(t, false);
        }

        private void setChildList(Node x){
            childList = x;
            if(x != null) x.parent = this;
        }

        public Node getChildList() { return childList; }

        @Override
        public int compareTo(Node other){ return content.getKey() - other.content.getKey(); }
    }
    private static FibonacciHeap<Double> buildExample(){
        var H = new FibonacciHeap<Double>();
        H.insert(3);
        var m = H.root_list;
        m.chainAdd(17).chainAdd(24).chainAdd(23).chainAdd(7).chainAdd(21);
        m.setChildList( H.new Node(18, true));
        m.degree = 2;
        var m_child = m.getChildList();
        m_child.degree = 1;
        m_child.setChildList(H.new Node(39, true));
        m_child.chainAdd(52).chainAdd(38);
        m_child.left.setChildList( H.new Node(41));
        m_child.left.degree = 1;
        m.right.setChildList(H.new Node(30));
        m.right.degree = 1;
        var t = H.new Node(26, true);
        t.degree = 1;
        t.setChildList(H.new Node(35));
        m.right.right.setChildList(t);
        m.right.right.degree = 2;
        t.chainAdd(46);
        H.number = 15;
        return H;
    }
    private static void bilinearPrintChildList(FibonacciHeap<Double>.Node t){
        var p = t;
        do{
            System.out.print(p.getKey());
            System.out.print(" ");
            p = p.right;
        }while(p != t);
        System.out.print("| ");
        p = t;
        do{
            System.out.print(p.getKey());
            System.out.print(" ");
            p = p.left;
        }while(p != t);
        System.out.print('\n');
    }
    public static void test() {
        var H = buildExample();
        var o = H.extractMin();
        System.out.printf("Min node key: %d\n", o.getKey());
        //see <<introduction to  algorithm>> to find this test sample.
        bilinearPrintChildList(H.root_list);
        bilinearPrintChildList(H.root_list.right.getChildList());
        bilinearPrintChildList(H.root_list.right.getChildList().left.getChildList());
        bilinearPrintChildList(H.root_list.getChildList());
        bilinearPrintChildList(H.root_list.getChildList().left.getChildList());
        bilinearPrintChildList(H.root_list.getChildList().left.left.getChildList());
        bilinearPrintChildList(H.root_list.getChildList().left.getChildList().getChildList());
        bilinearPrintChildList(H.root_list.right.right.getChildList());
        /* answer:
        7 18 38 | 7 38 18
        39 21 | 39 21
        52 | 52
        23 17 24 | 23 24 17
        26 46 | 26 46
        30 | 30
        35 | 35
        41 | 41
        * */
        System.out.printf("Decrease %d to 15\n", H.root_list.getChildList().left.getChildList().left.getKey());
        H.decreaseKey(H.root_list.getChildList().left.getChildList().left, 15);
        System.out.printf("Decrease %d to 5\n", H.root_list.getChildList().left.childList.childList.getKey());
        H.decreaseKey(H.root_list.getChildList().left.childList.childList, 5);
        bilinearPrintChildList(H.root_list);;
        bilinearPrintChildList(H.root_list.right.right.right.childList);
        bilinearPrintChildList(H.root_list.right.right.right.childList.right.childList);
        bilinearPrintChildList(H.root_list.right.right.right.right.childList);
        /*
        Decrease 46 to 15
        Decrease 35 to 5
        5 26 24 7 18 38 15 | 5 15 38 18 7 24 26
        23 17 | 23 17
        30 | 30
        39 21 | 39 21
        */
    }

    public int minKey() { return root_list.getKey(); }
    public V minValue() { return root_list.getValue(); }

    private void insert(Node x){
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
    private void insert(int p){ insert(new Node(p)); }
    public void insert(int key, V val){ insert(new Node(key, val)); }

    public IntegerPair<V> extractMin() {
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
        }else return null;
        return z.content;
    }
    private void consolidate(){
        var A = new GenericArray<Node>(upperBound()+1);
        var w = root_list;
        if(w == null) return;
        var dict = new HashSet<Node>(32);
        do{ // for w in root list>>
            var x = w; // x current node
            var next = x.right;
            int d = x.degree;
            while(A.get(d) != null){
                var y = A.get(d); // y stored node
                if(x.compareTo(y) > 0) { // exchange pointer
                    var t = x;
                    x = y;
                    y = t;
                }
                linkTo(y, x);
                A.set(d, null);
                d++;
            }
            A.set(d, x);
            // for w in root list<<
            dict.add(w);
            w = next;

        }while(!dict.contains(w));
        dict.clear();
        root_list = null;
        for(int i = 0; i <= upperBound(); i++){
            var t = A.get(i);
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
        if(f1.root_list == null | f2.root_list == null){
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

    public void decreaseKey(Node x, int new_key){
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
    private void cut(Node x, Node y){
        if(x == null & y == null) throw new IllegalArgumentException("Two arg is null");
        else if(x == null) throw new IllegalArgumentException("First arg is null");
        else if(y == null ) throw new IllegalArgumentException("Second arg is null.");
        removeNodeFromList(x);
        y.degree--;
        addNodeToList(x, rootList());
        x.mark = false;
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

    public void delete(Node x){
        if(x == null) throw new IllegalArgumentException("Can not delete null.");
        decreaseKey(x, minKey() - 1);
        extractMin();
    }

    private Node rootList(){ return root_list; }
    private void addNodeToList(Node x, Node list){
        if(x == null & list == null) throw new IllegalArgumentException("Two arg is null");
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
    private int upperBound(){ return (int)(Math.log(number)/Math.log(2)); }
}
