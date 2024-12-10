package apcsa.finalproject.fairshareproject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ParseReceipt {
    private ArrayList<String> receipt;
    private HashMap<String, Double> items;
    private String business;

    public ParseReceipt(String url) {
        try {
            // Fetch HTML content from the website
            String htmlContent = GetWebContent.fetchContent(url);

            // Extract plain text from the HTML
            String plainText = GetWebContent.extractPlainText(htmlContent);
            this.receipt = cleanReceipt(plainText);
            HashMap<String, Double> items = itemizeReceipt(this.receipt);
            if (!items.isEmpty()) {
                this.items = items;
                this.business = getBusiness(this.receipt);
            }
        } catch (IOException e) {
            System.err.println("Error fetching or parsing content: " + e.getMessage());
        }
    }

    public ArrayList<String> getReceipt() {
        return receipt;
    }

    public HashMap<String, Double> getItems() {
        return items;
    }

    public String getBusiness() {
        return business;
    }

    private static HashMap<String, Double> itemizeReceipt(ArrayList<String> tokens) {
        HashMap<String, Double> items = new HashMap<>();
        for (int i = 0; i < tokens.size(); i++) {
            String s = tokens.get(i);
            // Item and cost are on different lines
            if (s.matches("-? ?\\d+\\.\\d+")) {
                int n = 1;
                if (tokens.get(i-1).matches("-? ?\\d+\\.\\d+")) {
                    items.put("", Double.parseDouble(s.replace(" ","")));
                }
                else {
                    while (!tokens.get(i-n).matches("[A-Za-z0-9 &-]+")) {
                        n++;
                    }
                    items.put(tokens.get(i-n), Double.parseDouble(s.replace(" ", "")));
                }
            } // Item and cost are on same line
            else if (s.matches("[A-Za-z0-9 &-]+ -? ?\\d+\\.\\d+")) {
                Pattern pattern = Pattern.compile("([A-Za-z0-9 &-]+) (-? ?\\d+\\.\\d+)");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find())
                {
                    items.put(matcher.group(1), Double.parseDouble(matcher.group(2).replace(" ", "")));
                }
            }
            else if (s.matches("\\d+")) {
                if (tokens.get(i+1).matches("\\.\\d+")) {
                    int n = 1;
                    if (tokens.get(i-1).matches("-? ?\\d+\\.\\d+")) {
                        items.put("", Double.parseDouble(s+tokens.get(i+1)));
                    }
                    else {
                        while (!tokens.get(i-n).matches("[A-Za-z0-9 &-]+")) {
                            n++;
                        }
                        items.put(tokens.get(i-n), Double.parseDouble(s+tokens.get(i+1)));
                    }
                } else if (tokens.get(i+1).equals(".")&&tokens.get(i+2).matches("\\d+")){
                    int n = 1;
                    if (tokens.get(i-1).matches("-? ?\\d+\\.\\d+")) {
                        items.put("", Double.parseDouble(s+tokens.get(i+1)+tokens.get(i+2)));
                    }
                    else {
                        while (!tokens.get(i-n).matches("[A-Za-z0-9 &-]+")) {
                            n++;
                        }
                        items.put(tokens.get(i-n), Double.parseDouble(s+tokens.get(i+1)+tokens.get(i+2)));
                    }
                }
            }
        }
        ArrayList<String> remove = new ArrayList<>();
        for (String item:items.keySet()) {
            if (!item.equalsIgnoreCase("total")&&(items.get(item).equals(items.get("total"))||items.get(item).equals(items.get("Total")))) {
                remove.add(item);
            }
        }
        for (String r:remove) {
            items.remove(r);
        }
        return items;
    }

    private static String getBusiness(ArrayList<String> tokens) {
        return tokens.get(0);
    }

    private static ArrayList<String> cleanReceipt(String receipt) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(receipt.split("\n")));
        tokens.replaceAll(s -> s.replaceAll("[^a-zA-Z0-9. %&-]", "").strip());
        tokens.removeIf(String::isBlank);
        return tokens;
    }
}
