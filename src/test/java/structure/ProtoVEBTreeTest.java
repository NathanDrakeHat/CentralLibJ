package structure;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class ProtoVEBTreeTest {

    public ProtoVEBTree T;

    @BeforeEach
    void setUp(){
        T = new ProtoVEBTree(2);
        T.insert(1);
        T.insert(3);
        T.insert(5);
        T.insert(11);
        T.insert(13);
        T.insert(15);
    }

    @AfterEach
    void clean() { T = null; }

    @Test
    void hasMemberTest() {
        assertTrue(T.hasMember(3));
    }

    @Test
    void predecessorTest(){
        assertEquals(T.predecessor(3), 1);
    }

    @Test
    void successorTest(){
        assertEquals(T.successor(3), 5);
    }

    @Test
    void maximumTest(){
        assertEquals(T.maximum(), 15);
    }

    @Test
    void minimumTest(){
        assertEquals(T.minimum(), 1);
    }

    @Test
    void exceptionTest(){
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                T.successor(15);
            }
        });
    }
}