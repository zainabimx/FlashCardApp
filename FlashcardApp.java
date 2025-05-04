import java.util.*;
import java.io.*;

public class FlashcardApp {
    private static final String FILE_NAME = "flashcards.txt";
    private static List<Flashcard> flashcards = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadFlashcards();
        while (true) {
            System.out.println("\n=== Flashcard Menu ===");
            System.out.println("1. Add Flashcard");
            System.out.println("2. Start Quiz");
            System.out.println("3. Save Flashcards");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addFlashcard(); break;
                case "2": startQuiz(); break;
                case "3": saveFlashcards(); break;
                case "4": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void addFlashcard() {
        System.out.print("Enter question: ");
        String question = scanner.nextLine();
        System.out.print("Enter answer: ");
        String answer = scanner.nextLine();
        flashcards.add(new Flashcard(question, answer));
        System.out.println("Flashcard added!");
    }

    private static void startQuiz() {
        if (flashcards.isEmpty()) {
            System.out.println("No flashcards available.");
            return;
        }

        Collections.shuffle(flashcards);
        for (Flashcard card : flashcards) {
            System.out.println("\nQuestion: " + card.getQuestion());
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();
            if (userAnswer.equalsIgnoreCase(card.getAnswer())) {
                System.out.println("✅ Correct!");
            } else {
                System.out.println("❌ Incorrect! Correct answer: " + card.getAnswer());
            }
        }
    }

    private static void saveFlashcards() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Flashcard card : flashcards) {
                writer.println(card.getQuestion() + "|" + card.getAnswer());
            }
            System.out.println("Flashcards saved.");
        } catch (IOException e) {
            System.out.println("Error saving flashcards.");
        }
    }

    private static void loadFlashcards() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] parts = fileScanner.nextLine().split("\\|");
                if (parts.length == 2) {
                    flashcards.add(new Flashcard(parts[0], parts[1]));
                }
            }
            System.out.println("Flashcards loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading flashcards.");
        }
    }
}
