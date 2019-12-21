package ua.edu.ucu.stream;

import ua.edu.ucu.decorators.FilterDecorator;
import ua.edu.ucu.decorators.FlatMapDecorator;
import ua.edu.ucu.decorators.MapDecorator;
import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Iterator;

public class AsIntStream implements IntStream {

    private Iterator<Integer> iterator;
    private ArrayList<Integer> list;

    private AsIntStream(Iterator<Integer> iterator, ArrayList<Integer> list) {
        this.iterator = iterator;
        this.list = list;
    }


    public static IntStream of(int... values) {
        ArrayList<Integer> listToIter = new ArrayList<>();
        for (int el: values) {
            listToIter.add(el);
        }
        AsIntStream stream = new AsIntStream(listToIter.iterator(), listToIter);
        return stream;
    }

    private boolean exceptionCheck() {
        if (list.size() > 0) {
            return false;
        }
        return true;
    }
    @Override
    public Double average() throws IllegalArgumentException {
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        Iterator<Integer> currIter = iterator;
        AsIntStream forSum = new AsIntStream(this.list.iterator(), this.list);
        AsIntStream forCount = new AsIntStream(this.list.iterator(), this.list);
        int currSum = forSum.sum();
        long amount = forCount.count();
        Double aver = (double) currSum / amount;
        return aver;
    }

    @Override
    public Integer max() {
        class maxFunc implements IntBinaryOperator {

            @Override
            public int apply(int left, int right) {
                if (left > right) { return left;}
                return right;
            }
        }
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
//        Iterator<Integer> currIter = iterator;
//        int maxV = Integer.MIN_VALUE;
//        while (currIter.hasNext()) {
//            int curr = currIter.next();
//            if (curr > maxV) { maxV = curr; }
//        }
//        return maxV;
        int maxV = reduce(Integer.MIN_VALUE, new maxFunc());
        return maxV;
    }

    @Override
    public Integer min() {
//        if (exceptionCheck()) { throw new IllegalArgumentException(); }
//        Iterator<Integer> currIter = iterator;
//        int minVal = Integer.MAX_VALUE;
//        while (currIter.hasNext()) {
//            int curr = currIter.next();
//            if (curr < minVal) { minVal = curr; }
//        }
        class minFunc implements IntBinaryOperator {

            @Override
            public int apply(int left, int right) {
                if (left < right) { return left;}
                return right;
            }
        }
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        int minV = reduce(Integer.MAX_VALUE, new minFunc());
        return minV;
    }

    @Override
    public long count() {
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        Iterator<Integer> currIter = iterator;
        int amount = 0;
        while (currIter.hasNext()) {
            int curr = currIter.next();
            amount ++;
        }
        return amount;
    }

    @Override
    public Integer sum() {
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        Iterator<Integer> currIter = iterator;
        int currSum = 0;
        while (currIter.hasNext()) {
            currSum += currIter.next();
        }
        return currSum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        FilterDecorator filt = new FilterDecorator(iterator, predicate);
        ArrayList<Integer> newList = filt.filter();
        AsIntStream newStream = new AsIntStream(newList.iterator(), newList);
//        iterator = newStream.iterator;
//        list = newStream.list;
        return newStream;
    }

    @Override
    public void forEach(IntConsumer action) {
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        Iterator<Integer> currIter = iterator;
        while (currIter.hasNext()) {
            int curr = currIter.next();
            action.accept(curr);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        MapDecorator mapD = new MapDecorator(iterator, mapper);
        ArrayList<Integer> newList = mapD.map();
        AsIntStream newStream = new AsIntStream(newList.iterator(), newList);
//        iterator = newStream.iterator;
//        list = newStream.list;
        return newStream;
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        FlatMapDecorator fmD = new FlatMapDecorator(iterator, func);
        ArrayList<IntStream> listOfStreams = fmD.map();
        ArrayList<Integer> newList = new ArrayList<>();
        for (IntStream el: listOfStreams) {
            int[] currLst = el.toArray();
            ArrayList<Integer> lstToAdd = makeArrayList(currLst);
            newList.addAll(lstToAdd);
        }
        AsIntStream newStream = new AsIntStream(newList.iterator(), newList);
        return newStream;
    }

    private ArrayList<Integer> makeArrayList(int[] array) {
        ArrayList<Integer> intList = new ArrayList<Integer>(array.length);
        for (int el : array) {
            intList.add(el);
        }
        return intList;
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        while(iterator.hasNext()) {
            identity = op.apply(identity, iterator.next());
        }
        return identity;
    }

    @Override
    public int[] toArray() {
        if (exceptionCheck()) { throw new IllegalArgumentException(); }
        int[] newList = new int[list.size()];
        Iterator<Integer> currIter = iterator;
        int it = 0;
        while (currIter.hasNext()) {
            newList[it] = currIter.next();
            it++;
        }
        return newList;
    }

}
