package trigonometric;

import com.github.diosa.trigonometric.Ctg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.github.diosa.trigonometric.Cos;
import com.github.diosa.trigonometric.Sin;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CtgTest {

    private final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.00000001");
    private static final Double DELTA = 0.00001;
    private final Ctg ctg = new Ctg();
    @Spy
    private Cos spyCos;
    @Spy
    private Sin spySin;
    @Mock
    private Cos mockCos;
    @Mock
    private Sin mockSin;

    @Test
    void callSinCosCalculate() {
        Ctg ctg = new Ctg(spySin, spyCos);
        ctg.calculate(new BigDecimal(15), DEFAULT_PRECISION);
        verify(spySin, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
        verify(spyCos, atLeastOnce()).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 8, Math.PI / 4, 1, Math.PI / 2, 5 * Math.PI / 8, 3 * Math.PI / 4, 7 * Math.PI / 8})
    void calcWithMockSinCosParam(double param) {
        when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Ctg ctg = new Ctg(mockSin, mockCos);
        assertEquals(1 / Math.tan(param), ctg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 2 * Math.PI, 4 * Math.PI, -Math.PI, -2 * Math.PI, -4 * Math.PI, 20 * Math.PI, -50 * Math.PI})
    void calcWithZeroSin(double param) {
        when(mockSin.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.sin(param)));
        lenient().when(mockCos.calculate(eq(BigDecimal.valueOf(param)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.cos(param)));
        Ctg ctg = new Ctg(mockSin, mockCos);
        assertThrows(ArithmeticException.class, () -> ctg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -2, -1, 1, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        assertEquals(1 / Math.tan(param), ctg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.1, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithParams(double param) {
        assertEquals(1 / Math.tan(param), ctg.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), 0.00001);
    }

}