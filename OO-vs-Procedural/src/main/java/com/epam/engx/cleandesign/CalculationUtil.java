package com.epam.engx.cleandesign;

import java.util.Collection;
import java.util.function.ToDoubleFunction;

public class CalculationUtil {
    public CalculationUtil() {
    }

    public static <T> double summing(Collection<T> coll, ToDoubleFunction<T> extractDouble) {
        return coll.stream().mapToDouble(extractDouble).sum();
    }
}
