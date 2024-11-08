package logarithmic;

import com.github.diosa.logarithmic.Ln;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

public class LnTest {

    private final BigDecimal precision = new BigDecimal("0.000000001");
    private static final Double DELTA = 0.0000001;

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, -354, -2})
    void calcWithIllegalArgument(double param) {
        Ln ln = new Ln();
        assertThrows(IllegalArgumentException.class, () -> ln.calculate(BigDecimal.valueOf(param), precision));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.02, 0.2, 1, 2.256, 15, 50.543, 200})
    void calcWithPositive(double param) {
        Ln ln = new Ln();
        assertEquals(Math.log(param), ln.calculate(BigDecimal.valueOf(param), precision).doubleValue(), DELTA);
    }
}
