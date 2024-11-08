package com.github.diosa.base;

import java.math.BigDecimal;
import java.util.Objects;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

public abstract class FunctionWithValidation implements Decomposable{

    private static final int DEFAULT_MAX_ITERATIONS = 1000;

    protected final int maxIterations;

    protected FunctionWithValidation() {
        this.maxIterations = DEFAULT_MAX_ITERATIONS;
    }

    protected void baseValidate(final BigDecimal x, final BigDecimal precision) {
        Objects.requireNonNull(x, "Аргумент функции не может быть null");
        Objects.requireNonNull(precision, "Точность не может быть null");
        if (precision.compareTo(ZERO) <= 0 || precision.compareTo(ONE) >= 0) {
            throw new ArithmeticException("Точность должна быть в диапазоне (0; 1)");
        }
    }

    protected BigDecimal getFactorial(int f) {
        BigDecimal result = new BigDecimal(1);
        for (int i = 1; i <= f; i++) {
            result = result.multiply(BigDecimal.valueOf(i));
        }
        return result;
    }

    protected BigDecimal prepare(BigDecimal X) {
        double x = X.doubleValue();

        double PI2 = Math.PI * 2;

        if (x >= 0) {
            while (x > PI2) {
                x -= PI2;
            }
        } else if (x < 0){
            while (x < -PI2) {
                x += PI2;
            }
        }

        return new BigDecimal(x);
    }
}
