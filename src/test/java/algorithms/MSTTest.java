package algorithms;

import tools.WeightedGraph;

import static org.junit.jupiter.api.Assertions.*;

class MSTTest {
    static WeightedGraph<String> buildExample(){
        String n = "a b c d e d g h i";
        String[] names = n.split("");
        int len = names.length;
        MST.Vertex[] vertexes = new MST.Vertex[len];
        for(int i = 0; i < len; i++){ vertexes[i] = new MST.Vertex(names[i]); }
        WeightedGraph<String> res = new WeightedGraph<>();

        return res;
    }
}