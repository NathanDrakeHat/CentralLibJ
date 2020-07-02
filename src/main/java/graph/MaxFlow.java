package graph;

import java.util.Objects;

public class MaxFlow {
    public static class FlowVertex<V>{
        private final V content;
        private double e; // excess
        private int h; // height
        private final int hash;

        public FlowVertex(V content){
            Objects.requireNonNull(content);
            this.content = content;
            hash = content.hashCode();
        }

        @Override
        public boolean equals(Object other){
            if(other == this) return true;
            if(!(other instanceof FlowVertex)) return false;
            return ((FlowVertex<?>) other).content.equals(content);
        }

        @Override
        public int hashCode(){ return hash; }

        @Override
        public String toString() { return String.format("FlowVertex: %s",content.toString()); }
    }
    private static <T> void push(FlowVertex<T> u, FlowVertex<T> v, FlowGraph<FlowVertex<T>> graph){
        var delta = Math.min(u.e, graph.getResidualCapacity(u,v));
        var o1 = graph.tryGetFlowEdge(u,v);
        if(o1.isPresent()){
            var e = o1.get();
            e.flow += delta;
        }else{
            var o2 = graph.tryGetFlowEdge(v,u);
            if(o2.isPresent()){
                o2.get().flow -= delta;
            }else{
                throw new AssertionError();
            }
        }
        u.e -= delta;
        v.e += delta;
    }
}
