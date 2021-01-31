package org.nathan.AlgorithmsJava.tools;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class MinHeapTest {

    public static List<Integer> randomIntegerList(int low, int high, int len){
        List<Integer> l = new ArrayList<>();
        var rand = new Random();
        for (int i = low; i < high; i++){
            l.add(rand.nextInt(high - low)+low);
        }
        Collections.shuffle(l);
        return l;
    }

    public static void serializeIntegerList(List<Integer> t) throws IOException {
        StringBuilder file_name = new StringBuilder("ListOfInteger");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        file_name.append(dtf.format(now));
        file_name.append(".txt");
        FileOutputStream file_out=new FileOutputStream(file_name.toString());
        ObjectOutputStream out=new ObjectOutputStream(file_out);
        out.writeObject(t);
        out.flush();
        out.close();
    }

    @Test
    void randomBuildTest() {
        for (int i = 0; i < 10; i++){
            List<Integer> l = randomIntegerList(1,256,63);
            MinHeap<Integer,String> m = new MinHeap<>(l.stream().map(String::valueOf).collect(Collectors.toList()), Integer::valueOf,Integer::compare);
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0){
                res.add(Integer.valueOf(m.extractMin()));
            }
            for(int j = 0; j < res.size() - 1; j++){
                if(res.get(j).compareTo(res.get(j+1)) <= 0){
                    assertTrue(true);
                }
                else{

                    fail();
                }
            }
        }
    }

    @Test
    void randomAddTest(){
        for (int i = 0; i < 10; i++){
            List<Integer> l = randomIntegerList(1,256,63);
            MinHeap<Integer,String> m = new MinHeap<>(Integer::compare);
            for (Integer integer : l) {
                m.Add(String.valueOf(integer), integer);
            }
            List<Integer> res = new ArrayList<>();
            while (m.heapSize() > 0){
                res.add(Integer.valueOf(m.extractMin()));
            }
            for(int j = 0; j < res.size() - 1; j++){
                if(res.get(j).compareTo(res.get(j+1)) <= 0){
                    assertTrue(true);
                }
                else{
                    System.out.println(res);
                    fail();
                }
            }
        }
    }

    // TODO updateKeyTest
    @Test
    void randomUpdateKeyTest(){

    }
}