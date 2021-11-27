
/**
 *
 * @author Qihan Liu
 * 
 * 
 **/
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BoggleBoard {
    // the 16 Boggle dice (1992 version)
    private static final String[] BOGGLE_1992 = {
            "LRYTTE", "VTHRWE", "EGHWNE", "SEOTIS",
            "ANAEEG", "IDSYTT", "OATTOW", "MTOICU",
            "AFPKFS", "XLDERI", "HCPOAS", "ENSIEU",
            "YLDEVR", "ZNRNHL", "NMIQHU", "OBBAOJ"
    };

    // the 16 Boggle dice (1983 version)
    private static final String[] BOGGLE_1983 = {
            "AACIOT", "ABILTY", "ABJMOQ", "ACDEMP",
            "ACELRS", "ADENVZ", "AHMORS", "BIFORX",
            "DENOSW", "DKNOTU", "EEFHIY", "EGINTV",
            "EGKLUY", "EHINPS", "ELPSTU", "GILRUW",
    };

    // the 25 Boggle Master / Boggle Deluxe dice
    private static final String[] BOGGLE_MASTER = {
            "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
            "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCNSTW",
            "CEIILT", "CEILPT", "CEIPST", "DDLNOR", "DHHLOR",
            "DHHNOT", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
            "FIPRSY", "GORRVW", "HIPRRY", "NOOTUW", "OOOTTU"
    };

    // the 25 Big Boggle dice
    private static final String[] BOGGLE_BIG = {
            "AAAFRS", "AAEEEE", "AAFIRS", "ADENNN", "AEEEEM",
            "AEEGMU", "AEGMNN", "AFIRSY", "BJKQXZ", "CCENST",
            "CEIILT", "CEILPT", "CEIPST", "DDHNOT", "DHHLOR",
            "DHLNOR", "DHLNOR", "EIIITT", "EMOTTT", "ENSSSU",
            "FIPRSY", "GORRVW", "IPRRRY", "NOOTUW", "OOOTTU"
    };
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final double[] FREQUENCIES = {
            0.08167, 0.01492, 0.02782, 0.04253, 0.12703, 0.02228,
            0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
            0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
            0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
            0.01974, 0.00074
    };

    private final int m; // number of rows
    private final int n; // number of columns
    private char[][] board; // the m-by-n array of characters
    // Initializes a random 4-by-4 Boggle board.
    // (by rolling the Hasbro dice)

    public BoggleBoard() {
        m = 4;
        n = 4;
        StdRandom.shuffle(BOGGLE_1992);
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letters = BOGGLE_1992[n * i + j];
                int r = StdRandom.uniform(letters.length());
                board[i][j] = letters.charAt(r);
            }
        }
    }

    // Initializes a random m-by-n Boggle board.
    // (using the frequency of letters in the English language)
    public BoggleBoard(int m, int n) {
        this.m = m;
        this.n = n;
        if (m <= 0)
            throw new IllegalArgumentException("rows can't be a negative");
        if (n <= 0)
            throw new IllegalArgumentException("cols can't be a negative");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int r = StdRandom.discrete(FREQUENCIES);
                board[i][j] = ALPHABET.charAt(r);
            }
        }
    }

    // Initializes a Boggle board from the specified filename.
    public BoggleBoard(String filename) {
        In in = new In(filename);
        m = in.readInt();
        n = in.readInt();
        if (m <= 0)
            throw new IllegalArgumentException("number of rows must be a positive integer");
        if (n <= 0)
            throw new IllegalArgumentException("number of columns must be a positive integer");
        board = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                String letter = in.readString().toUpperCase();
                if (letter.equals("QU"))
                    board[i][j] = 'Q';
                else if (letter.length() != 1)
                    throw new IllegalArgumentException("invalid character: " + letter);
                else if (!ALPHABET.contains(letter))
                    throw new IllegalArgumentException("invalid character: " + letter);
                else
                    board[i][j] = letter.charAt(0);
            }
        }
    }

    // // Initializes a Boggle board from the 2d char array.
    // // (with 'Q' representing the two-letter sequence "Qu")
    // public BoggleBoard(char[][] a) {

    // }

    // Returns the number of rows.
    public int rows() {
        return m;
    }

    // Returns the number of columns.
    public int cols() {
        return n;
    }

    // Returns the letter in row i and column j.
    // (with 'Q' representing the two-letter sequence "Qu")
    public char getLetter(int i, int j) {
        return board[i][j];
    }

    // Returns a string representation of the board.
    public String toString() {
        StringBuilder sb = new StringBuilder(m + " " + n + "\n");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(board[i][j]);
                if (board[i][j] == 'Q')
                    sb.append("u ");
                else
                    sb.append("  ");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        BoggleBoard BB = new BoggleBoard();
        StdOut.println(BB.toString());
    }
}