package algorithm;

import jdk.jshell.spi.ExecutionControl;

import java.util.*;

public class BFS {
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Graph{
        public static class Vertex{
            private Vertex parent;
            private COLOR color;
            private double d; //distance
            private final char name;

            Vertex(char name){ this.name = name; }
            public boolean equals(Vertex other){ return name == other.name; }
        }
        public static class Node{
            public Vertex vertex;
            public Node next;
            private Node(Vertex v){ vertex = v; }
            public boolean equals(Node other) { return vertex.equals(other.vertex); }
        }
        Node[] Vertexs;
        public Graph(int s) { Vertexs = new Node[s]; }
        public Node buildNext(Vertex v){
            return new Node(v);
        }
    }

    public static void breathFirstSearch(Graph G, Graph.Vertex s) {
        for(var n : G.Vertexs){
            var v = n.vertex;
            if(!v.equals(s)){
                v.color = COLOR.WHITE;
                v.d = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.d = 0;
        s.parent = null;
        Queue<Graph.Vertex> Q = new LinkedList<>();
        Q.add(s);
        while(!Q.isEmpty()){
            Graph.Vertex u = Q.remove();

            int i = 0; // find u container
            var n = G.Vertexs[i];
            while(!n.vertex.equals(u)){
                n = G.Vertexs[++i];
            }
            n = n.next;
            while(n != null){ // search u adjacent vertex
                var v = n.vertex;
                if(v.color == COLOR.WHITE){
                    v.color = COLOR.GRAY;
                    v.d = u.d + 1;
                    v.parent = u;
                    Q.add(v);
                }
                n = n.next;
            }
            u.color = COLOR.BLACK;
        }
    }

    public static void printPath(Graph G, Graph.Vertex s, Graph.Vertex v){
        if(v == s){
            System.out.println(s.name);
        }else if(v.parent == null){
            System.out.println("no path from 's' to 'v' exists.");
        }else{
            printPath(G, s, v.parent);
            System.out.println(v.name);
        }
    }

    public static char[] getPath(Graph G, Graph.Vertex s, Graph.Vertex v){
        List<Character> t = new ArrayList<>();
        getPath(G, s, v, t);
        int idx = 0;
        char[] res = new char[t.size()];
        for(var i : t)
            res[idx++] = i;
        return res;
    }
    private static void getPath(Graph G, Graph.Vertex s, Graph.Vertex v, List<Character> res){
        if(v == s){
            res.add(s.name);
        } else if(v.parent != null){
            getPath(G, s, v.parent, res);
            res.add(v.name);
        }
    }
}
