import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    private static ContentGenerator generator;
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        try {
            // Load API key
            String apiKey = Files.readString(Paths.get("api-key.txt")).trim();
            generator = new ContentGenerator(apiKey);
            
            System.out.println("🤖 AI Content Assistant - Powered by Groq");
            System.out.println("==========================================\n");
            
            // Main menu loop
            while (true) {
                displayMenu();
                int choice = getChoice();
                
                if (choice == 5) {
                    System.out.println("\n👋 Thanks for using AI Content Assistant!");
                    break;
                }
                
                handleChoice(choice);
            }
            
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== What would you like to create? ===");
        System.out.println("1. 📝 Blog Post");
        System.out.println("2. 📱  Social Media Post");
        System.out.println("3. ✉️  Email");
        System.out.println("4. 🛍️  Product Description");
        System.out.println("5. 🚪 Exit");
        System.out.print("\nChoose (1-5): ");
    }
    
    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
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
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        } catch (IOException e) {
            System.out.println("❌ Error generating content: " + e.getMessage());
        }
    }
    
    private static void generateBlogPost() throws IOException {
        System.out.print("\n📝 Blog topic: ");
        String topic = scanner.nextLine();
        
        System.out.print("Target audience (e.g., beginners, professionals): ");
        String audience = scanner.nextLine();
        
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
        System.out.print("\n📱 Post topic/message: ");
        String topic = scanner.nextLine();
        
        System.out.print("Platform (LinkedIn/Twitter/Instagram): ");
        String platform = scanner.nextLine();
        
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
        System.out.print("\n✉️  Email purpose (e.g., job application, inquiry, follow-up): ");
        String purpose = scanner.nextLine();
        
        System.out.print("Recipient/Context: ");
        String context = scanner.nextLine();
        
        System.out.print("Tone (professional/friendly/urgent): ");
        String tone = scanner.nextLine();
        
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
        System.out.print("\n🛍️  Product name: ");
        String product = scanner.nextLine();
        
        System.out.print("Key features (comma-separated): ");
        String features = scanner.nextLine();
        
        System.out.println("\n⏳ Generating product description...");
        
        String prompt = String.format(
            "Write a compelling product description for: %s. " +
            "Features: %s. " +
            "Include a catchy headline, benefits-focused copy (100-150 words), " +
            "and a call-to-action. Make it persuasive and SEO-friendly.",
            product, features
        );
        
        String content = generator.generateContent(prompt);
        System.out.println("\n" + content);
        
        generator.saveToFile(content, "products", "product");
    }
}