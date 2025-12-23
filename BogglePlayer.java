import java.util.ArrayList;
import java.util.List;

public class BogglePlayer {
    private String name;
    private int score;
    private List<String> words;

    public BogglePlayer(String name) {
        this.name = name;
        this.score = 0;
        this.words = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addPoints(int points) {
        this.score += points;
    }

    public int getScore() {
        return score;
    }

    public void addWord(String word) {
        words.add(word);
    }

    public List<String> getWords() {
        return new ArrayList<>(words);
    }

    public void clearWords() {
        words.clear();
    }
}
