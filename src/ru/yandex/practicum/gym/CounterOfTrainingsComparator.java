package ru.yandex.practicum.gym;

import java.util.Comparator;

public class CounterOfTrainingsComparator implements Comparator<CounterOfTrainings> {
    @Override
    public int compare(CounterOfTrainings a, CounterOfTrainings b) {
        return Integer.compare(b.getCount(), a.getCount()); // по убыванию
    }
}
