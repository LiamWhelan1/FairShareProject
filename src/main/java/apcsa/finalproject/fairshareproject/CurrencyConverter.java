package apcsa.finalproject.fairshareproject;

import java.io.*;
import java.net.*;
import org.json.*;

public class CurrencyConverter {

    private static final String API_KEY = "ca1a54ea2b83be2c0e1ff926d499afaa"; // Switch when we run out of free uses
    private static final String API_URL = "http://data.fixer.io/api/latest?access_key=" + API_KEY + "&symbols=";

    // Method to fetch the exchange rate and perform the conversion
    public static double convertCurrency(double amount, String sourceCurrency, String targetCurrency) throws IOException, JSONException {
        // Fetch exchange rates from Fixer.io
        JSONObject jsonResponse = getJsonObject(sourceCurrency, targetCurrency);

        // Check if the response contains the rates
        if (!jsonResponse.getBoolean("success")) {
            throw new JSONException("Error: Unable to fetch exchange rates.");
        }

        // Get the exchange rates for the source and target currencies
        JSONObject rates = jsonResponse.getJSONObject("rates");
        double sourceRate = rates.getDouble(sourceCurrency);
        double targetRate = rates.getDouble(targetCurrency);

        // Return the amount converted to the target currency
        return (amount / sourceRate) * targetRate;
    }

    private static JSONObject getJsonObject(String sourceCurrency, String targetCurrency) throws IOException {
        String url = API_URL + sourceCurrency + "," + targetCurrency; // URL to fetch exchange rates for the required currencies
        URL apiUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse the JSON response
        JSONObject jsonResponse = new JSONObject(response.toString());
        return jsonResponse;
    }
}