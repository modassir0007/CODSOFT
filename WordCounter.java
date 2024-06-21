import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class WordCounter {
    private static final Set<String> STOP_WORDS = new HashSet<>();

    static {
        String[] stopWordsArray = {"a", "an", "and", "the", "in", "on", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "out", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
        for (String word : stopWordsArray) {
            STOP_WORDS.add(word.toLowerCase());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Word Counter!");

        String text = "";
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter '1' to input text or '2' to provide a file:");
            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                System.out.println("Please enter your text:");
                text = scanner.nextLine();
                if (!text.trim().isEmpty()) {
                    validInput = true;
                } else {
                    System.out.println("Input text cannot be empty. Please try again.");
                }
            } else if (choice.equals("2")) {
                System.out.println("Please provide the file path:");
                String filePath = scanner.nextLine();
                try {
                    text = readFile(filePath);
                    validInput = true;
                } catch (FileNotFoundException e) {
                    System.out.println("File not found. Please try again.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        countWords(text);
        scanner.close();
    }

    private static String readFile(String filePath) throws FileNotFoundException {
        StringBuilder content = new StringBuilder();
        File file = new File(filePath);
        Scanner fileScanner = new Scanner(file);

        while (fileScanner.hasNextLine()) {
            content.append(fileScanner.nextLine()).append(" ");
        }

        fileScanner.close();
        return content.toString();
    }

    private static void countWords(String text) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        int totalWords = 0;
        int uniqueWords = 0;

        // Regular expression to match words
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
        Scanner textScanner = new Scanner(text.toLowerCase());

        while (textScanner.hasNext(pattern)) {
            String word = textScanner.next(pattern);
            if (!STOP_WORDS.contains(word)) {
                totalWords++;
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        textScanner.close();
        uniqueWords = wordCount.size();

        System.out.println("Total words (excluding stop words): " + totalWords);
        System.out.println("Number of unique words: " + uniqueWords);
        System.out.println("Word frequencies:");

        for (HashMap.Entry<String, Integer> entry : wordCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
