package tools;


public class DPMatrice<V> {//matrice for DP problem
    private final Object[][] matrice;

    public DPMatrice(int size) {
        matrice = new Object[size][];
        for(int i = 0; i < size; i ++)
            matrice[i] = new Object[size-i];
    }

    @SuppressWarnings("unchecked")
    public V getAt(int r, int c){ return (V)matrice[r][c-r]; }

    public void setAt(V v, int r, int c) { matrice[r][c-r] = v; }
}