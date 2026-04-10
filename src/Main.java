import okhttp3.*;
import com.google.gson.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    private static final String API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static String API_KEY;

    public static void main(String[] args) {
        try {
            API_KEY = Files.readString(Paths.get("api-key.txt")).trim();
            System.out.println("API key loaded successfully.");
            System.out.println("=================================");
            System.out.println("  AI Cover Letter Generator");
            System.out.println("=================================\n");

            Scanner scanner = new Scanner(System.in);

            System.out.println("Paste the job description below.");
            System.out.println("When done, type END on a new line and press Enter:\n");
            StringBuilder jobDesc = new StringBuilder();
            String line;
            while (!(line = scanner.nextLine()).equals("END")) {
                jobDesc.append(line).append("\n");
            }

            System.out.println("\nBriefly describe your background (skills, experience):");
            String background = scanner.nextLine();

            System.out.println("\nChoose tone:");
            System.out.println("  1 = Professional");
            System.out.println("  2 = Enthusiastic");
            System.out.println("  3 = Concise");
            System.out.print("Enter 1, 2, or 3: ");
            String toneChoice = scanner.nextLine().trim();
            String tone = switch (toneChoice) {
                case "2" -> "enthusiastic and energetic";
                case "3" -> "concise and to the point";
                default  -> "professional and formal";
            };

            String prompt = String.format(
                "You are an expert career coach. Write a compelling cover letter with a %s tone.\n\n" +
                "Job Description:\n%s\n\n" +
                "Applicant Background:\n%s\n\n" +
                "Instructions:\n" +
                "- Start with a strong opening hook\n" +
                "- Match skills from the background to requirements in the job description\n" +
                "- Keep it to 3-4 paragraphs\n" +
                "- End with a confident call to action\n" +
                "- Do NOT include placeholder brackets, write the full letter body only",
                tone, jobDesc.toString(), background
            );

            System.out.println("\nGenerating your cover letter...\n");
            String result = callGroq(prompt);

            System.out.println("=================================");
            System.out.println("         YOUR COVER LETTER");
            System.out.println("=================================\n");
            System.out.println(result);

            Files.writeString(Paths.get("cover-letter.txt"), result);
            System.out.println("\n[Saved to cover-letter.txt]");

            scanner.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String callGroq(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = String.format(
            "{\"model\": \"llama-3.3-70b-versatile\", \"messages\": [{\"role\": \"user\", \"content\": \"%s\"}]}",
            prompt.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "")
        );

        RequestBody body = RequestBody.create(
            jsonBody,
            MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
            .url(API_URL)
            .addHeader("Authorization", "Bearer " + API_KEY)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                String errorBody = response.body() != null ? response.body().string() : "no body";
                throw new IOException("API call failed: " + response.code() + "\n" + errorBody);
            }

            String responseBody = response.body().string();
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            return json.getAsJsonArray("choices")
                       .get(0).getAsJsonObject()
                       .getAsJsonObject("message")
                       .get("content").getAsString();
        }
    }
}