package structue;

public class ProtoVEBTree {
    //keys are not duplicate and confined in a range
    private int u; //universe size
    private boolean[] array;
    private ProtoVEBTree summary;
    private ProtoVEBTree[] cluster;

    private ProtoVEBTree(){}
    private ProtoVEBTree(int u, String s){
        if(!s.equals("universe")) throw new IllegalArgumentException();
        this.u = u;
        if(u > 2){
            cluster = new ProtoVEBTree[(int)Math.pow(u, 1/2.0)];
            summary = new ProtoVEBTree((int)Math.pow(u, 1/2.0), "universe");
            for(int i = 0; i < cluster.length; i++)
                cluster[i] = new ProtoVEBTree((int)Math.pow(u, 1/2.0), "universe");
        }else if (u == 2) array = new boolean[2];
        else throw new IllegalArgumentException("Bug found, Check your implementation!");
    }
    public ProtoVEBTree(int k){
        if(k <= 0) throw new IllegalArgumentException("Input >= 1");
        u = (int)Math.pow(2, Math.pow(2, k));
        cluster = new ProtoVEBTree[(int)Math.pow(u, 1/2.0)];
        for(int i = 0; i < cluster.length; i++)
            cluster[i] = new ProtoVEBTree((int)Math.pow(u, 1/2.0), "universe");
        summary = new ProtoVEBTree((int)Math.pow(u, 1/2.0), "universe");
    }

    public boolean hasMember(int x){
        if(x < 0 | x >= u) throw new IllegalArgumentException("Input out of range.");
        return hasMember(this, x);
    }
    private boolean hasMember(ProtoVEBTree T, int x){
        if(T.u == 2) return T.array[x];
        else return hasMember(T.cluster[T.high(x)], T.low(x));
    }

    public ProtoVEBTree insert(int x){
        if(x < 0 | x >= u) throw new IllegalArgumentException("Input out of range.");
        insert(this, x);
        return this;
    }
    private void insert(ProtoVEBTree T, int x){
        if(T.u == 2) {
//            System.out.printf("set true at: %d\n",x);
            T.array[x] = true;
        }
        else{
//            System.out.printf("enter cluster: %d, index is %d\n",T.high(x), T.low(x));
            insert(T.cluster[T.high(x)], T.low(x));
//            System.out.printf("enter summary: %d\n", T.high(x));
            insert(T.summary, T.high(x));
        }
    }

    public int maximum() {
        var res = maximum(this);
        if(res == null) throw new NullPointerException();
        else return res;
    }
    private Integer maximum(ProtoVEBTree T) {
        if(T.u == 2){
            if(T.array[1]) return 1;
            else if(T.array[0]) return 0;
            else return null;
        }else{
            var max_cluster = maximum(T.summary);
            if(max_cluster == null) throw new NullPointerException();
            else{
                var offset = maximum(T.cluster[max_cluster]);
                return T.index(max_cluster, offset);
            }
        }
    }

    public int minimum() {
        var res = minimum(this);
        if(res == null) throw new NullPointerException();
        else return res;
    }
    private Integer minimum(ProtoVEBTree T){
        if(T.u == 2){
            if(T.array[0]) return 0;
            else if(T.array[1]) return 1;
            else return null;
        }else{
            var min_cluster = minimum(T.summary);
            if(min_cluster == null) return null;
            else{
                var offset = minimum(T.cluster[min_cluster]);
                return T.index(min_cluster, offset);
            }
        }
    }

    public int successor(int x) {
        if(x < 0 | x >= u) throw new IllegalArgumentException("Input out of range.");
        var res = successor(this, x);
        if(res == null) throw new NullPointerException();
        else return res;
    }
    private Integer successor(ProtoVEBTree T, int x) {
        if(T.u == 2){
            if(x == 0 & T.array[1]) return 1;
            else return null;
        }else{
            var offset = successor(T.cluster[T.high(x)], T.low(x));
            if(offset != null) return T.index(T.high(x), offset);
            else{
                var succ_cluster = successor(T.summary, T.high(x));
                if(succ_cluster == null) return null;
                else {
                    offset = maximum(T.cluster[succ_cluster]);
                    return T.index(succ_cluster, offset);
                }
            }
        }
    }

    public int predecessor(int x) {
        if(x < 0 | x >= u) throw new IllegalArgumentException("Input out of range.");
        var res = predecessor(this, x);
        if(res == null) throw new NullPointerException();
        else return res;
    }
    private Integer predecessor(ProtoVEBTree T, int x)  {
        if(T.u == 2){
            if(x == 1 & T.array[0]) return 0;
            else return null;
        }else{
            var offset = predecessor(T.cluster[T.high(x)], T.low(x)); // search same cluster
            if(offset != null) return T.index(T.high(x), offset); // it isn't first item
            else{
                var pre_cluster = predecessor(T.summary, T.high(x));
                if(pre_cluster == null) return null;
                else {
                    offset = maximum(T.cluster[pre_cluster]);
                    return T.index(pre_cluster, offset);
                }
            }
        }
    }

    //public void delete(int x){}

    // cluster index:
    private int high(int x) { return (int)(x / Math.pow(u, 1/2.0)); }
    // array index:
    private int low(int x) { return (int)(x % Math.pow(u, 1/2.0)); }
    // global index:
    private int index(int h, Integer l) {
        if(l == null) throw new NullPointerException();
        return (int)(h * Math.pow(u, 1/2.0) + l);
    }
}