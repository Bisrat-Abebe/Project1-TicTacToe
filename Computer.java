import java.lang.Math;

public class Computer implements Player {
    String[] easyNameChoices = {"Jeff", "Bob", "Larry", "Anish"};
    String[] medNameChoices = {"Barack Obama", "George Washington", "Thomas Jefferson"};
    String[] hardNameChoices = {"The Creature", "Biblically Accurate Angel", "Bisrat"};

    private String name;
    private final String piece;
    private final int difficulty;
    private boolean wentFirst;

    public Computer(String piece, int difficulty) {
        this.difficulty = difficulty;
        if (this.difficulty == 1) {
            name = easyNameChoices[(int) (Math.random() * 100) % easyNameChoices.length];
        } else if (this.difficulty == 2) {
            name = medNameChoices[(int) (Math.random() * 100) % medNameChoices.length];
        } else {
            name = hardNameChoices[(int) (Math.random() * 100) % hardNameChoices.length];
        }
        if (piece.equals("X")){
            this.piece = "O";
        } else {
            this.piece = "X";
        }
        wentFirst = false;

    }

    public String getPiece(){
        return piece;
    }

    public String name(){
        return name;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public void setFirst(int num) {
        if (num == 2) {
            wentFirst = true;
        }
    }

    public boolean getFirst() {
        return wentFirst;
    }
}
