package trigonometric;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.github.diosa.trigonometric.Cos;
import com.github.diosa.trigonometric.Sin;
import com.github.diosa.trigonometric.Tg;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TgTest {

    private final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00000001");
    private static final Double DELTA = 0.00001;
    private final Tg tg = new Tg();
    @Spy
    private Cos spyCos;
    @Spy
    private Sin spySin;
    @Mock
    private Cos mockCos;
    @Mock
    private Sin mockSin;

    @Test
    void originalSinCos() {
        Tg tan = new Tg(spySin, spyCos);
        tan.calculate(new BigDecimal(15), DEFAULT_PRECISION);
        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Math.PI / 8, -Math.PI / 4, -1, 0, 1, Math.PI / 4, Math.PI / 8})
    void calcWithMockSinCosParam(double param) {
        when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Tg tan = new Tg(mockSin, mockCos);
        assertEquals(Math.tan(param), tan.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, 3 * Math.PI / 2, 5 * Math.PI / 2, -Math.PI / 2, -3 * Math.PI / 2, -5 * Math.PI / 2})
    void calcWithZeroCos(double param) {
        lenient().when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Tg tan = new Tg(mockSin, mockCos);
        assertThrows(ArithmeticException.class, () -> tan.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = {5 * Math.PI / 8, 6 * Math.PI / 8, 7 * Math.PI / 8, Math.PI, 9 * Math.PI / 8, 10 * Math.PI / 8, 11 * Math.PI / 8})
    void calcWithPeriodParams(double param) {
        assertEquals(Math.tan(param), tg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 0, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(Math.tan(param), tg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(Math.tan(param), tg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), 0.00001);
    }

}