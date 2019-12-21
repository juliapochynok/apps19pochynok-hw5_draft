package ua.edu.ucu.stream;

import org.junit.Before;
import org.junit.Test;
import ua.edu.ucu.function.IntBinaryOperator;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class AsIntStreamTest {

    private IntStream stream;

    @Before
    public void init() {
        int[] intArr = {-100, 45, 2, 5, -2, 7, 1111, 8};
        stream =  AsIntStream.of(intArr);
    }

    @Test
    public void testAverage() {
        System.out.println("Average Operation");
        double expRes = 134.5;
        double result = stream.average();
        assertEquals(expRes, result, 0.1);
    }

    @Test
    public void testMax() {
        System.out.println("Max Operation");
        int expRes = 1111;
        int result = stream.max();
        assertEquals(expRes, result);
    }

    @Test
    public void testMin() {
        System.out.println("Min Operation");
        int expRes = -100;
        int result = stream.min();
        assertEquals(expRes, result);
    }

    @Test
    public void testCount() {
        System.out.println("Count Operation");
        long expRes = 8;
        long result = stream.count();
        assertEquals(expRes, result);
    }

    @Test
    public void testSum() {
        System.out.println("Sum Operation");
        int expRes = 1076;
        int result = stream.sum();
        assertEquals(expRes, result);
    }

    @Test
    public void testFilter() {
        System.out.println("Filter Operation");
        int[] expRes = {45, 1111, 8};
        IntStream result = stream.filter(x -> x > 7);
        assertArrayEquals(expRes, result.toArray());
    }

    @Test
    public void testForEach() {
        System.out.println("ForEach Operation");
        ArrayList<Integer> newList = new ArrayList<>();
        int[] t = {-100, 45, 2, 5, -2, 7, 1111, 8};
        ArrayList<Integer> actual = new ArrayList<>();
        for (int el: t) {
            actual.add(el);
        }
        stream.forEach(x -> newList.add(x));
        assertArrayEquals(newList.toArray(), actual.toArray());
    }

    @Test
    public void testMap() {
        System.out.println("Map Operation");
        int[] expRes = {-98, 47, 4, 7, 0, 9, 1113, 10};
        IntStream result = stream.map(x -> x + 2);
        assertArrayEquals(expRes, result.toArray());
    }

    @Test
    public void testFlatMap() {
        System.out.println("FlatMap Operation");
        int[] expRes = {-100, 0, 45, 0, 2, 0, 5, 0, -2, 0, 7, 0, 1111, 0, 8, 0};
        IntStream result = stream.flatMap(x -> AsIntStream.of(x, x*0));
        assertArrayEquals(expRes, result.toArray());
    }

    @Test
    public void testReduce() {
        class tryFun implements IntBinaryOperator {

            @Override
            public int apply(int left, int right) {
                return left - right;
            }
        }

        System.out.println("Reduce Operation");
        int expRes = -1072;
        int result = stream.reduce(4, new tryFun());
        assertEquals(expRes, result);
    }

    @Test
    public void testToArray() {
        System.out.println("FlatMap Operation");
        int[] expRes = {-100, 45, 2, 5, -2, 7, 1111, 8};
        int[] result = stream.toArray();
        assertArrayEquals(expRes, result);
    }

}

