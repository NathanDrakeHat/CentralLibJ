package algorithms;

import tools.LinkedGraph;

import java.util.*;

public class BFS {
    // breath first search
    enum COLOR{ WHITE, GRAY, BLACK}
    public static class Vertex implements Comparable<Vertex>{
        private Vertex parent;
        private COLOR color;
        private double d; //distance
        private final char name; // generic

        Vertex(char name){ this.name = name; }

        public boolean equals(Vertex other){ return name == other.name; }

        @Override public int hashCode(){
            return name;
        }

        @Override public int compareTo(Vertex other) {
            return Character.compare(this.name, other.name);
        }
    }

    public static void breathFirstSearch(LinkedGraph<Vertex> G, Vertex s) {
        var vs = G.getVertexes();
        for (Vertex v : vs) {
            if (!v.equals(s)) {
                v.color = COLOR.WHITE;
                v.d = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.d = 0;
        s.parent = null;
        Queue<Vertex> Q = new LinkedList<>();
        Q.add(s);
        while(!Q.isEmpty()){
            Vertex u = Q.remove();
            for(var v : G.getNeighbors(u)){
                if(v.color == COLOR.WHITE){
                    v.color = COLOR.GRAY;
                    v.d = u.d + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.color = COLOR.BLACK;
        }
    }

    public static void printPath(Vertex s, Vertex v){
        if(v == s){
            System.out.println(s.name);
        }else if(v.parent == null){
            System.out.println("no path found.");
        }else{
            printPath(s, v.parent);
            System.out.println(v.name);
        }
    }
    public static char[] getPath( Vertex s, Vertex v){
        List<Character> t = new ArrayList<>();
        getPath(s, v, t);
        int idx = 0;
        char[] res = new char[t.size()];
        for(var i : t)
            res[idx++] = i;
        return res;
    }
    private static void getPath(Vertex s, Vertex v, List<Character> res){
        if(v == s){
            res.add(s.name);
        } else if(v.parent != null){
            getPath(s, v.parent, res);
            res.add(v.name);
        }
    }
}
