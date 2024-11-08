package com.github.diosa.logarithmic;

import com.github.diosa.base.FunctionWithValidation;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Log extends FunctionWithValidation {

    private final Ln ln;
    private final int base;

    public Log(int base) {
        this.ln = new Ln();
        this.base = base;
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Основание логарифма должно быть положительным числом не равным 1");
        }
    }

    public Log(Ln ln, int base) {
        this.ln = ln;
        this.base = base;
        if (base <= 0 || base == 1) {
            throw new IllegalArgumentException("Основание логарифма должно быть положительным числом не равным 1");
        }
    }

    @Override
    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        baseValidate(x, precision);
        BigDecimal lnX = this.ln.calculate(x, precision);
        BigDecimal lnA = this.ln.calculate(BigDecimal.valueOf(this.base), precision);
        return lnX.divide(lnA, precision.scale(), RoundingMode.HALF_EVEN);
    }
}