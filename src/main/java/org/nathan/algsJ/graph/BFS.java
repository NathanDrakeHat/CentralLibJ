package org.nathan.algsJ.graph;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * breath first search
 */
public final class BFS{
  public static <T> void breathFirstSearch(@NotNull LinkGraph<Vert<T>, BaseEdge<Vert<T>>> G, @NotNull BFS.Vert<T> s){
    var vs = G.allVertices();
    for(var v : vs){
      if(!v.equals(s)){
        v.color = COLOR.WHITE;
        v.distance = Double.POSITIVE_INFINITY;
        v.parent = null;
      }
    }
    s.color = COLOR.GRAY;
    s.distance = 0;
    s.parent = null;
    Queue<Vert<T>> Q = new LinkedList<>();
    Q.add(s);
    while(!Q.isEmpty()) {
      var u = Q.remove();
      var u_edges = G.adjacentEdgesOf(u);
      for(var edge : u_edges){
        var v = edge.another(u);
        if(v.color == COLOR.WHITE){
          v.color = COLOR.GRAY;
          v.distance = u.distance + 1;
          v.parent = u;
          Q.add(v);
        }
      }
      u.color = COLOR.BLACK;
    }
  }

  public static <T> List<T> getPath(@NotNull BFS.Vert<T> s, @NotNull BFS.Vert<T> v){
    List<T> t = new ArrayList<>();
    traverse(s, v, t);
    int idx = 0;
    List<T> res = new ArrayList<>(t.size());
    for(var i : t){
      res.add(idx++, i);
    }
    return res;
  }

  private static <T> void traverse(Vert<T> s, Vert<T> v, List<T> res){
    if(v == s){
      res.add(s.identity);
    }
    else if(v.parent != null){
      traverse(s, v.parent, res);
      res.add(v.identity);
    }
  }

  enum COLOR{WHITE, GRAY, BLACK}

  public static class Vert<ID> extends BaseVert<ID>{
    @Nullable
    BFS.Vert<ID> parent;
    double distance; // d
    COLOR color;

    Vert(@NotNull ID name){
      super(name);
    }

    Vert(){
      super(null);
    }

    public @Nullable BFS.Vert<ID> getParent(){
      return parent;
    }

    public double getDistance(){
      return distance;
    }

    @Override
    public String toString(){
      return String.format("BFS.Vertex: (%s)", identity != null ? identity.toString() : "()");
    }
  }
}
