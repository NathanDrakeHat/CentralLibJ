public class MatrixChain { // dynamic programming

    public static void test(){
        MatrixChainResult res = matrixChainOrder(Data.demo);
        res.print();
        if(res.min_cost == Data.answer){
            System.out.println("Pass.");
        }else{
            System.out.println("Not pass");
        }
    }

    public static class PairNode {
        // arithmetic tree
        public int a; // a * b
        public PairNode left;
        public PairNode right;

        PairNode(){}

        PairNode(int a){
            this.a = a;
        }
    }

    public static class MatrixChainResult{
        public int min_cost;
        private PairNode res;

        public void print(){
            print(this.res);
            System.out.println();
        }

        private void print(PairNode p){
            if(p.left != null | p.right != null)
                System.out.print("(");
            if(p.left != null){
                print(p.left);
            }
            if(p.right != null){
                print(p.right);
            }
            if(p.right == null & p.left == null)
                System.out.print(p.a + 1);
            if(p.left != null | p.right != null)
                System.out.print(")");
        }
    }

    private static class Data{
        private static int[][] a1 = new int[30][35];
        private static int[][] a2 = new int[35][15];
        private static int[][] a3 = new int[15][5];
        private static int[][] a4 = new int[5][10];
        private static int[][] a5 = new int[10][20];
        private static int[][] a6 = new int[20][25];
        public static int[][][] demo = {a1, a2, a3, a4, a5, a6};
        public static int answer = (35 * 15 * 5) + (30 * 35 * 5) + (5 * 10 * 20) + (5 * 20 * 25) + (30 * 5 * 25);
    }

    public static MatrixChainResult matrixChainOrder(int[][][] p){ // [start, end] [mid, end]
        MatrixChainResult[][] m = new MatrixChainResult[p.length][p.length]; // memory
        for(int i = 0; i < p.length; i++){
            m[i][i] = new MatrixChainResult();
            m[i][i].min_cost = 0;
            m[i][i].res = new PairNode(i);
        }
        for(int l = 2; l <= p.length; l++){ // len
            for(int s = 0; s + l - 1 < p.length; s++){ // start
                int e = s + l - 1; // end

                if(l == 2) {
                    m[s][e] = new MatrixChainResult();
                    m[s][e].min_cost = p[s].length * p[s][0].length * p[e][0].length;
                    m[s][e].res = new PairNode();
                    m[s][e].res.left = m[s][s].res;
                    m[s][e].res.right = m[e][e].res;
                }else {
                    m[s][e] = new MatrixChainResult();
                    m[s][e].min_cost = p[s].length * p[s][0].length * p[e][0].length + m[s][s].min_cost + m[s+1][e].min_cost;
                    m[s][e].res = new PairNode();
                    m[s][e].res.left = m[s][s].res;
                    m[s][e].res.right = m[s+1][e].res;
                    for (int i = 1; i < l - 1; i++) {
                        int cost = m[s][s+i].min_cost + m[s+i+1][e].min_cost + p[s].length * p[s+i+1].length * p[e][0].length;
                        if(cost < m[s][e].min_cost){
                            m[s][e].min_cost = cost;
                            m[s][e].res = new PairNode();
                            m[s][e].res.left = m[s][s+i].res;
                            m[s][e].res.right = m[s+i+1][e].res;
                        }
                    }
                }
            }
        }
        return m[0][p.length-1];
    }

}