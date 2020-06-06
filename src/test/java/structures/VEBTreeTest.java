package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VEBTreeTest {
    
    private static boolean has(VEBTree V, int[] a){
        for(var i : a)
            if(!V.hasMember(i))
                return false;
        return true;
    }
    @Test
    void testCase1() {
        var V = new VEBTree(4);
        V.safeInsert(1).safeInsert(9).safeInsert(5).safeInsert(3).safeInsert(15);
        V.safeInsert(5).safeInsert(3).safeInsert(15).safeInsert(1);
        assertNull(V.successor(15));
        assertNull(V.predecessor(1));
        assertEquals(1, (int) V.predecessor(3));
        assertEquals(15, (int) V.successor(9));
        assertEquals(3, (int) V.predecessor(5));
        assertTrue(has(V, new int[] {1, 3, 5, 9, 15}));
        assertFalse(has(V, new int[]{2, 4, 6, 7, 8, 10, 11, 12, 13, 14}));
        assertEquals(1, (int) V.minimum());
        assertEquals(15, (int) V.maximum());
        V.safeDelete(1).safeDelete(5).safeDelete(15);
        assertFalse(V.hasMember(1));
        assertTrue(has(V, new int[]{3, 9}));
        assertEquals(3, (int) V.minimum());
        assertEquals(9, (int) V.maximum());
        assertFalse(has(V, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}));
        assertEquals(9, (int) V.successor(3));
        assertEquals(3, (int) V.predecessor(9));
    }
}