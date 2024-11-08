package logarithmic;

import com.github.diosa.logarithmic.Ln;
import com.github.diosa.logarithmic.Log;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0000000001");
    private static final Double DELTA = 0.00001;
    @Mock
    private Ln mockLn;
    @Spy
    private Ln spyLn;

    @Test
    void callLnCalculate() {
        Log log = new Log(spyLn,5);
        log.calculate(new BigDecimal(15), DEFAULT_PRECISION);
        verify(spyLn, times(2)).calculate(any(BigDecimal.class), any(BigDecimal.class));
    }

    @Test
    void calcPositiveWithMock() {
        when(mockLn.calculate(eq(new BigDecimal(100)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.log(100)));
        when(mockLn.calculate(eq(new BigDecimal(3)), any(BigDecimal.class)))
                .thenReturn(BigDecimal.valueOf(Math.log(3)));

        Log log = new Log(mockLn, 3);
        assertEquals(Math.log(100) / Math.log(3), log.calculate(new BigDecimal(100), DEFAULT_PRECISION).doubleValue(), DELTA);
    }

    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0, 1})
    void createLogWithZeroOneOrNegativeBase(int param) {
        assertThrows(IllegalArgumentException.class, () -> new Log(param));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.02, 1, 2.256, 15, 50.543, 200})
    void calcWithPositive(double param) {
        int[] bases = {2, 3, 5, 10};
        for (int base : bases) {
            Log log = new Log(base);
            assertEquals(Math.log(param) / Math.log(base),
                    log.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), DELTA);
        }
    }

}