package ua.edu.ucu.decorators;

import ua.edu.ucu.function.IntPredicate;
import java.util.ArrayList;
import java.util.Iterator;

public class FilterDecorator {

    private IntPredicate predicate;
    private Iterator<Integer> iterator;

    public FilterDecorator(Iterator<Integer> iterator, IntPredicate predicate) {
        this.iterator = iterator;
        this.predicate = predicate;
    }

    public ArrayList<Integer> filter() {
        ArrayList<Integer> newList = new ArrayList<>();
        while (iterator.hasNext()) {
            int currVal = iterator.next();
            if (predicate.test(currVal)) {
                newList.add(currVal);
            }
        }
        return newList;
    }

}
