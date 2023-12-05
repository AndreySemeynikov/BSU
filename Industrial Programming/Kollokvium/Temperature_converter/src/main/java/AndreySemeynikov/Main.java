package AndreySemeynikov;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        double celsiusTemperature = 25.0;
        BaseConverter converter = BaseConverter.createConverter(celsiusTemperature);

        System.out.println("Temperature in Celsius: " + celsiusTemperature);
        System.out.println("Temperature in Kelvin: " + converter.convertToKelvin());
        System.out.println("Temperature in Fahrenheit: " + converter.convertToFahrenheit());
        System.out.println("Temperature in Rankine: " + converter.convertToRankine());
    }
}