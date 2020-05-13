package structue;

public class DisjointSet <V>{
    public class Node{
        Node parent;
        V content;
        int rank = 0;
    }
    Node root;
    public DisjointSet(){
        root = new Node();
        root.parent = root;
        root.rank = 0;
    }

    public void union(DisjointSet<V> other){
        link(findSet(this.root), findSet(other.root));
    }

    public void link(Node x, Node y){
        if(x.rank > y.rank)
            y.parent = x;
        else {
            x.parent = y;
            if(x.rank == y.rank) y.rank++;
        }
    }

    public Node findSet(Node x){
        if(x != x.parent)
            x.parent = findSet(x.parent);
        return x.parent;
    }
}
