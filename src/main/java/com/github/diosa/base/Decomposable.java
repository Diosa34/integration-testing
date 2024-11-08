package com.github.diosa.base;

import java.math.BigDecimal;

public interface Decomposable {
    BigDecimal calculate(final BigDecimal x, final BigDecimal precision);
}
