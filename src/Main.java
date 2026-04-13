import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * AI Content Assistant - Main Application
 * 
 * A command-line tool for generating various types of content using AI.
 * Features include blog generation, email drafting, text summarization, and
 * Q&A.
 * 
 * @author Abdul Rafay
 * @version 1.0
 */

public class Main {
    private static ContentGenerator generator;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main entry point of the application.
     * Loads API key, initializes generator, and runs the main menu loop.
     */

    public static void main(String[] args) {
        try {
            // Load API key
            String apiKey = Files.readString(Paths.get("api-key.txt")).trim();
            generator = new ContentGenerator(apiKey);

            printWelcome();

            // Main loop
            while (true) {
                displayMenu();
                int choice = getChoice();

                if (choice == 10) { // Exit
                    System.out.println("\n👋 Thanks for using AI Content Assistant!");
                    break;
                }

                handleChoice(choice);
            }

        } catch (Exception e) {
            System.out.println("❌ Fatal Error: " + e.getMessage());
            System.out.println("\n💡 Common fixes:");
            System.out.println("   1. Check api-key.txt exists and has valid key");
            System.out.println("   2. Check your internet connection");
            System.out.println("   3. Try using mobile data instead of WiFi");
        } finally {
            scanner.close();
        }
    }

    // NEW: Welcome message
    private static void printWelcome() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   🤖 AI CONTENT ASSISTANT v1.0          ║");
        System.out.println("║   Powered by Groq (Llama 3.3)          ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n💡 First time? Try option 0 for a tutorial!");
    }

    /**
     * Displays the main menu with all available options.
     * Menu includes content generation, summarization, Q&A, and utility features.
     */

    private static void displayMenu() {
        System.out.println("\n===== MAIN MENU ===");
        System.out.println("0. 📚 Tutorial (First time? Start here)");
        System.out.println("1. 📝 Generate Blog Post");
        System.out.println("2. 📱 Generate Social Media Post");
        System.out.println("3. ✉️ Generate Email");
        System.out.println("4. 🛍️ Generate Product Description");
        System.out.println("5. 📄 Summarize Text");
        System.out.println("6. ❓ Ask AI Anything");
        System.out.println("7. 📂 Summarize from File");
        System.out.println("8. 📜 View History");
        System.out.println("9. 💡 Tips & Tricks");
        System.out.println("10. 🚪 Exit");

        System.out.print("\nYour choice: ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void handleChoice(int choice) {
        try {
            switch (choice) {
                case 0 -> showTutorial(); // NEW
                case 1 -> generateBlogPost();
                case 2 -> generateSocialPost();
                case 3 -> generateEmail();
                case 4 -> generateProductDescription();
                case 5 -> summarizeText();
                case 6 -> askQuestion();
                case 7 -> summarizeFromFile();
                case 8 -> viewHistory();
                case 9 -> showTips(); // NEW
                default -> System.out.println("❌ Invalid choice. Please enter 0-10.");
            }
        } catch (IOException e) {
            System.out.println("\n❌ Error: " + e.getMessage());
            System.out.println("💡 The program will continue. Try again or choose a different option.");
        }
    }

    // NEW: Tutorial for first-time users
    private static void showTutorial() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║           QUICK TUTORIAL               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\nWelcome! Here's how to use this tool:\n");

        System.out.println("📝 CONTENT GENERATION (Options 1-4):");
        System.out.println("   - Choose what type of content you want");
        System.out.println("   - Answer a few questions (topic, audience, etc.)");
        System.out.println("   - AI generates professional content");
        System.out.println("   - Saved automatically to outputs/ folder\n");

        System.out.println("📄 SUMMARIZATION (Option 5 & 7):");
        System.out.println("   - Paste long text or upload a file");
        System.out.println("   - Get a concise summary");
        System.out.println("   - Choose short/medium/detailed\n");

        System.out.println("❓ Q&A (Option 6):");
        System.out.println("   - Ask ANY question");
        System.out.println("   - Get detailed AI explanations");
        System.out.println("   - Great for learning!\n");

        System.out.println("📜 HISTORY (Option 8):");
        System.out.println("   - See all your past generations");
        System.out.println("   - Files organized by type\n");

        System.out.println("💡 TIP: All outputs are saved automatically!");
        System.out.println("    Check the outputs/ folder to find them.\n");

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // NEW: Tips and tricks
    private static void showTips() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         TIPS & TRICKS                  ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        System.out.println("🎯 GETTING BETTER RESULTS:\n");
        System.out.println("1. Be Specific:");
        System.out.println("   ❌ 'Write about technology'");
        System.out.println("   ✅ 'Write about how AI is changing education for college students'\n");

        System.out.println("2. Mention Your Audience:");
        System.out.println("   - Helps AI adjust tone and complexity");
        System.out.println("   - Examples: beginners, professionals, students\n");

        System.out.println("3. Regenerate if Needed:");
        System.out.println("   - Not happy with result? Try again!");
        System.out.println("   - Rephrase your prompt slightly\n");

        System.out.println("4. File Organization:");
        System.out.println("   - All outputs in outputs/ folder");
        System.out.println("   - Organized by type (blogs, emails, etc.)");
        System.out.println("   - Timestamped filenames\n");

        System.out.println("⚡ QUICK WORKFLOW:\n");
        System.out.println("   Generate → Review → Edit if needed → Use!");
        System.out.println("   AI does 80% of work, you polish the 20%\n");

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // UPDATED: Blog generation with validation
    private static void generateBlogPost() throws IOException {
        System.out.println("\n📝 BLOG POST GENERATOR");
        System.out.println("======================");

        System.out.print("\nBlog topic: ");
        String topic = scanner.nextLine().trim();

        // Validation
        if (topic.isEmpty()) {
            System.out.println("❌ Topic cannot be empty!");
            return;
        }

        System.out.print("Target audience (e.g., beginners, professionals): ");
        String audience = scanner.nextLine().trim();

        if (audience.isEmpty()) {
            audience = "general readers"; // Default
        }

        System.out.println("\n⏳ Generating blog post... (this may take 5-10 seconds)");

        String prompt = String.format(
                "Write a 300-word blog post about '%s' for %s. " +
                        "Include an engaging intro, 2-3 main points, and a conclusion. " +
                        "Use a conversational but informative tone.",
                topic, audience);

        String content = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println(content);
        System.out.println("=".repeat(50));

        generator.saveToFile(content, "blogs", "blog");
    }

    // UPDATED: Social post with validation
    private static void generateSocialPost() throws IOException {
        System.out.println("\n📱 SOCIAL MEDIA POST GENERATOR");
        System.out.println("==============================");

        System.out.print("\nPost topic/message: ");
        String topic = scanner.nextLine().trim();

        if (topic.isEmpty()) {
            System.out.println("❌ Topic cannot be empty!");
            return;
        }

        System.out.print("Platform (LinkedIn/Twitter/Instagram): ");
        String platform = scanner.nextLine().trim();

        if (platform.isEmpty()) {
            platform = "LinkedIn"; // Default
        }

        System.out.println("\n⏳ Generating social post...");

        String prompt = String.format(
                "Create a %s post about: %s. " +
                        "Keep it engaging, concise, and include relevant emojis. " +
                        "Max 280 characters for Twitter, 150 words for LinkedIn/Instagram.",
                platform, topic);

        String content = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println(content);
        System.out.println("=".repeat(50));

        generator.saveToFile(content, "social", platform.toLowerCase());
    }

    // UPDATED: Email with validation
    private static void generateEmail() throws IOException {
        System.out.println("\n✉️  EMAIL GENERATOR");
        System.out.println("==================");

        System.out.print("\nEmail purpose (e.g., job application, inquiry): ");
        String purpose = scanner.nextLine().trim();

        if (purpose.isEmpty()) {
            System.out.println("❌ Purpose cannot be empty!");
            return;
        }

        System.out.print("Recipient/Context: ");
        String context = scanner.nextLine().trim();

        System.out.print("Tone (professional/friendly/urgent): ");
        String tone = scanner.nextLine().trim();

        if (tone.isEmpty()) {
            tone = "professional"; // Default
        }

        System.out.println("\n⏳ Generating email...");

        String prompt = String.format(
                "Write a %s email for: %s. Context: %s. " +
                        "Include subject line, greeting, body (3-4 paragraphs), and closing.",
                tone, purpose, context);

        String content = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println(content);
        System.out.println("=".repeat(50));

        generator.saveToFile(content, "emails", "email");
    }

    // UPDATED: Product description with validation
    private static void generateProductDescription() throws IOException {
        System.out.println("\n🛍️  PRODUCT DESCRIPTION GENERATOR");
        System.out.println("=================================");

        System.out.print("\nProduct name: ");
        String product = scanner.nextLine().trim();

        if (product.isEmpty()) {
            System.out.println("❌ Product name cannot be empty!");
            return;
        }

        System.out.print("Key features (comma-separated): ");
        String features = scanner.nextLine().trim();

        if (features.isEmpty()) {
            System.out.println("❌ Features cannot be empty!");
            return;
        }

        System.out.println("\n⏳ Generating product description...");

        String prompt = String.format(
                "Write a compelling product description for: %s. " +
                        "Features: %s. " +
                        "Include a catchy headline, benefits-focused copy (100-150 words), " +
                        "and a call-to-action. Make it persuasive and SEO-friendly.",
                product, features);

        String content = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println(content);
        System.out.println("=".repeat(50));

        generator.saveToFile(content, "products", "product");
    }

    private static void summarizeText() throws IOException {
        System.out.println("\n📄 TEXT SUMMARIZER");
        System.out.println("==================");
        System.out.println("\nPaste your text below (press Enter twice when done):");
        System.out.println("-".repeat(50));

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

        System.out.print("\nSummary length (short/medium/detailed): ");
        String length = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n⏳ Summarizing...");

        String wordCount = switch (length) {
            case "short" -> "50-75 words";
            case "detailed" -> "300-400 words";
            default -> "150-200 words"; // medium or anything else
        };

        String prompt = String.format(
                "Summarize the following text in %s. " +
                        "Extract key points and main ideas. Keep it clear and concise.\n\n" +
                        "Text to summarize:\n%s",
                wordCount, textToSummarize);

        String summary = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("SUMMARY:");
        System.out.println("=".repeat(50));
        System.out.println(summary);
        System.out.println("=".repeat(50));

        generator.saveToFile(summary, "summaries", "summary");
    }

    private static void askQuestion() throws IOException {
        System.out.println("\n❓ ASK AI ANYTHING");
        System.out.println("==================");

        System.out.print("\nYour question: ");
        String question = scanner.nextLine().trim();

        if (question.isEmpty()) {
            System.out.println("❌ Question cannot be empty!");
            return;
        }

        System.out.print("Detail level (brief/detailed/expert): ");
        String detail = scanner.nextLine().trim().toLowerCase();

        System.out.println("\n⏳ Thinking...");

        String promptPrefix = switch (detail) {
            case "brief" -> "Answer briefly in 2-3 sentences: ";
            case "expert" -> "Provide an expert-level, detailed explanation with examples: ";
            default -> "Explain clearly in a paragraph or two: ";
        };

        String prompt = promptPrefix + question;
        String answer = generator.generateContent(prompt);

        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANSWER:");
        System.out.println("=".repeat(50));
        System.out.println(answer);
        System.out.println("=".repeat(50));

        generator.saveToFile(
                "Question: " + question + "\n\n" + answer,
                "qa",
                "answer");
    }

    private static void summarizeFromFile() throws IOException {
        System.out.println("\n📂 FILE SUMMARIZER");
        System.out.println("==================");

        System.out.print("\nEnter file path (e.g., article.txt): ");
        String filepath = scanner.nextLine().trim();

        if (filepath.isEmpty()) {
            System.out.println("❌ File path cannot be empty!");
            return;
        }

        try {
            String content = Files.readString(Paths.get(filepath));

            if (content.trim().isEmpty()) {
                System.out.println("❌ File is empty!");
                return;
            }

            System.out.print("\nSummary length (short/medium/detailed): ");
            String length = scanner.nextLine().trim().toLowerCase();

            System.out.println("\n⏳ Summarizing file...");

            String wordCount = switch (length) {
                case "short" -> "50-75 words";
                case "detailed" -> "300-400 words";
                default -> "150-200 words";
            };

            String prompt = String.format(
                    "Summarize the following text in %s. " +
                            "Extract key points and main ideas.\n\n%s",
                    wordCount, content);

            String summary = generator.generateContent(prompt);

            System.out.println("\n" + "=".repeat(50));
            System.out.println("FILE SUMMARY:");
            System.out.println("=".repeat(50));
            System.out.println(summary);
            System.out.println("=".repeat(50));

            generator.saveToFile(summary, "summaries", "file_summary");

        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
            System.out.println("💡 Tips:");
            System.out.println("   - Make sure the file exists");
            System.out.println("   - Check the file path is correct");
            System.out.println("   - Try using full path (e.g., C:\\Users\\...\\file.txt)");
        }
    }

    private static void viewHistory() {
        System.out.println("\n📜 GENERATION HISTORY");
        System.out.println("====================\n");

        String[] folders = { "blogs", "social", "emails", "products", "summaries", "qa" };
        boolean hasAnyFiles = false;

        for (String folder : folders) {
            File dir = new File("outputs/" + folder);

            if (!dir.exists() || !dir.isDirectory()) {
                continue;
            }

            File[] files = dir.listFiles();

            if (files == null || files.length == 0) {
                continue;
            }

            hasAnyFiles = true;
            System.out.println("📁 " + folder.toUpperCase() + " (" + files.length + " files):");

            int count = Math.min(files.length, 5);
            for (int i = files.length - 1; i >= files.length - count; i--) {
                System.out.println("   • " + files[i].getName());
            }

            if (files.length > 5) {
                System.out.println("   ... and " + (files.length - 5) + " more");
            }

            System.out.println();
        }

        if (!hasAnyFiles) {
            System.out.println("📭 No files yet! Generate some content to see history.\n");
        }

        System.out.println("💡 All files are in the outputs/ folder\n");
    }
}