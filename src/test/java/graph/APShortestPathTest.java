package graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class APShortestPathTest {

    @Test
    void slowAllPairsShortestPaths() {
        var res = APShortestPath.slowAllPairsShortestPaths(new double[][]{
                {0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,2,Double.POSITIVE_INFINITY},
                {3,0,4,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY},
                {8,Double.POSITIVE_INFINITY,0,-5,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,1,Double.POSITIVE_INFINITY,0,6},
                {-4,7,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,0}
        });
        var answer = new double[][]{
                {0,3,7,2,8},
                {1,0,4,-1,5},
                {-3,-4,0,-5,1},
                {2,1,5,0,6},
                {-4,-1,3,-2,0}
        };
        assertArrayEquals(answer,res);
    }

    @Test
    void fasterAllPairsShortestPaths() {
        var res = APShortestPath.fasterAllPairsShortestPaths(new double[][]{
                {0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,2,Double.POSITIVE_INFINITY},
                {3,0,4,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY},
                {8,Double.POSITIVE_INFINITY,0,-5,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,1,Double.POSITIVE_INFINITY,0,6},
                {-4,7,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,0}
        });
        var answer = new double[][]{
                {0,3,7,2,8},
                {1,0,4,-1,5},
                {-3,-4,0,-5,1},
                {2,1,5,0,6},
                {-4,-1,3,-2,0}
        };
        assertArrayEquals(answer,res);
    }


    @Test
    void algorithmFloydWarshallTest(){
        var res = APShortestPath.algorithmFloydWarshall(new double[][]{
                {0,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,2,Double.POSITIVE_INFINITY},
                {3,0,4,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY},
                {8,Double.POSITIVE_INFINITY,0,-5,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,1,Double.POSITIVE_INFINITY,0,6},
                {-4,7,Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,0}
        });
        var answer = new double[][]{
                {0,3,7,2,8},
                {1,0,4,-1,5},
                {-3,-4,0,-5,1},
                {2,1,5,0,6},
                {-4,-1,3,-2,0}
        };
        assertArrayEquals(answer,res);
    }

    @Test
    void transitiveClosureTest(){
        var res = APShortestPath.transitiveClosure(new double[][]{
                {0,                       Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY,1},
                {Double.POSITIVE_INFINITY,0                       ,1                        ,Double.POSITIVE_INFINITY},
                {Double.POSITIVE_INFINITY,1                       ,0                       ,1},
                {Double.POSITIVE_INFINITY,1                       ,Double.POSITIVE_INFINITY,0}
        });

        var answer = new boolean[][]{
                {true,true,true,true},
                {false,true,true,true},
                {false,true,true,true},
                {false,true,true,true}
        };
        assertArrayEquals(answer, res);
    }

    @Test
    void algorithmJohnson(){
        try{
            APShortestPath.algorithmJohnson(build(), new BFS.BFSVertex<>("0"));
        }catch (APShortestPath.NegativeCyclesException e){
            fail();
        }
    }
    static Graph<BFS.BFSVertex<String>> build(){
        String[] names = "1,2,3,4,5".split(",");
        List<BFS.BFSVertex<String>> vertices = new ArrayList<>();
        for(var name : names)
            vertices.add(new BFS.BFSVertex<>(name));
        var res = new Graph<>(vertices, Graph.Direction.DIRECTED);
        res.setNeighbor(vertices.get(0),vertices.get(1),3);
        res.setNeighbor(vertices.get(0),vertices.get(2),8);
        res.setNeighbor(vertices.get(0),vertices.get(4),-4);

        res.setNeighbor(vertices.get(1),vertices.get(3),1);
        res.setNeighbor(vertices.get(1),vertices.get(4),7);

        res.setNeighbor(vertices.get(2),vertices.get(1),4);

        res.setNeighbor(vertices.get(3),vertices.get(2),-5);

        res.setNeighbor(vertices.get(4),vertices.get(3),6);
        return res;
    }
}