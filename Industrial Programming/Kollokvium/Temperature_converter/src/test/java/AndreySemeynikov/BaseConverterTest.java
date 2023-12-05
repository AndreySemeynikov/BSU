package AndreySemeynikov;


import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;

public class BaseConverterTest {

    double celsiusTemperature;
    BaseConverter converter;

    @Before
    public void SetUp() {
        celsiusTemperature = 25.0;
        converter = BaseConverter.createConverter(celsiusTemperature);
    }
    @Test
    public void convertToKelvin() {
        assertEquals(celsiusTemperature, converter.convertToKelvin() - 273.15, 0.001);
    }

    @Test
    public void convertToFahrenheit() {
        assertEquals(celsiusTemperature, (converter.convertToFahrenheit() - 32) * 5 / 9, 0.001);
    }

    @Test
    public void convertToRankine() {
        assertEquals(celsiusTemperature, (converter.convertToRankine()-491.67)*5/9, 0.001);
    }

    @Test
    public void createConverter() {
        Locale.setDefault(new Locale("en", "US"));
        BaseConverter converterUS = BaseConverter.createConverter(25.0);
        assertTrue(converterUS instanceof FahrenheitConverter);

        // Тестирование для стран, не использующих градусы Фаренгейта
        Locale.setDefault(new Locale("fr", "FR"));
        BaseConverter converterFR = BaseConverter.createConverter(25.0);
        assertTrue(converterFR instanceof BaseConverter);
    }
}