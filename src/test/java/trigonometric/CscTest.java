package trigonometric;

import com.github.diosa.trigonometric.Csc;
import com.github.diosa.trigonometric.Sin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CscTest {
    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.000000001");
    private static final Double DELTA = 0.00001;
    final Csc csc = new Csc();
    @Spy
    private Sin spySin;
    @Mock
    private Sin mockSin;

    @Test
    void callCosCalculate() {
        Csc csc = new Csc(spySin);
        csc.calculate(new BigDecimal(15), DEFAULT_PRECISION);
        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 8, Math.PI / 4, 3 * Math.PI / 8, Math.PI / 2, 5 * Math.PI / 8, 3 * Math.PI / 4, 7 * Math.PI / 8})
    void calcWithMockSinParam(double param) {
        when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        Csc csc = new Csc(mockSin);
        assertEquals(1 / Math.sin(param), csc.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 2 * Math.PI, 3 * Math.PI, -Math.PI, -2 * Math.PI, -3 * Math.PI})
    void calcWithZeroSin(double param) {
        when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        Csc csc = new Csc(mockSin);
        assertThrows(ArithmeticException.class, () -> csc.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        Assertions.assertEquals(1 / Math.sin(param), csc.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithDoubleParams(double param) {
        Assertions.assertEquals(1 / Math.sin(param), csc.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }
}
