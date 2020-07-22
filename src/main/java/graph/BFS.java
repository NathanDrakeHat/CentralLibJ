package graph;

import java.util.*;

// breath first search
public final class BFS {
    enum COLOR {WHITE, GRAY, BLACK}

    public static class BFSVertex<V> {
        BFSVertex<V> parent;
        private COLOR color;
        double distance; // d
        private final V content;
        private final String string;
        private final int hash_code;

        public BFSVertex(V name) {
            Objects.requireNonNull(name);
            this.content = name;
            string = String.format("BFS.Vertex: (%s)", content.toString());
            hash_code = string.hashCode();
        }

        BFSVertex() {
            content = null;
            string = "BFS.Vertex: ()";
            hash_code = string.hashCode();
        }

        public V getContent() {
            return content;
        }

        public BFSVertex<V> getParent() {
            return parent;
        }

        public double getDistance() {
            return distance;
        }

        @Override
        public boolean equals(Object other_vertex) {
            if (!(other_vertex instanceof BFSVertex)) {
                return false;
            }
            else if (other_vertex == this) {
                return true;
            }
            else {
                if (content == null) {
                    return ((BFSVertex<?>) other_vertex).content == null;
                }
                return content.equals(((BFSVertex<?>) other_vertex).content);
            }
        }

        @Override
        public int hashCode() {
            return hash_code;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public static <T> void breathFirstSearch(LinkedGraph<BFSVertex<T>> G, BFSVertex<T> s) {
        Objects.requireNonNull(G);
        Objects.requireNonNull(s);
        var vs = G.getAllVertices();
        for (var v : vs) {
            if (!v.equals(s)) {
                v.color = COLOR.WHITE;
                v.distance = Double.POSITIVE_INFINITY;
                v.parent = null;
            }
        }
        s.color = COLOR.GRAY;
        s.distance = 0;
        s.parent = null;
        Queue<BFSVertex<T>> Q = new LinkedList<>();
        Q.add(s);
        while (!Q.isEmpty()) {
            var u = Q.remove();
            var u_edges = G.getEdgesAt(u);
            for (var edge : u_edges) {
                var v = edge.getAnotherSide(u);
                if (v.color == COLOR.WHITE) {
                    v.color = COLOR.GRAY;
                    v.distance = u.distance + 1;
                    v.parent = u;
                    Q.add(v);
                }
            }
            u.color = COLOR.BLACK;
        }
    }

    public static <T> List<T> getPath(BFSVertex<T> s, BFSVertex<T> v) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(v);
        List<T> t = new ArrayList<>();
        traverse(s, v, t);
        int idx = 0;
        List<T> res = new ArrayList<>(t.size());
        for (var i : t) {
            res.add(idx++, i);
        }
        return res;
    }

    private static <T> void traverse(BFSVertex<T> s, BFSVertex<T> v, List<T> res) {
        if (v == s) {
            res.add(s.content);
        }
        else if (v.parent != null) {
            traverse(s, v.parent, res);
            res.add(v.content);
        }
    }
}
