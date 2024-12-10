package apcsa.finalproject.fairshareproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetWebContent {

    /**
     * Fetches content from a specified URL and returns it as a string.
     *
     * @param urlString the URL to fetch the content from
     * @return the content of the webpage as a string
     * @throws IOException if an I/O error occurs
     */
    public static String fetchContent(String urlString) throws IOException {
        StringBuilder content = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Open connection
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Use GET request
            connection.setConnectTimeout(5000); // Set timeout to 5 seconds
            connection.setReadTimeout(5000);

            // Check for successful response code (HTTP 200)
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Create a reader for the input stream
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } else {
                throw new IOException("HTTP error code: " + connection.getResponseCode());
            }
        } finally {
            // Close resources
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }

        return content.toString();
    }

    /**
     * Parses HTML content, removes pop-ups, and extracts plain text while preserving special characters.
     *
     * @param htmlContent the HTML content to parse
     * @return the plain text extracted from the HTML with special characters preserved
     */
    public static String extractPlainText(String htmlContent) {
        // Parse the HTML content using Jsoup
        Document doc = Jsoup.parse(htmlContent);

        // Use .wholeText() to preserve newlines, tabs, and formatting
        return doc.body().wholeText();
    }
}
