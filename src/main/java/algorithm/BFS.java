package algorithm;

import jdk.jshell.spi.ExecutionControl;

import java.util.*;

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
        public Vertex vertex;
        public Node next;

        public Node(Vertex v){ vertex = v; }
        public Node getNext(){ return this.next;}
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

    public static char[] getPath(Node[] G, Vertex s, Vertex v){
        List<Character> t = new ArrayList<>();
        getPath(G, s, v, t);
        int idx = 0;
        char[] res = new char[t.size()];
        for(var i : t)
            res[idx++] = i;
        return res;
    }

    private static void getPath(Node[] G, Vertex s, Vertex v, List<Character> res){
        if(v == s){
            res.add(s.name);
        } else if(v.parent != null){
            getPath(G, s, v.parent, res);
            res.add(v.name);
        }
    }
}
