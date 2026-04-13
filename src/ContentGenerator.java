import okhttp3.*;
import com.google.gson.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContentGenerator {
    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private String apiKey;
    private OkHttpClient client;

    public ContentGenerator(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    public String generateContent(String prompt) throws IOException {
        return generateContent(prompt, 3); // 3 attempts
    }

    // Overloaded method with retry logic
    public String generateContent(String prompt, int maxAttempts) throws IOException {
        IOException lastException = null;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                // Build JSON request
                String jsonBody = String.format(
                        "{\"model\": \"llama-3.3-70b-versatile\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
                        escapeJson(prompt));

                RequestBody body = RequestBody.create(
                        jsonBody,
                        MediaType.parse("application/json"));

                Request request = new Request.Builder()
                        .url(API_URL)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("API Error: " + response.code() + " - " + response.message());
                    }

                    String responseBody = response.body().string();
                    JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

                    return json
                            .getAsJsonArray("choices")
                            .get(0)
                            .getAsJsonObject()
                            .getAsJsonObject("message")
                            .get("content")
                            .getAsString();
                }

            } catch (IOException e) {
                lastException = e;

                if (attempt < maxAttempts) {
                    System.out.println("⚠️  Attempt " + attempt + " failed. Retrying in 2 seconds...");
                    try {
                        Thread.sleep(2000); // Wait 2 seconds before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        // If all attempts failed, throw the last exception
        throw new IOException("Failed after " + maxAttempts + " attempts: " + lastException.getMessage());
    }

    public void saveToFile(String content, String folder, String prefix) {
        try {
            // Create timestamp for filename
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String filename = String.format("outputs/%s/%s_%s.txt", folder, prefix, timestamp);

            // Create directory if doesn't exist
            Files.createDirectories(Paths.get("outputs/" + folder));

            // Write to file
            Files.writeString(Paths.get(filename), content);

            System.out.println("\n✅ Saved to: " + filename);

        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    private String escapeJson(String text) {
        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}