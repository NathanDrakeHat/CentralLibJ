import jdk.jshell.spi.ExecutionControl;

public class BTree { // disk storage
    private int h; // height
    final private int t = 4; // minimum degree
    private BNode root;
    public static class BNode{
        private int n; // number of keys current stored
        private boolean leaf; // is leaf
        private double[] keys; // at least t - 1, at most 2t - 1 keys
        private BNode[] c; // at least t, at most 2t children

        public BNode(int t){
            this.keys = new double[t - 1];
            this.c = new BNode[t];
        }
    }
    public static class Tuple{
        final public BNode x;
        final public int i;

        public Tuple(BNode x, int i){
            this.x = x;
            this.i = i;
        }
    }

    public BNode getRoot() { return this.root; }

    private void setRoot(BNode x) { this.root = x; }

    public Tuple Search(BNode x, double key)  { // pseudo-code
        int i = 0;
        while (i < x.n & key > x.keys[i]) {
            i++;
        }
        if(i < x.n & key == x.keys[i]){
            return new Tuple(x, i);
        }else if(x.leaf){
            return null;
        }else{
            System.out.println("Reading disk to get c[i]");
            return Search(x.c[i], key);
        }
    }

    private void SplitChild(BNode x, int i){ // pseudo-code
        BNode z = new BNode(this.t);
        BNode y = x.c[i];
        z.leaf = y.leaf;
        z.n = t - 1;
        System.arraycopy(y.keys, this.t, z.keys, 0, t-1);
        if(!y.leaf){
            System.arraycopy(y.c, this.t, z.c, 0, t);
        }
        y.n = t - 1;
        // x.c right move 1 unit from i+1 to n+1
        if (x.n + 1 - i >= 0) // length check
            System.arraycopy(x.c, i, x.c, i + 1, x.n + 1 - i);
        x.c[i] = z;
        // x.keys right move 1 unit from i to n
        if (x.n - i - 1 >= 0)
            System.arraycopy(x.keys, i - 1, x.keys, i - 1 + 1, x.n - i - 1);
        x.keys[i-1] = y.keys[t-1];
        x.n++;
    }

    public void insert(double k) { // pseudo-code
        BNode r = getRoot();
        if (r.n == 2 * t - 1) {
            BNode s = new BNode(t);
            setRoot(s);
            s.leaf = false;
            s.n = 0;
            s.c[0] = r;
            SplitChild(s, 0);
            insert(s, k);
        } else {
            insert(root, k);
        }
    }

    private void insert(BNode x, double k){ // pseudo-code
        int i = x.n - 1;
        if(x.leaf){
            while (i >= 0 & k < x.keys[i]){
                x.keys[i+1] = x.keys[i];
                i--;
            }
            x.keys[i+1] = k;
            x.n++;
        }else{
            while(i >= 0 & k < x.keys[i]){
                i--;
            }
            i++;
            if(x.c[i].n == 2*t - 1){
                SplitChild(x, i);
                if(k > x.keys[i]){
                    i++;
                }
            }
            insert(x.c[i], k);
        }
    }

    public BNode delete(double k) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("No pseudo-code in book...");
    }
}
