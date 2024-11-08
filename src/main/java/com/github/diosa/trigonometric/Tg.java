package com.github.diosa.trigonometric;

import com.github.diosa.base.FunctionWithValidation;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static java.math.RoundingMode.HALF_UP;

public class Tg extends FunctionWithValidation {
    private final Sin sin;
    private final Cos cos;

    public Tg(Sin sin, Cos cos) {
        super();
        this.sin = sin;
        this.cos = cos;
    }

    public Tg() {
        super();
        this.sin = new Sin();
        this.cos = new Cos();
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        if (this.cos.calculate(x, precision).setScale(precision.scale(), HALF_EVEN).compareTo(ZERO) == 0) {
            throw new ArithmeticException("Cos(x) не может быть равен 0, tg(x) не существует");
        }

        final BigDecimal sinValue = sin.calculate(x, precision);
        final BigDecimal cosValue = cos.calculate(x, precision);

        final BigDecimal result = sinValue.divide(cosValue, HALF_UP);
        return result.setScale(precision.scale(), HALF_UP);
    }
}
