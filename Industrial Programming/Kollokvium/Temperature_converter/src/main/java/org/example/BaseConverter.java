package org.example;

import java.util.Locale;

public class BaseConverter {
    private double celsiusTemperature;

    public BaseConverter(double celsiusTemperature) {
        this.celsiusTemperature = celsiusTemperature;
    }

    public double convertToKelvin() {
        return celsiusTemperature + 273.15;
    }

    public double convertToFahrenheit() {
        return celsiusTemperature * 9/5 + 32;
    }
    public double convertToRankine() {
        return celsiusTemperature * 9 / 5 + 491.67;
    }

    public static BaseConverter createConverter(double celsiusTemperature) {
        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();

        if (isFahrenheitCountry(countryCode)) {
            return new FahrenheitConverter(celsiusTemperature);
        } else {
            return new BaseConverter(celsiusTemperature);
        }
    }

    private static boolean isFahrenheitCountry(String countryCode) {
        String[] fahrenheitCountries = {"BS", "US", "BZ", "KY", "PW"};
        for (String country : fahrenheitCountries) {
            if (country.equals(countryCode)) {
                return true;
            }
        }
        return false;
    }
}

class FahrenheitConverter extends BaseConverter {
    public FahrenheitConverter(double celsiusTemperature) {
        super(celsiusTemperature);
    }

    @Override
    public double convertToFahrenheit() {
        return super.convertToFahrenheit();
    }
    public double convertToRankine() {
        return super.convertToRankine();
    }
}
