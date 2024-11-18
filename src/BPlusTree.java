import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Node class for B+ Tree
class Node {
    List<String> words;          // List of words
    List<String> definitions;    // Corresponding definitions
    List<Node> children;         // Child nodes
    boolean isLeaf;              // True if the node is a leaf

    Node(boolean isLeaf) {
        this.words = new ArrayList<>();
        this.definitions = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = isLeaf;
    }
}

// B+ Tree Implementation
public class BPlusTree {
    private final int T; // Degree of the B+ Tree
    private Node root;

    public BPlusTree(int degree) {
        this.T = degree;
        this.root = new Node(true);
    }

    // Insert a word and definition into the B+ Tree
    public void insert(String word, String definition) {
        Node r = root;
        if (r.words.size() == 2 * T - 1) {
            Node newRoot = new Node(false);
            newRoot.children.add(r);
            splitChild(newRoot, 0, r);
            root = newRoot;
            insertNonFull(newRoot, word, definition);
        } else {
            insertNonFull(r, word, definition);
        }
    }

    private void insertNonFull(Node node, String word, String definition) {
        int i = node.words.size() - 1;
        if (node.isLeaf) {
            node.words.add(""); // Placeholder
            node.definitions.add("");
            while (i >= 0 && word.compareTo(node.words.get(i)) < 0) {
                node.words.set(i + 1, node.words.get(i));
                node.definitions.set(i + 1, node.definitions.get(i));
                i--;
            }
            node.words.set(i + 1, word);
            node.definitions.set(i + 1, definition);
        } else {
            while (i >= 0 && word.compareTo(node.words.get(i)) < 0) {
                i--;
            }
            i++;
            Node child = node.children.get(i);
            if (child.words.size() == 2 * T - 1) {
                splitChild(node, i, child);
                if (word.compareTo(node.words.get(i)) > 0) {
                    i++;
                }
            }
            insertNonFull(node.children.get(i), word, definition);
        }
    }

    private void splitChild(Node parent, int index, Node child) {
        Node newChild = new Node(child.isLeaf);
        parent.words.add(index, child.words.get(T - 1));
        parent.definitions.add(index, child.definitions.get(T - 1));
        parent.children.add(index + 1, newChild);

        newChild.words.addAll(child.words.subList(T, child.words.size()));
        newChild.definitions.addAll(child.definitions.subList(T, child.definitions.size()));

        child.words = new ArrayList<>(child.words.subList(0, T - 1));
        child.definitions = new ArrayList<>(child.definitions.subList(0, T - 1));

        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(T, child.children.size()));
            child.children = new ArrayList<>(child.children.subList(0, T));
        }
    }

    // Search for a definition by word
    public String search(String word) {
        return search(root, word);
    }

    private String search(Node node, String word) {
        int i = 0;
        while (i < node.words.size() && word.compareTo(node.words.get(i)) > 0) {
            i++;
        }
        if (i < node.words.size() && word.equals(node.words.get(i))) {
            return node.definitions.get(i); // Word found
        }
        if (node.isLeaf) {
            return "Word not found";
        }
        return search(node.children.get(i), word);
    }

    // Print the B+ Tree (for debugging)
    public void printTree() {
        printTree(root, 0);
    }

    private void printTree(Node node, int level) {
        System.out.println("Level " + level + ": Words " + node.words + ", Definitions " + node.definitions);
        if (!node.isLeaf) {
            for (Node child : node.children) {
                printTree(child, level + 1);
            }
        }
    }
}

