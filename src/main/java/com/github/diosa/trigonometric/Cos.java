package com.github.diosa.trigonometric;

import com.github.diosa.base.FunctionWithValidation;

import java.math.BigDecimal;

import static java.math.MathContext.DECIMAL128;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class Cos extends FunctionWithValidation {
    public Cos() {
        super();
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        baseValidate(x, precision);
        BigDecimal X = prepare(x);

        BigDecimal sum = new BigDecimal(0), prev;

        int n = 0;
        do {
            prev = sum;
            sum = sum.add((((new BigDecimal(-1)).pow(n)).multiply(X.pow(2 * n))).divide(this.getFactorial(2 * n), DECIMAL128.getPrecision(), HALF_EVEN));
            n++;
        } while (new BigDecimal("0.1").pow(precision.scale()).compareTo(prev.subtract(sum).abs()) < 0 || n < maxIterations);

        return sum.setScale(precision.scale(), HALF_UP);
    }
}
