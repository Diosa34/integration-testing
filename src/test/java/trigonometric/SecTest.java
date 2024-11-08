package trigonometric;

import com.github.diosa.trigonometric.Sec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.github.diosa.trigonometric.Cos;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class SecTest {
    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0000000001");
    private static final Double DELTA = 0.00001;
    final Sec sec = new Sec();
    @Spy
    private Cos spyCos;
    @Mock
    private Cos mockCos;

    @Test
    void callCosCalculate() {
        Sec sec = new Sec(spyCos);
        sec.calculate(new BigDecimal(15), DEFAULT_PRECISION);
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI / 8, -Math.PI / 4, -1, 0, 1, Math.PI / 4, Math.PI / 8})
    void calcWithMockCosParam(double param) {
        when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Sec sec = new Sec(mockCos);
        assertEquals(1 / Math.cos(param), sec.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2, 5 * Math.PI / 2, -Math.PI / 2, -3 * Math.PI / 2, -5 * Math.PI / 2, 15 * Math.PI / 2, 35 * Math.PI / 2})
    void calcWithZeroCos(double param) {
        when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Sec sec = new Sec(mockCos);
        assertThrows(ArithmeticException.class, () -> sec.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(1 / Math.cos(param), sec.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithDoubleParams(double param) {
        assertEquals(1 / Math.cos(param), sec.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }
}

