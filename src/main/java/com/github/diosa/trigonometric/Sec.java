package com.github.diosa.trigonometric;


import com.github.diosa.base.FunctionWithValidation;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

public class Sec extends FunctionWithValidation {
    private final Cos cos;

    public Sec(final Cos cos) {
        super();
        this.cos = cos;
    }

    public Sec() {
        super();
        this.cos = new Cos();
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        if (this.cos.calculate(x, precision).setScale(precision.scale(), HALF_EVEN).compareTo(ZERO) == 0) {
            throw new ArithmeticException("Cos(x) не может быть равен 0, sec(x) не существует");
        }

        final BigDecimal cosValue = cos.calculate(x, precision);

        final BigDecimal result = (BigDecimal.valueOf(1).setScale(precision.scale(), HALF_EVEN)).divide(cosValue, HALF_EVEN);
        return result.setScale(precision.scale(), HALF_EVEN);
    }
}
