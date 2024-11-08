package com.github.diosa.trigonometric;


import com.github.diosa.base.FunctionWithValidation;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class Csc extends FunctionWithValidation {
    private final Sin sin;

    public Csc(final Sin sin) {
        super();
        this.sin = sin;
    }

    public Csc() {
        super();
        this.sin = new Sin();
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        if (this.sin.calculate(x, precision).setScale(precision.scale(), HALF_EVEN).compareTo(ZERO) == 0) {
            throw new ArithmeticException("Sin(x) не может быть равен 0, csc(x) не существует");
        }

        final BigDecimal sinValue = sin.calculate(x, precision);

        final BigDecimal result = (BigDecimal.valueOf(1).setScale(precision.scale(), HALF_EVEN)).divide(sinValue, HALF_UP);
        return result.setScale(precision.scale(), HALF_UP);
    }
}
