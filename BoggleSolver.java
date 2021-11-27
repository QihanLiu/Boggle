
/**
 *
 * @author Qihan Liu
 * References
 * https://www.jxtxzzw.com/archives/5672
 * 
 **/
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class BoggleSolver {
    private class Trie {
        private final TrieNode root;

        private Trie() {
            root = new TrieNode();
        }

        private void insert(String word) {
            checkNull(word, "words cannot be null!");
            TrieNode node = root;
            char c;
            for (int i = 0; i < word.length(); i++) {
                c = word.charAt(i);
                // get next node
                node = node.put(c);
                if (c == 'Q') {
                    i++;
                    if (i == word.length() || word.charAt(i) != 'U') {
                        // Ignore "Q" and "Qx"
                        return;
                    }
                    node = node.put('U');
                }
            }
            // remember the word in this node
            node.word = word;
        }

        // search if word is in trie
        private boolean search(String word) {
            TrieNode node = trie.root;
            char c;
            for (int i = 0; i < word.length(); i++) {
                c = word.charAt(i);
                // check through letters
                node = node.nextLetters[c - 'A'];
                if (node == null) {
                    return false;
                }
            }
            // see if word is contained in this node.
            return node.word != null;
        }
    }

    private static class TrieNode {
        // UID records the number of boards solved by this trie.
        private int UID = 0;
        // valid word in this node
        private String word = null;
        // 26 letters
        private TrieNode[] nextLetters = new TrieNode[26];

        // put letter in trie
        private TrieNode put(char c) {
            if (nextLetters[c - 'A'] == null) {
                nextLetters[c - 'A'] = new TrieNode();
            }
            return nextLetters[c - 'A'];
        }
    }

    private class Cube {
        // record neighbours of a block
        private int n = 0;
        private final int[] nearRow = new int[8];
        private final int[] nearCol = new int[8];

        private Cube(int row, int col) {
            // get nearby letters in the board.
            for (int newRow = row - 1; newRow <= row + 1; newRow++) {
                for (int newCol = col - 1; newCol <= col + 1; newCol++) {
                    // record valid neighbours
                    if (isValid(row, col, newRow, newCol)) {
                        nearRow[n] = newRow;
                        nearCol[n] = newCol;
                        n++;
                    }
                }
            }
        }
    }

    // check a neighbour index is valid
    private boolean isValid(int row, int col, int newRow, int newCol) {
        // original point
        if (newRow == row && newCol == col) {
            return false;
        }
        // outside of board
        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols) {
            return false;
        }
        return true;
    }

    private boolean[][] marked;
    private int rows, cols, uid = 0;
    private final Trie trie;
    // valid neighbour of each block
    private Cube[][] neighbour;
    private ArrayList<String> validWords;

    // Initializes the data structure using the given array of strings as the
    // dictionary.
    // (You can assume each word in the dictionary contains only the uppercase
    // letters A through Z.)
    public BoggleSolver(String[] dictionary) {
        checkNull(dictionary, "Dictionary cannot be null!");
        trie = new Trie();
        for (String word : dictionary) {
            trie.insert(word);
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        checkNull(board, "Board cannot be null!");
        rows = board.rows();
        cols = board.cols();
        marked = new boolean[rows][cols];
        neighbour = new Cube[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                neighbour[i][j] = new Cube(i, j);
            }
        }
        validWords = new ArrayList<String>();
        // record if and when the word is recorded.
        uid++;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                DFS(board, i, j, trie.root);
            }
        }
        return validWords;
    }

    private void DFS(BoggleBoard board, int row, int col, TrieNode node) {
        char c = board.getLetter(row, col);
        // StdOut.println(" letter: " + c);
        marked[row][col] = true;
        // check next letter
        TrieNode next = node.nextLetters[c - 'A'];
        if (c == 'Q' && next != null) {
            next = next.nextLetters['U' - 'A'];
        }
        // if there is no such letter, finish DFS
        if (next == null) {
            marked[row][col] = false;
            return;
        }
        // if current node gives word, record uid of this board and this word.
        if (next.word != null && next.UID != uid && next.word.length() >= 3) {
            validWords.add(next.word);
            next.UID = uid;
        }
        // get nearby blocks
        Cube block = neighbour[row][col];
        for (int i = 0; i < block.n; i++) {
            // read neighbours
            int newRow = block.nearRow[i], newCol = block.nearCol[i];
            if (marked[newRow][newCol]) {
                continue;
            }
            DFS(board, newRow, newCol, next);
        }
        marked[row][col] = false;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        checkNull(word, "Cannot calculate score of a null word.");
        if (!trie.search(word)) {
            return 0;
        } else if (word.length() < 3) {
            return 0;
        }
        switch (word.length()) {
            case 3:
            case 4:
                return 1;
            case 5:
                return 2;
            case 6:
                return 3;
            case 7:
                return 5;
            default:
                return 11;
        }
    }

    // throw massage when object is null.
    private static void checkNull(Object any, String message) {
        if (any == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void main(String[] args) {
        In in = new In("dictionary-yawl.txt");

        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard("board-test.txt");
        StdOut.println(board.toString());
        // StdOut.println(solver.trie.search("QUEUE"));
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}