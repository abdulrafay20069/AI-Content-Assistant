import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static ContentGenerator generator;
    private static Scanner scanner = new Scanner(System.in);
    
    // Helper to get non-empty input
    private static String getNonEmptyInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (!input.isEmpty()) {
                return input;
            }
            
            System.out.println("❌ Input cannot be empty. Please try again.");
        }
    }
    
    public static void main(String[] args) {
        try {
            String apiKey = Files.readString(Paths.get("api-key.txt")).trim();
            generator = new ContentGenerator(apiKey);
            
            System.out.println("🤖 AI Content Assistant v1.0 - Powered by Groq");
            System.out.println("===============================================\n");
            
            while (true) {
                displayMenu();
                int choice = getChoice();
                
                if (choice == 9) {
                    System.out.println("\n👋 Thanks for using AI Content Assistant!");
                    System.out.println("💾 All your files are saved in the outputs/ folder");
                    break;
                }
                
                handleChoice(choice);
            }
            
        } catch (Exception e) {
            System.out.println("\n❌ Critical Error: " + e.getMessage());
            System.out.println("💡 Make sure api-key.txt exists and is valid");
        } finally {
            scanner.close();
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== What would you like to do? ===");
        System.out.println("1. 📝 Blog Post");
        System.out.println("2. 📱 Social Media Post");
        System.out.println("3. ✉️  Email");
        System.out.println("4. 🛍️  Product Description");
        System.out.println("5. 📄 Summarize Text");
        System.out.println("6. ❓ Ask AI Anything");
        System.out.println("7. 📂 Summarize from File");
        System.out.println("8. 📜 View History");
        System.out.println("9. 🚪 Exit");
        System.out.print("\nChoose (1-9): ");
    }
    
    private static int getChoice() {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void handleChoice(int choice) {
        try {
            switch (choice) {
                case 1 -> generateBlogPost();
                case 2 -> generateSocialPost();
                case 3 -> generateEmail();
                case 4 -> generateProductDescription();
                case 5 -> summarizeText();
                case 6 -> askQuestion();
                case 7 -> summarizeFromFile();
                case 8 -> viewHistory();
                default -> System.out.println("❌ Invalid choice. Please choose 1-9.");
            }
        } catch (IOException e) {
            System.out.println("\n⚠️  Something went wrong!");
            System.out.println("💡 Check your internet connection and try again");
        }
    }
    
    private static void generateBlogPost() throws IOException {
        System.out.println("\n📝 BLOG POST GENERATOR\n");
        
        String topic = getNonEmptyInput("Topic: ");
        String audience = getNonEmptyInput("Target audience: ");
        
        System.out.println("\n⏳ Generating blog post...");
        
        String prompt = String.format(
            "Write a 300-word blog post about '%s' for %s. " +
            "Include an engaging intro, 2-3 main points, and a conclusion. " +
            "Use a conversational but informative tone.",
            topic, audience
        );
        
        String content = generator.generateContent(prompt);
        System.out.println("\n" + content);
        generator.saveToFile(content, "blogs", "blog");
    }
    
    private static void generateSocialPost() throws IOException {
        System.out.println("\n📱 SOCIAL MEDIA POST GENERATOR\n");
        
        String topic = getNonEmptyInput("Post topic/message: ");
        String platform = getNonEmptyInput("Platform (LinkedIn/Twitter/Instagram): ");
        
        System.out.println("\n⏳ Generating social post...");
        
        String prompt = String.format(
            "Create a %s post about: %s. " +
            "Keep it engaging, concise, and include relevant emojis. " +
            "Max 280 characters for Twitter, 150 words for LinkedIn/Instagram.",
            platform, topic
        );
        
        String content = generator.generateContent(prompt);
        System.out.println("\n" + content);
        generator.saveToFile(content, "social", platform.toLowerCase());
    }
    
    private static void generateEmail() throws IOException {
        System.out.println("\n✉️  EMAIL GENERATOR\n");
        
        String purpose = getNonEmptyInput("Email purpose: ");
        String context = getNonEmptyInput("Recipient/Context: ");
        String tone = getNonEmptyInput("Tone (professional/friendly/urgent): ");
        
        System.out.println("\n⏳ Generating email...");
        
        String prompt = String.format(
            "Write a %s email for: %s. Context: %s. " +
            "Include subject line, greeting, body (3-4 paragraphs), and closing.",
            tone, purpose, context
        );
        
        String content = generator.generateContent(prompt);
        System.out.println("\n" + content);
        generator.saveToFile(content, "emails", "email");
    }
    
    private static void generateProductDescription() throws IOException {
        System.out.println("\n🛍️  PRODUCT DESCRIPTION GENERATOR\n");
        
        String product = getNonEmptyInput("Product name: ");
        String features = getNonEmptyInput("Key features (comma-separated): ");
        
        System.out.println("\n⏳ Generating product description...");
        
        String prompt = String.format(
            "Write a compelling product description for: %s. " +
            "Features: %s. " +
            "Include a catchy headline, benefits-focused copy (100-150 words), " +
            "and a call-to-action.",
            product, features
        );
        
        String content = generator.generateContent(prompt);
        System.out.println("\n" + content);
        generator.saveToFile(content, "products", "product");
    }
    
    private static void summarizeText() throws IOException {
        System.out.println("\n📄 TEXT SUMMARIZER");
        System.out.println("Paste your text (press Enter twice when done):");
        System.out.println("--------------------");
        
        StringBuilder textBuilder = new StringBuilder();
        String line;
        int emptyLines = 0;
        
        while (emptyLines < 2) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                emptyLines++;
            } else {
                emptyLines = 0;
                textBuilder.append(line).append("\n");
            }
        }
        
        String textToSummarize = textBuilder.toString().trim();
        
        if (textToSummarize.isEmpty()) {
            System.out.println("❌ No text provided!");
            return;
        }
        
        System.out.print("\nLength (short/medium/detailed): ");
        String length = scanner.nextLine().toLowerCase();
        
        System.out.println("\n⏳ Summarizing...");
        
        String wordCount = switch (length) {
            case "short" -> "50-75 words";
            case "medium" -> "150-200 words";
            case "detailed" -> "300-400 words";
            default -> "150 words";
        };
        
        String prompt = String.format(
            "Summarize the following text in %s. " +
            "Extract key points and main ideas.\n\n%s",
            wordCount, textToSummarize
        );
        
        String summary = generator.generateContent(prompt);
        System.out.println("\n=== SUMMARY ===");
        System.out.println(summary);
        System.out.println("===============\n");
        
        generator.saveToFile(summary, "summaries", "summary");
    }
    
    private static void askQuestion() throws IOException {
        System.out.println("\n❓ ASK AI ANYTHING\n");
        
        String question = getNonEmptyInput("Your question: ");
        
        System.out.print("Detail level (brief/detailed/expert): ");
        String detail = scanner.nextLine().toLowerCase();
        
        System.out.println("\n⏳ Thinking...");
        
        String promptPrefix = switch (detail) {
            case "brief" -> "Answer briefly in 2-3 sentences: ";
            case "expert" -> "Provide an expert-level, detailed explanation with examples: ";
            default -> "Explain clearly in a paragraph or two: ";
        };
        
        String prompt = promptPrefix + question;
        String answer = generator.generateContent(prompt);
        
        System.out.println("\n=== ANSWER ===");
        System.out.println(answer);
        System.out.println("==============\n");
        
        generator.saveToFile("Q: " + question + "\n\nA: " + answer, "qa", "answer");
    }
    
    private static void summarizeFromFile() throws IOException {
        System.out.println("\n📂 SUMMARIZE FROM FILE\n");
        
        String filepath = getNonEmptyInput("File path: ");
        
        try {
            String content = Files.readString(Paths.get(filepath));
            
            System.out.print("Length (short/medium/detailed): ");
            String length = scanner.nextLine().toLowerCase();
            
            System.out.println("\n⏳ Summarizing file...");
            
            String wordCount = switch (length) {
                case "short" -> "50-75 words";
                case "medium" -> "150-200 words";
                case "detailed" -> "300-400 words";
                default -> "150 words";
            };
            
            String prompt = String.format(
                "Summarize the following text in %s.\n\n%s",
                wordCount, content
            );
            
            String summary = generator.generateContent(prompt);
            System.out.println("\n=== SUMMARY ===");
            System.out.println(summary);
            System.out.println("===============\n");
            
            generator.saveToFile(summary, "summaries", "file_summary");
            
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            System.out.println("💡 Make sure file exists and path is correct");
        }
    }
    
    private static void viewHistory() {
        System.out.println("\n📜 GENERATION HISTORY");
        System.out.println("====================\n");
        
        String[] folders = {"blogs", "social", "emails", "products", "summaries", "qa"};
        int totalFiles = 0;
        
        for (String folder : folders) {
            System.out.println("📁 " + folder.toUpperCase() + ":");
            
            File dir = new File("outputs/" + folder);
            
            if (!dir.exists() || !dir.isDirectory()) {
                System.out.println("   (No files yet)\n");
                continue;
            }
            
            File[] files = dir.listFiles();
            
            if (files == null || files.length == 0) {
                System.out.println("   (No files yet)\n");
                continue;
            }
            
            totalFiles += files.length;
            int count = Math.min(files.length, 5);
            
            for (int i = files.length - 1; i >= files.length - count; i--) {
                System.out.println("   - " + files[i].getName());
            }
            
            if (files.length > 5) {
                System.out.println("   ... and " + (files.length - 5) + " more");
            }
            
            System.out.println();
        }
        
        System.out.println("📊 Total files generated: " + totalFiles);
    }
}