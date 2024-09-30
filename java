import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class SecretSharing {

    public static void main(String[] args) {
        try {
            // Read JSON from file
            System.out.println("Reading JSON file...");
            String jsonData = readJsonFile("testcase.json");
            System.out.println("JSON data: " + jsonData);

            // Parse JSON manually
            System.out.println("Parsing JSON...");
            Map<String, Object> jsonObject = parseJson(jsonData);
            System.out.println("Parsed JSON object: " + jsonObject);

            // Extract keys
            Map<String, Integer> keys = (Map<String, Integer>) jsonObject.get("keys");
            if (keys == null) {
                System.out.println("Error: 'keys' not found in JSON.");
                return;
            }
            int n = keys.get("n");
            int k = keys.get("k");

            System.out.println("n = " + n + ", k = " + k);

            // Decode points
            List<double[]> points = new ArrayList<>();
            for (String key : jsonObject.keySet()) {
                if (key.equals("keys")) continue;

                int x = Integer.parseInt(key);
                System.out.println("Processing point for x = " + x);
                Map<String, String> pointData = (Map<String, String>) jsonObject.get(key);
                if (pointData == null) {
                    System.out.println("Error: Point data not found for key = " + key);
                    continue;
                }
                int base = Integer.parseInt(pointData.get("base"));
                String value = pointData.get("value");

                BigInteger decodedY = new BigInteger(value, base);
                points.add(new double[]{x, decodedY.doubleValue()});
            }

            // Compute constant term (secret c)
            double constantC = findConstantTerm(points, k);
            System.out.println("Secret (constant term): " + constantC);
        } catch (FileNotFoundException e) {
            System.err.println("Error: testcase.json file not found. Please ensure the file is in the correct location.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int decodeValue(String base, String value) {
        int radix = Integer.parseInt(base);  // Convert base to integer (radix)
        return Integer.parseInt(value, radix); 
}


