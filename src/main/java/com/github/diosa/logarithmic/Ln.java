package com.github.diosa.logarithmic;

import com.github.diosa.base.FunctionWithValidation;
import static java.math.RoundingMode.HALF_EVEN;

import java.math.BigDecimal;

public class Ln extends FunctionWithValidation {

    public Ln() {
        super();
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        baseValidate(x, precision);
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(String.format("Ln argument x = %s must be greater than zero", x));
        }
        BigDecimal y = calcYForTaylorRow(x, precision);
        int iteration = 0;
        BigDecimal currentVal = BigDecimal.ZERO;
        BigDecimal previousVal;
        do {
            previousVal = currentVal;
            BigDecimal stepResult = (y.pow(2 * iteration + 1)).multiply(new BigDecimal(2))
                    .divide(BigDecimal.valueOf(2L * iteration + 1), precision.scale(), HALF_EVEN);
            currentVal = currentVal.add(stepResult);
            iteration++;
        } while (new BigDecimal("0.1").pow(precision.scale()).compareTo(previousVal.subtract(currentVal).abs()) < 0 && iteration < maxIterations);

        return currentVal;
    }

    private BigDecimal calcYForTaylorRow(BigDecimal x, BigDecimal precision) {
        return (x.subtract(BigDecimal.ONE)).divide(x.add(BigDecimal.ONE), precision.scale(), HALF_EVEN);
    }
}