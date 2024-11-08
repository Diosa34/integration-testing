import com.github.diosa.SystemOfFunctions;
import com.github.diosa.logarithmic.Ln;
import com.github.diosa.logarithmic.Log;
import com.github.diosa.trigonometric.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
public class SystemOfFunctionsTest {

    private final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.000000001");
    private static final Double DELTA = 0.0000001;

    final SystemOfFunctions systemFunc = new SystemOfFunctions();
//    (((((cos(-2.45) + cot(-2.45)) * cos(-2.45)) + (sin(-2.45) - tan(-2.45))) + ((cot(-2.45) * sin(-2.45)) / ((sec(-2.45) / csc(-2.45)) - (csc(-2.45) / cot(-2.45))))) ^ 2)

    @Mock private Sin mockSin;
    @Mock private Cos mockCos;
    @Mock private Tg mockTg;
    @Mock private Ctg mockCtg;
    @Mock private Sec mockSec;
    @Mock private Csc mockCsc;

    @Mock private Log mockLog2;
    @Mock private Log mockLog3;
    @Mock private Log mockLog5;
    @Mock private Log mockLog10;

    private final Ln ln = new Ln();

    @Test
    public void trigonometryPartMock() {
        BigDecimal x = BigDecimal.valueOf(-2.45);
        when(mockSin.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(-0.637764702));
        when(mockCos.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(-0.770231254));
        when(mockTg.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(0.8280171686));
        when(mockCtg.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1.20770442683556817));
        when(mockSec.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(-1.29831137693431));
        when(mockCsc.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(-1.5679763973345477));
        SystemOfFunctions sysFuncTrigonometryMock= new SystemOfFunctions(mockSin, mockCos, mockTg, mockCtg, mockSec, mockCsc);
        assertEquals(4.687106701, sysFuncTrigonometryMock.calculate(x, DEFAULT_PRECISION).doubleValue(), DELTA);
    }
//    (((((cos(-2.45) + cot(-2.45)) * cos(-2.45)) + (sin(-2.45) - tan(-2.45))) + ((cot(-2.45) * sin(-2.45)) / ((sec(-2.45) / csc(-2.45)) - (csc(-2.45) / cot(-2.45))))) ^ 2)

    @Test
    public void trigonometryPart() {
        BigDecimal x = BigDecimal.valueOf(-2.45);
        assertEquals(4.687106701, systemFunc.calculate(x, DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @Test
    public void logarithmicPartMock() {
        BigDecimal x = BigDecimal.valueOf(4.5);
        when(mockLog10.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(0.6532125137753437));
        when(mockLog2.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(2.169925001442312));
        when(mockLog3.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(1.36907024642854256));
        when(mockLog5.calculate(eq(x), any(BigDecimal.class))).thenReturn(BigDecimal.valueOf(0.9345358308985775));
        SystemOfFunctions sysFuncLogarithmicMock= new SystemOfFunctions(ln, mockLog2, mockLog3, mockLog5, mockLog10);
        assertEquals(5.1803262168563, sysFuncLogarithmicMock.calculate(x, DEFAULT_PRECISION).doubleValue(), DELTA);
    }
//    (((((log_10(4.5) ^ 3) / log_10(4.5)) / log_10(4.5)) - ((log_10(4.5) - log_2(4.5)) ^ 3)) + (((log_2(4.5) - log_2(4.5)) + log_3(4.5)) - (log_5(4.5) / (log_10(4.5) + log_2(4.5)))))

    @Test
    public void logarithmicPart() {
        BigDecimal x = BigDecimal.valueOf(4.5);
        assertEquals(5.1803262168563, systemFunc.calculate(x, DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {
            Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY
    })
    public void calculateExceptions(double x) {
        assertThrows(IllegalArgumentException.class, () -> systemFunc.calculate(BigDecimal.valueOf(x), DEFAULT_PRECISION));
    }
}
