import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int degree = 30;
        BPlusTree tree = new BPlusTree(degree);

        String csvFile = "data.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2); // Split into word and definition
                String word = parts[0].trim();
                String definition = parts[1].trim();
                tree.insert(word, definition);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("B+ Tree structure:");
        tree.printTree();

        // Search for words
        String searchWord = "able";
        System.out.println("Searching for word '" + searchWord + "': " + tree.search(searchWord));

        searchWord = "unknown";
        System.out.println("Searching for word '" + searchWord + "': " + tree.search(searchWord));

        // insert and  print a test word
        tree.insert("hijibiji", "hijibiji");
        System.out.println("Searching for word '" + "hijibiji" + "': " + tree.search("hijibiji"));
    }
}
