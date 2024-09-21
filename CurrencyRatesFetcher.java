import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyRatesFetcher {

    public static double getRate(String baseCurrency, String targetCurrency) {
        try {
            String apiUrl = "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {


                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String jsonResponse = response.toString();

                // Extract rates JSON
                int ratesStartIndex = jsonResponse.indexOf("\"rates\":") + 8;
                int ratesEndIndex = jsonResponse.indexOf("}", ratesStartIndex) + 1;


                String ratesJson = jsonResponse.substring(ratesStartIndex, ratesEndIndex);

                // Extract the target currency rate
                int targetIndex = ratesJson.indexOf("\"" + targetCurrency + "\":");
                if (targetIndex != -1) {
                    int rateStartIndex = targetIndex + targetCurrency.length() + 3;
                    int rateEndIndex = ratesJson.indexOf(",", rateStartIndex);
                    if (rateEndIndex == -1) {
                        rateEndIndex = ratesJson.length() - 1;
                    }
                    String rateString = ratesJson.substring(rateStartIndex, rateEndIndex);
                    
                    return Double.parseDouble(rateString);

                } else {
                    System.out.println("Invalid target currency: " + targetCurrency);
                    return -1;
                }

            } else {
                System.out.println("GET request failed");
                return -1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
