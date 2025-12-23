import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class BoggleGame {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Set<String> dictionary = new HashSet<>();

    public static void main(String[] args) {
        // Load dictionary
        loadDictionary();

        // Initialize cubes
        char[][] allLetters = new char[16][6];
        for (int i = 0; i < 16; i++) {
            System.out.println("Enter 6 letters for cube " + (i + 1) + ":");
            for (int j = 0; j < 6; j++) {
                allLetters[i][j] = scanner.next().charAt(0);
            }
        }
        List<BoggleCube> cubes = BoggleCube.makeCubes(allLetters);

        // Initialize players
        System.out.println("Enter the number of players:");
        int numberOfPlayers = scanner.nextInt();
        List<BogglePlayer> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Enter the name of player " + (i + 1) + ":");
            String playerName = scanner.next();
            players.add(new BogglePlayer(playerName));
        }

        // Generate Random Boggle Board
        while (true) {
            char[][] board = new char[4][4];
            List<BoggleCube> usedCubes = new ArrayList<>(cubes);
            Collections.shuffle(usedCubes);
            int cubeIndex = 0;
            for (int row = 0; row < 4; row++) {
                for (int col = 0; col < 4; col++) {
                    board[row][col] = usedCubes.get(cubeIndex++).getRandomLetter();
                }
            }

            // Display the board
            System.out.println("Boggle Board:");
            for (char[] row : board) {
                for (char letter : row) {
                    System.out.print(letter + " ");
                }
                System.out.println();
            }

            // Waits for Player Input and validates them (if they are in the dictionary, more than 2 letters, and if it is a new word)
            Map<String, Set<String>> playerWords = new HashMap<>();
            for (BogglePlayer player : players) {
                player.clearWords();
                System.out.println(player.getName() + ", enter your words (type done in lowercase to finish):");
                while (true) {
                    String word = scanner.next();
                    if (word.equals("done")) break;
                    if (word.length() < 3) {
                        System.out.println("Word must be at least 3 letters long.");
                        continue;
                    }
                    if (!dictionary.contains(word)) {
                        System.out.println("Word is not in the dictionary.");
                        continue;
                    }
                    if (player.getWords().contains(word)) {
                        System.out.println("You've already entered this word.");
                        continue;
                    }
                    player.addWord(word);
                    playerWords.computeIfAbsent(word, k -> new HashSet<>()).add(player.getName());
                }
            }

            // Calculate scores
            for (BogglePlayer player : players) {
                for (String word : player.getWords()) {
                    if (playerWords.get(word).size() == 1) {
                        player.addPoints(fibonacci(word.length() - 2));
                    }
                }
                System.out.println(player.getName() + "'s score: " + player.getScore());
            }

            // Check for a winner
            for (BogglePlayer player: players){
                boolean hasWinner = players.stream().anyMatch(p -> p.getScore() >= 100);
                if (hasWinner) {
                    System.out.println("We have a winner! " + player.getName() + " Wins!");
                    break;
                }
            }
        }
    }

    //Loads words.txt and making sure every word is in uppercase and while removing spaces
    private static void loadDictionary() {
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String word;
            while ((word = br.readLine()) != null) {
                dictionary.add(word.trim().toUpperCase());
            }
        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            System.exit(1);
        }
    }

    //Fibonacci sequence to calculate score
    private static int fibonacci(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        int a = 0, b = 1, c = 1;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }
}
