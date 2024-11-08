package com.github.diosa;


import com.github.diosa.base.Decomposable;
import com.github.diosa.logarithmic.Ln;
import com.github.diosa.logarithmic.Log;
import com.github.diosa.trigonometric.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;

@Getter
@Setter
public class SystemOfFunctions implements Decomposable {

    private final Sin sin;
    private final Cos cos;
    private final Tg tg;
    private final Ctg ctg;
    private final Sec sec;
    private final Csc csc;
    private final Ln ln;
    private final Log log2;
    private final Log log3;
    private final Log log5;
    private final Log log10;

    public SystemOfFunctions() {
        this.sin = new Sin();
        this.cos = new Cos();
        this.tg = new Tg();
        this.ctg = new Ctg();
        this.sec = new Sec();
        this.csc = new Csc();
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log3 = new Log(3);
        this.log5 = new Log(5);
        this.log10 = new Log(10);
    }

    public SystemOfFunctions(Sin sin, Cos cos, Tg tg, Ctg ctg, Sec sec, Csc csc) {
        this.sin = sin;
        this.cos = cos;
        this.tg = tg;
        this.ctg = ctg;
        this.sec = sec;
        this.csc = csc;
        this.ln = new Ln();
        this.log2 = new Log(2);
        this.log3 = new Log(3);
        this.log5 = new Log(5);
        this.log10 = new Log(10);
    }

    public SystemOfFunctions(Ln ln, Log log2, Log log3, Log log5, Log log10) {
        this.sin = new Sin();
        this.cos = new Cos();
        this.tg = new Tg();
        this.ctg = new Ctg();
        this.sec = new Sec();
        this.csc = new Csc();
        this.ln = ln;
        this.log2 = log2;
        this.log3 = log3;
        this.log5 = log5;
        this.log10 = log10;
    }


    public BigDecimal calculate(BigDecimal x, BigDecimal precision) {
        if (x.compareTo(BigDecimal.ZERO) <= 0) {
            BigDecimal sinVal = this.sin.calculate(x, precision);
            BigDecimal cosVal = this.cos.calculate(x, precision);
            BigDecimal tgVal = this.tg.calculate(x, precision);
            BigDecimal ctgVal = this.ctg.calculate(x, precision);
            BigDecimal secVal = this.sec.calculate(x, precision);
            BigDecimal cscVal = this.csc.calculate(x, precision);

            belowZeroCheckSmallDenominator(ctgVal, cscVal, x);

            BigDecimal secDivCsc = secVal.divide(cscVal, HALF_EVEN);
            BigDecimal cscDivCtg = cscVal.divide(ctgVal, HALF_EVEN);
            BigDecimal denominator = secDivCsc.subtract(cscDivCtg);

            belowZeroCheckBDenominator(denominator, x);

            BigDecimal firstSummand = (cosVal
                                            .add(ctgVal))
                                            .multiply(cosVal);

            BigDecimal secondSummand = sinVal
                                            .subtract(tgVal);

            BigDecimal thirdSummand = (ctgVal
                                            .multiply(sinVal))
                                            .divide(denominator, HALF_EVEN);

            return firstSummand
                    .add(secondSummand)
                    .add(thirdSummand)
                    .pow(2);
        } else {
            BigDecimal log10Val = this.log10.calculate(x, precision);
            BigDecimal log2Val = this.log2.calculate(x, precision);
            BigDecimal log10plusLog2 = log10Val.add(log2Val);

            greaterZeroCheck(log10Val, log10plusLog2, x);

            BigDecimal log3Val = this.log3.calculate(x, precision);
            BigDecimal log5Val = this.log5.calculate(x, precision);

            BigDecimal firstPart = ((log10Val
                                        .pow(3))
                                        .divide(log10Val, HALF_EVEN))
                                        .divide(log10Val, HALF_EVEN);

            BigDecimal secondPart = (log10Val
                                        .subtract(log2Val))
                                        .pow(3);

            BigDecimal firstSummand = firstPart
                                        .subtract(secondPart);

            BigDecimal thirdPart = log2Val
                                        .subtract(log2Val)
                                        .add(log3Val);

            BigDecimal fourthPart = log5Val
                                        .divide(log10plusLog2, HALF_EVEN);

            BigDecimal secondSummand = thirdPart.subtract(fourthPart);

            return firstSummand.add(secondSummand);
        }
    }

    private void belowZeroCheckSmallDenominator(BigDecimal ctgVal, BigDecimal cscVal, BigDecimal x) {
        if (ctgVal.compareTo(ZERO) == 0) {
            throw new ArithmeticException(String.format("%s не входит в область определения функции, ctg(%s) не может быть равен нулю", x, x));
        }

        if (cscVal.compareTo(ZERO) == 0) {
            throw new ArithmeticException(String.format("%s не входит в область определения функции, csc(%s) не может быть равен нулю", x, x));
        }
    }

    private void belowZeroCheckBDenominator(BigDecimal denominator, BigDecimal x) {
        if (denominator.compareTo(ZERO) == 0) {
            throw new ArithmeticException(String.format("%s не входит в область определения функции, знаменатель не может быть равен нулю", x));
        }
    }

    private void greaterZeroCheck(BigDecimal log10Val, BigDecimal log10plusLog2, BigDecimal x) {
        if (log10Val.compareTo(ZERO) == 0) {
            throw new ArithmeticException(String.format("%s не входит в область определения функции, log10(%s) не может быть равен нулю", x, x));
        }

        if (log10plusLog2.compareTo(ZERO) == 0) {
            throw new ArithmeticException(String.format("%s не входит в область определения функции, log10(%s) + log2(%s) в знаменателе не может быть равно нулю", x, x, x));
        }
    }
}
