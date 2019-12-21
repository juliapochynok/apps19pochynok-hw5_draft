package ua.edu.ucu.decorators;

import ua.edu.ucu.function.IntToIntStreamFunction;
import ua.edu.ucu.stream.IntStream;

import java.util.ArrayList;
import java.util.Iterator;

public class FlatMapDecorator {

    private IntToIntStreamFunction func;
    private Iterator<Integer> iterator;

    public FlatMapDecorator(Iterator<Integer> iterator, IntToIntStreamFunction function) {
        this.iterator = iterator;
        this.func = function;
    }

    public ArrayList<IntStream> map() {
        ArrayList<IntStream> newList = new ArrayList<>();
        while (iterator.hasNext()) {
            newList.add(func.applyAsIntStream(iterator.next()));
        }
        return newList;
    }
}


