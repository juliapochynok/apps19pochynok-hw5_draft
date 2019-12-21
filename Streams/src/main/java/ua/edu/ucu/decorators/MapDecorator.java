package ua.edu.ucu.decorators;

import ua.edu.ucu.function.IntUnaryOperator;

import java.util.ArrayList;
import java.util.Iterator;

// Map every element to another object using MyFunction
public class MapDecorator {

    private IntUnaryOperator function;
    private Iterator<Integer> iterator;

    public MapDecorator(Iterator<Integer> iterator, IntUnaryOperator function) {
        this.iterator = iterator;
        this.function = function;
    }

    public ArrayList<Integer> map() {
        ArrayList<Integer> newList = new ArrayList<>();
        while (iterator.hasNext()) {
                newList.add(function.apply(iterator.next()));
            }
        return newList;
        }
}

