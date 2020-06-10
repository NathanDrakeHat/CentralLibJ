package algorithms;
// minimum spanning tree
public class MST {
    public static class Vertex{
        private final String name;

        public Vertex(String n) { name = n; }

        public String getName() { return name; }

        @Override
        public boolean equals(Object other_vertex){
            if(other_vertex instanceof Vertex){
                return name.equals(((Vertex) other_vertex).name);
            }else return false;
        }

        @Override
        public int hashCode(){ return name.hashCode(); }
    }
}
