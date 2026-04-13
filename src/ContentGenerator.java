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
    
    // Main method with retry logic
    public String generateContent(String prompt) throws IOException {
        return generateContentWithRetry(prompt, 3);  // Try up to 3 times
    }
    
    // NEW: Retry logic
    private String generateContentWithRetry(String prompt, int maxAttempts) throws IOException {
        IOException lastException = null;
        
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try {
                return makeApiCall(prompt);  // Try to make the call
                
            } catch (IOException e) {
                lastException = e;
                
                if (attempt < maxAttempts) {
                    System.out.println("⚠️  Connection issue. Retrying... (" + attempt + "/" + maxAttempts + ")");
                    try {
                        Thread.sleep(1000);  // Wait 1 second before retry
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        // If all attempts failed, throw the last exception
        throw lastException;
    }
    
    // Actual API call (renamed from generateContent)
    private String makeApiCall(String prompt) throws IOException {
        // Build JSON request
        String jsonBody = String.format(
            "{\"model\": \"llama-3.3-70b-versatile\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
            escapeJson(prompt)
        );
        
        RequestBody body = RequestBody.create(
            jsonBody,
            MediaType.parse("application/json")
        );
        
        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + apiKey)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorMsg = getErrorMessage(response.code());
                throw new IOException(errorMsg);
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
    }
    
    // NEW: Better error messages based on error code
    private String getErrorMessage(int code) {
        return switch (code) {
            case 401 -> "Invalid API key. Check your api-key.txt file.";
            case 429 -> "Rate limit exceeded. Wait a moment and try again.";
            case 500, 502, 503 -> "Groq server error. Try again in a minute.";
            default -> "API Error (Code: " + code + ")";
        };
    }
    
    public void saveToFile(String content, String folder, String prefix) {
        try {
            String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            
            String filename = String.format("outputs/%s/%s_%s.txt", folder, prefix, timestamp);
            
            Files.createDirectories(Paths.get("outputs/" + folder));
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