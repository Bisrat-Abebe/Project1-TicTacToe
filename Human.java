import java.util.Scanner;

public class Human implements Player {
    private String name;
    private String piece;

    public Human(String name, String piece) {
        this.name = name;
        this.piece = piece;
    }

    public String name(){
        return name;
    }

    public String getPiece() {
        return piece;
    }

}
