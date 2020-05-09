public class DFS {
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex{
        private Vertex parent;
        private COLOR color;
        private int d; //discovered time
        private int f; // finished time
        private char name;

        Vertex(char name){ this.name = name; }

        public char getName() {return name;}

        public boolean equals(Vertex other){ return name == other.getName(); }
    }
    public static class Node{
        private Vertex vertex;
        private Node next;

        public Node(Vertex v){ vertex = v; }

        public Node getNext(){ return this.next;}

        public Vertex getVertex(){ return vertex; }


    }

    public static class Data{
        public static String names = "uvwxyz";
        public static Node[] makeGraph(){
            Node[] G = new Node[6];
            for(int i = 0; i < 6; i++){
                G[i] = new Node(new Vertex(names.charAt(i)));
            }
            G[0].next = new Node(G[1].vertex);
            G[0].next.next = new Node(G[3].vertex);

            G[1].next = new Node(G[4].vertex);

            G[2].next = new Node(G[4].vertex);
            G[2].next.next = new Node(G[5].vertex);

            G[3].next = new Node(G[1].vertex);

            G[4].next = new Node(G[3].vertex);

            G[5].next = new Node(G[5].vertex);
            return G;
        }
    }

    public static void test(){
        Node[] G = Data.makeGraph();
        depthFirstSearch(G);
        for(Node n : G){
            System.out.print(n.vertex.d);
            System.out.print(" ");
            System.out.println(n.vertex.f);
        }
    }

    public static void depthFirstSearch(Node[] G) {
        for (Node n : G) {
            n.vertex.color = COLOR.WHITE;
            n.vertex.parent = null;
        }
        int time = 0;
        for(Node n : G){
            Vertex u = n.vertex;
            if(u.color == COLOR.WHITE){
                time = depthFirstSearchVisit(G, u, time);
            }
        }
    }
    private static int depthFirstSearchVisit(Node[] G, Vertex u, int time){
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
            Vertex v = t.vertex;
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
