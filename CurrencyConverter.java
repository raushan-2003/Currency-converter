import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n\n********************    WELCOME TO CURRENCY CONVERTER   ********************\n\n");


        System.out.print("Enter the base currency (e.g., USD): ");

        String baseCurrency = scanner.nextLine().toUpperCase();

        System.out.print("\nEnter the target currency (e.g., EUR): ");

        String targetCurrency = scanner.nextLine().toUpperCase();

        System.out.print("\nEnter the amount to convert: ");

        double amount = scanner.nextDouble();

        double rate = CurrencyRatesFetcher.getRate(baseCurrency, targetCurrency);

        
        if (rate != -1) {
            double convertedAmount = Math.round(amount * rate * 100.0) / 100.0;
            System.out.println("\n\nConverted Amount: " + convertedAmount + " " + targetCurrency+"\n\n");
        } else {
            System.out.println("Conversion failed due to invalid currency code.");
        }

        scanner.close();
    }
}
