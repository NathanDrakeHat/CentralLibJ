package algorithm;

import java.util.ArrayList;

public class ActivitySelection {
    // greedy algorithm
    public static void test(){
        Node res = recursiveActivitySelector(Data.s, Data.f);
        res.print();
        res = greedyActivitySelector(Data.s, Data.f);
        res.print();
    }

    public static class Node{
        private int val ;
        private Node next;

        Node(){}

        Node(int val){
            this.val = val;
            this.next = null;
        }

        Node(int val, Node n){
            this.val = val;
            this.next = n;
        }

        public Node getNext(){ return this.next;}

        public Node setNext(Node n){
            this.next = n;
            return this;
        }

        public Node setAndReturnNext(Node n){
            setNext(n);
            return getNext();
        }

        public int getValue(){ return this.val; }

        public void setValue(int val) { this.val = val; }

        public void print(){
            Node ptr = this;
            do {
                System.out.print(ptr.val);
                System.out.print(" ");
                ptr = ptr.getNext();
            } while (ptr != null);
        }
    }

    public static class Data{
        public static int[] s = {1, 3, 0, 5, 3, 5, 6, 8, 8, 2, 12};
        public static int[] f = {4, 5, 6, 7, 9, 9, 10, 11, 12, 14, 16};
    }

    public static Node recursiveActivitySelector(int[] s, int[] f){
        return new Node(0).setNext(recursiveActivitySelector(s, f, 0));
    }

    public static Node recursiveActivitySelector(int[] s, int[] f, int k){
        int m = k + 1;
        if(k >= s.length | m >= s.length){
            return null;
        }
        while (m < s.length & s[m] < f[k]) { // f is sorted
            m++;
        }
        return new Node(m).setNext(recursiveActivitySelector(s, f, m));
    }

    public static Node greedyActivitySelector(int[] s, int[] f){
        int len = s.length;
        Node ptr = new Node(0);
        Node root = ptr;
        int f_idx = 0;
        for(int p = 1; p < len; p++){
            if(s[p] >= f[f_idx]){
                ptr = ptr.setAndReturnNext(new Node(p));
                f_idx = p;
            }
        }
        return root;
    }

}

