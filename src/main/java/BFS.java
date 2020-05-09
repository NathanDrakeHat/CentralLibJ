import jdk.jshell.spi.ExecutionControl;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex{
        private Vertex parent;
        private COLOR color;
        private double d; //distance
        private char name;

        Vertex(char name){ this.name = name; }

        public double getDistance() { return this.d; }

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
        public static String names = "rstuvwxy";
        public static Node[] makeGraph(){
            Node[] G = new Node[8];
            for(int i = 0; i < 8; i++){
                G[i] = new Node(new Vertex(names.charAt(i)));
            }
            G[0].next = new Node(G[1].vertex);
            G[0].next.next = new Node(G[4].vertex);

            G[1].next = new Node(G[0].vertex);
            G[1].next.next = new Node(G[5].vertex);

            G[2].next = new Node(G[3].vertex);
            G[2].next.next = new Node(G[5].vertex);
            G[2].next.next.next = new Node(G[6].vertex);

            G[3].next = new Node(G[2].vertex);
            G[3].next.next = new Node(G[6].vertex);
            G[3].next.next.next = new Node(G[7].vertex);

            G[4].next = new Node(G[0].vertex);

            G[5].next = new Node(G[1].vertex);
            G[5].next.next = new Node(G[2].vertex);
            G[5].next.next.next = new Node(G[6].vertex);

            G[6].next = new Node(G[2].vertex);
            G[6].next.next = new Node(G[3].vertex);
            G[6].next.next.next = new Node(G[5].vertex);
            G[6].next.next.next.next = new Node(G[7].vertex);

            G[7].next = new Node(G[3].vertex);
            G[7].next.next = new Node(G[6].vertex);

            return G;
        }
    }

    public static void test() {
        Node[] G = Data.makeGraph();
        breathFirstSearch(G, G[1].vertex);
        printPath(G, G[1].vertex, G[7].vertex);
    }

    public static void breathFirstSearch(Node[] G, Vertex s) {
        for(Node n : G){
            if(n.vertex != s){
                n.vertex.color = COLOR.WHITE;
                n.vertex.d = Double.POSITIVE_INFINITY;
                n.vertex.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.d = 0;
        s.parent = null;
        Queue<Vertex> Q = new LinkedList<>();
        Q.add(s);
        while(!Q.isEmpty()){
            Vertex u = Q.remove();

            int i = 0; // find u container
            Node n = G[i];
            while(!n.vertex.equals(u)){
                n = G[++i];
            }
            n = n.next;
            while(n != null){ // search u adjacent vertex
                if(n.vertex.color == COLOR.WHITE){
                    n.vertex.color = COLOR.GRAY;
                    n.vertex.d = u.d + 1;
                    n.vertex.parent = u;
                    Q.add(n.vertex);
                }
                n = n.next;
            }
            u.color = COLOR.BLACK;
        }
    }

    public static void printPath(Node[] G, Vertex s, Vertex v){
        if(v == s){
            System.out.println(s.name);
        }else if(v.parent == null){
            System.out.println("no path from 's' to 'v' exists.");
        }else{
            printPath(G, s, v.parent);
            System.out.println(v.name);
        }
    }
}
