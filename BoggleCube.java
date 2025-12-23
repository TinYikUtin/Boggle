import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BoggleCube {
    private char[] letters;

    public BoggleCube(char[] letters) {
        this.letters = letters;
    }

    public char getRandomLetter() {
        Random rand = new Random();
        int index = rand.nextInt(letters.length);
        return letters[index];
    }

    public static List<BoggleCube> makeCubes(char[][] allLetters) {
        List<BoggleCube> cubes = new ArrayList<>();
        for (char[] letters : allLetters) {
            cubes.add(new BoggleCube(letters));
        }
        return cubes;
    }
}
