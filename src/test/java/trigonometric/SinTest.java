package trigonometric;

import com.github.diosa.trigonometric.Sin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;


public class SinTest {

    private static final BigDecimal DEFAULT_PRECISION = new BigDecimal("0.0000001");
    private final Sin sin = new Sin();

    @ParameterizedTest
    @ValueSource(doubles = {-200, -112, -77, -15, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 15, 77, 114, 250})
    void calcWithIntParams(double param) {
        Assertions.assertEquals(Math.sin(param), sin.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), 0.0000001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-200.991, -112.05, -77.1, -15.4567, -2.23, -0.01, 0.432, 1.0304, 15.432, 77.235, 114.9093, 250.8})
    void calcWithDoubleParams(double param) {
        Assertions.assertEquals(Math.sin(param), sin.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), 0.0000001);
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI/2, Math.PI, 3 * Math.PI/2, 2 * Math.PI/2, -Math.PI/2, -Math.PI, -3 * Math.PI/2, -2 * Math.PI/2})
    void calcWithSpecialPoints(double param) {
        Assertions.assertEquals(Math.sin(param), sin.calculate(BigDecimal.valueOf(param), DEFAULT_PRECISION).doubleValue(), 0.0000001);
    }
}
