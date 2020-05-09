package algorithm;

public class DFS {
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Node{
        public static class Vertex{
            private Vertex parent;
            private COLOR color;
            public int d; //discovered time
            public int f; // finished time
            private char name;

            Vertex(char name){ this.name = name; }

            public char getName() {return name;}

            public boolean equals(Vertex other){ return name == other.getName(); }
        }
        public Vertex vertex;
        public Node next;
        public Node(Vertex v){ vertex = v; }
    }

    public static void depthFirstSearch(Node[] G) {
        for (Node n : G) {
            n.vertex.color = COLOR.WHITE;
            n.vertex.parent = null;
        }
        int time = 0;
        for(Node n : G){
            var u = n.vertex;
            if(u.color == COLOR.WHITE){
                time = depthFirstSearchVisit(G, u, time);
            }
        }
    }
    private static int depthFirstSearchVisit(Node[] G, Node.Vertex u, int time){
        time++;
        u.d = time;
        u.color = COLOR.GRAY;

        int i = 0; // find u container
        Node t = G[i];
        while(!t.vertex.equals(u)){
            t = G[++i];
        }

        t = t.next;
        while(t != null){ // search u adjacent vertex
            var v = t.vertex;
            if(v.color == COLOR.WHITE){
                v.parent = u;
                time = depthFirstSearchVisit(G, v, time);
            }
            t = t.next;
        }
        u.color = COLOR.BLACK;
        time++;
        u.f = time;
        return time;
    }

    public static void typologicalSort(Node[] G){
        // 1.call depthFirstSearch to compute f
        // 2.sort f in descend order
    }
    public static void stronglyConnectedComponents(Node[] G){
        // 1.call depthFirstSearch on G to compute f
        // 2.compute G^T which is reverse directed G
        // 3.call depthFirstSearch on G^T in the order of decreasing f
        // 4.output forest formed by line 3
    }
}
