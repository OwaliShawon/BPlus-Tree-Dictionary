import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to do?");
        System.out.println("1. Insert a word and definition");
        System.out.println("2. Search a Definition");
        System.out.println("3. Print the tree");
        int choice = scanner.nextInt();

        switch (choice){
            case 1: {
                System.out.println("Insert a word");
                String word = scanner.next();
                System.out.println("Insert a Meaning");
                String def = scanner.next();
                tree.insert(word, def);
                System.out.println("Inserted - " +  word + " : "+ def);
                System.out.println("Searching for word '" + word + "': " + tree.search(word));
                break;
            }
            case 2: {
                System.out.println("Insert a word for definition");
                String word = scanner.next();
//                System.out.println(tree.search(word));
                 System.out.println("Definition of word '" + word + "' is: " + tree.search(word));
                break;
            }
            case 3: {
                tree.printTree();
                break;
            }
            default: {
                System.out.println("Invalid choice");
            }
            }
        }
}
