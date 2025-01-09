import java.util.Scanner;
import java.lang.Math;

public class TicTacToe {

    private String[][] table;
    private int spacesLeft;
    private Player p1;
    private Player p2;

    // variables that represents whether the human player chose a specific space on the table
    // on THEIR first turn
    private boolean wentCorner;
    private boolean wentCenter;
    private boolean wentAdjacent;


    //variables holding the first piece placed by the human player
    private String firstPiece;
    private String lastOppPiece;


    //Constructor for the tic-tac-toe game.
    public TicTacToe(Player p1, Player p2) {
        table = new String[3][3];
        this.p1 = p1;
        this.p2 = p2;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                table[i][j] = "  ";
            }
        }
        spacesLeft = 9;
        wentCorner = false;
        wentCenter = false;
        wentAdjacent = false;
        firstPiece = null;
        lastOppPiece = null;
    }

    /**
     * return the table
     */
    public String[][] getTable() {
        return table;
    }

    /**
     * Returns whether the given location is a valid option to place down a piece.
     */
    public boolean validMove(String choice) {
        String space = choice.trim();
        if (!space.equals("00") && !space.equals("01") && !space.equals("02") && !space.equals("10")
                && !space.equals("11") && !space.equals("12") && !space.equals("20")
                && !space.equals("21") && !space.equals("22")) {
            System.out.println("\nInvalid space, try again. ");
            return false;
        }
        if (!table[getRow(space)][getCol(space)].equals("  ")) {
            System.out.println("\nThere is already a piece there. Try again. ");
            return false;
        }

        return true;
    }


    public void makeMove(Player p1) {
        if (!gameOver()) {
            boolean validChoice = false;
            String spaceChosen = null;
            while (!validChoice) {
                Scanner space = new Scanner(System.in);
                System.out.println("Choose your space: ");
                spaceChosen = space.nextLine();
                validChoice = validMove(spaceChosen);
            }
            table[getRow(spaceChosen)][getCol(spaceChosen)] = p1.getPiece() + " ";
            lastOppPiece = spaceChosen;
            spacesLeft -= 1;

        }
    }


    //diff 1 means easy, 2 means medium, 3 means hard
    public void computerMove(Computer player) {
        //Holds all possible spaces that bot can choose from
        String[] choices = new String[9];

        int size = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals("  ")) {
                    choices[size] = Integer.toString(i) + Integer.toString(j);
                    size++;
                }
            }
        }

        if (player.getDifficulty() == 1) {
            easyCompMove(choices, player, size);
        }
        if (player.getDifficulty() == 2) {
            medCompMove(choices, player, size);
        }
        if (player.getDifficulty() == 3) {
            hardCompMove(choices, player, size);
        }
        spacesLeft -= 1;
    }

    public void easyCompMove(String[] choices, Computer player, int size) {
        String choiceMade = choices[(int) (100 * Math.random()) % size];
        int row = Integer.parseInt(choiceMade) / 10;
        int col = Integer.parseInt(choiceMade) % 10;
        table[row][col] = player.getPiece() + " ";
    }

    public void medCompMove(String[] choices, Computer player, int size) {
        int choice = (((int) (100 * Math.random())) % 10) + 1;
        if (choice < 2) {
            easyCompMove(choices, player, size);
        } else {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else if (canWin(p1)) {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            } else {
                if (spacesLeft == 9) {
                    String cornerChosen = corners(choices)[((int) (100 * Math.random())) % 4];
                    table[getRow(cornerChosen)][getCol(cornerChosen)] = player.getPiece() + " ";
                } else {
                    easyCompMove(choices, player, size);
                }
            }
        }
    }

    public void hardCompMove(String[] choices, Computer player, int size) {
        if (spacesLeft == 9) {
            String cornerChosen = corners(choices)[((int) (100 * Math.random())) % 4];
            table[getRow(cornerChosen)][getCol(cornerChosen)] = player.getPiece() + " ";

        } else if (spacesLeft == 8) {
            firstPiece = getFirstPiece();

            if (firstPiece.equals("00") || firstPiece.equals("20") || firstPiece.equals("02")
                    || firstPiece.equals("22")) {
                wentCorner = true;
            } else if (firstPiece.equals("01") || firstPiece.equals("10") || firstPiece.equals("21")
                    || firstPiece.equals("12")) {
                wentAdjacent = true;
            }

            //Runs when player starts off on a corner as first move
            if (wentCorner || wentAdjacent) {
                table[1][1] = player.getPiece() + " ";
            } else {
                wentCenter = true;
                String cornerChosen = corners(choices)[((int) (100 * Math.random())) % 4];
                table[getRow(cornerChosen)][getCol(cornerChosen)] = player.getPiece() + " ";
            }

        } else if (spacesLeft == 7) {
            wentCenter(choices);
            wentAdjacent(choices);
            String[] corners = corners(choices);

            if (numCorners(corners) == 2) {
                wentCorner = true;
                int chooseIndex = (int) (2 * Math.random());
                String chosenCorner = corners[chooseIndex];
                int row = Integer.parseInt(chosenCorner) / 10;
                int col = Integer.parseInt(chosenCorner) % 10;
                table[row][col] = player.getPiece() + " ";
            } else if (wentCenter) {
                if (!table[0][0].equals("  ")) {
                    table[2][2] = player.getPiece() + " ";
                } else if (!table[0][2].equals("  ")) {
                    table[2][0] = player.getPiece() + " ";
                } else if (!table[2][0].equals("  ")) {
                    table[0][2] = player.getPiece() + " ";
                } else {
                    table[0][0] = player.getPiece() + " ";
                }
            } else if (wentAdjacent) {
                table[1][1] = player.getPiece() + " ";
            }

        } else if (spacesLeft == 6) {
            if (canWin(p1)) {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            } else if (wentCorner) {
                if (Math.abs(getCol(firstPiece) - getCol(lastOppPiece)) == 2) {
                    table[2][1] = player.getPiece() + " ";
                } else {
                    table[1][emptyColNum()] = player.getPiece() + " ";
                }
            } else if (wentAdjacent) {
                if (linedUp()) {
                    String[] corners = corners(choices);
                    int chooseIndex = (int) (2 * Math.random());
                    String chosenCorner = corners[chooseIndex];
                    int row = Integer.parseInt(chosenCorner) / 10;
                    int col = Integer.parseInt(chosenCorner) % 10;
                    table[row][col] = player.getPiece() + " ";
                } else if (numCorners(corners(choices)) == 4) {
                    String badRow = Integer.toString(emptyRowNum());
                    String badCol = Integer.toString(emptyColNum());
                    String badCorner = badRow + badCol;
                    String[] goodCorners = new String[3];
                    int count = 0;
                    for (String corner : corners(choices)) {
                        if (corner != null) {
                            if (!corner.equals(badCorner)) {
                                goodCorners[count] = corner;
                                count++;
                            }
                        }
                    }
                    String chosenCorner = goodCorners[(((int) (100 * Math.random())) % 3)];
                    table[getRow(chosenCorner)][getCol(chosenCorner)] = player.getPiece() + " ";
                } else {
                    if (emptyRowNum() != -1) {
                        int row = 0;
                        if (emptyRowNum() == 0) {
                            row = 2;
                        } else {
                            row = 0;
                        }
                        table[row][(getCol((lastOppPiece)) + 2) % 4] = player.getPiece() + " ";
                    } else if (emptyColNum() != -1) {
                        int col = 0;
                        if (emptyColNum() == 0) {
                            col = 2;
                        } else {
                            col = 0;
                        }

                        table[(getRow(lastOppPiece) + 2) % 4][col] = player.getPiece() + " ";
                    }
                }
            } else if (wentCenter) {
                String chosenCorner = corners(choices)[(((int) (100 * Math.random())) % 2)];
                table[getRow(chosenCorner)][getCol(chosenCorner)] = player.getPiece() + " ";
            }

        } else if (spacesLeft == 5) {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else if (wentCorner) {
                String[] corners = corners(choices);
                String chosenCorner = corners[0];
                table[getRow(chosenCorner)][getCol(chosenCorner)] = player.getPiece() + " ";
            } else if (wentCenter) {
                if (canWin(p1)) {
                    table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                            player.getPiece() + " ";
                } else {
                    table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                            player.getPiece() + " ";
                }
            } else if (wentAdjacent) {
                if (canWin(p1)) {
                    table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                            player.getPiece() + " ";
                } else {
                    String cornerChosen = aloneCorner(choices);
                    int row = getRow(cornerChosen);
                    int col = getCol(cornerChosen);
                    table[row][col] = player.getPiece() + " ";
                }
            }

        } else if (spacesLeft == 4) {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else if (canWin(p1)) {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            } else if (wentCenter) {
                System.out.println(semiEmptyRowNum(player));
                if (semiEmptyRowNum(player) != -1) {
                    int goodRow = semiEmptyRowNum(player);
                    String[] goodChoices = new String[2];
                    int count = 0;
                    for (String choice : choices) {
                        if (choice != null) {
                            if (getRow(choice) == goodRow) {
                                goodChoices[count] = choice;
                                count++;
                            }
                        }
                    }
                    String spaceChosen = goodChoices[((int) (100 * Math.random())) % 2];
                    table[getRow(spaceChosen)][getCol(spaceChosen)] = player.getPiece() + " ";
                } else if (semiEmptyColNum(player) != -1) {
                    int goodCol = semiEmptyColNum(player);
                    String[] goodChoices = new String[2];
                    int count = 0;
                    for (String choice : choices) {
                        if (choice != null) {
                            if (getCol(choice) == goodCol) {
                                goodChoices[count] = choice;
                                count++;
                            }
                        }
                    }
                    String spaceChosen = goodChoices[((int) (100 * Math.random())) % 2];
                    table[getRow(spaceChosen)][getCol(spaceChosen)] = player.getPiece() + " ";
                }
            } else if (wentAdjacent) {
                String cornerChosen = corners(choices)[((int) (100 * Math.random())) % 2];
                table[getRow(cornerChosen)][getCol(cornerChosen)] = player.getPiece() + " ";            }

        } else if (spacesLeft == 3) {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            }

        } else if (spacesLeft == 2) {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else if (canWin(p1)) {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            } else {
                easyCompMove(choices, player, size);
            }


        } else if (spacesLeft == 1) {
            if (canWin(player)) {
                table[getRow(winningSpace(player))][getCol(winningSpace(player))] =
                        player.getPiece() + " ";
            } else {
                table[getRow(winningSpace(p1))][getCol(winningSpace(p1))] =
                        player.getPiece() + " ";
            }
        }
    }


    // Returns a string to indicate that there is a vertical sequence of spaces that has 2 pieces
    // out of 3 in a row, and the String returned is the last empty space in the sequence. If there
    // isn't a vertical sequence in which there is 2 in a row.
    public String twoRowVertical(Player player) {
        String piece = player.getPiece() + " ";
        int inARow = 0;
        String thirdSpace = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[j][i].equals(piece)) {
                    inARow++;
                }
                if (table[j][i].equals("  ")) {
                    thirdSpace = Integer.toString(j) + Integer.toString(i);
                }
            }
            if (inARow == 2) {
                return thirdSpace;
            }
            inARow = 0;
            thirdSpace = null;
        }
        return null;
    }


    // Returns a string to indicate that there is a horizontal sequence of spaces that has 2 pieces
    // out of 3 in a row, and the String returned is the last empty space in the sequence. If there
    // isn't a horizontal sequence in which there is 2 in a row.
    public String twoRowHorizontal(Player player) {
        int inARow = 0;
        String piece = player.getPiece() + " ";
        String thirdSpace = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals(piece)) {
                    inARow++;
                }
                if (table[i][j].equals("  ")) {
                    thirdSpace = Integer.toString(i) + Integer.toString(j);
                }
            }
            if (inARow == 2) {
                return thirdSpace;
            }
            inARow = 0;
            thirdSpace = null;
        }
        return null;
    }

    // Returns a string to indicate that there is a diagonal sequence of spaces that has 2 pieces
    // out of 3 in a row, and the String returned is the last empty space in the sequence. If there
    // isn't a diagonal sequence in which there is 2 in a row.
    public String twoRowDiagonal(Player player) {
        int inARow = 0;
        String piece = player.getPiece() + " ";
        String thirdSpace = null;
        if (table[0][0].equals(piece)) {
            inARow++;
        } else {
            if (table[0][0].equals("  ")) {
                thirdSpace = "00";
            }
        }
        if (table[1][1].equals(piece)) {
            inARow++;
        } else {
            if (table[1][1].equals("  ")) {
                thirdSpace = "11";
            }
        }
        if (table[2][2].equals(piece)) {
            inARow++;
        } else {
            if (table[2][2].equals("  ")) {
                thirdSpace = "22";
            }
        }

        if (inARow == 2 && thirdSpace != null) {
            return thirdSpace;
        } else {
            inARow = 0;
            thirdSpace = null;
        }

        if (table[2][0].equals(piece)) {
            inARow++;
        } else {
            if (table[2][0].equals("  ")) {
                thirdSpace = "20";
            }
        }
        if (table[1][1].equals(piece)) {
            inARow++;
        } else {
            if (table[1][1].equals("  ")) {
                thirdSpace = "11";
            }
        }
        if (table[0][2].equals(piece)) {
            inARow++;
        } else {
            if (table[0][2].equals("  ")) {
                thirdSpace = "02";
            }
        }

        if (inARow == 2 && thirdSpace != null) {
            return thirdSpace;
        } else {
            return null;
        }
    }

    // Returns the space that, when the correct piece is placed there, a player wins. Returns null
    // if no such spot exists.
    public String winningSpace(Player player) {
        if (twoRowVertical(player) != null) {
            return twoRowVertical(player);
        } else if (twoRowHorizontal(player) != null) {
            return twoRowHorizontal(player);
        } else {
            return twoRowDiagonal(player);
        }
    }

    public String[] corners(String[] spaces) {
        String[] corners = new String[4];
        int index = 0;
        for (String space : spaces) {
            if (space != null) {
                if (space.equals("00") || space.equals("02") || space.equals("20") || space.equals(
                        "22")) {
                    corners[index] = space;
                    index++;
                }
            }
        }
        return corners;
    }

    public int numCorners(String[] corners) {
        int count = 0;
        for (String corner : corners) {
            if (corner != null) {
                count++;
            }
        }
        return count;
    }

    public void wentCenter(String[] choices) {
        int count = 0;
        for (String space : choices) {
            if (space != null) {
                if (space.equals("11")) {
                    count++;
                }
            }
        }
        if (count == 1) {
            wentCenter = false;
        } else {
            wentCenter = true;
        }
    }

    public void wentAdjacent(String[] choices) {
        int numAdjacent = 4;
        for (String space : choices) {
            if (space != null) {
                if (space.equals("01") || space.equals("10") || space.equals("21") || space.equals(
                        "12")) {
                    numAdjacent--;
                }
            }
        }
        if (numAdjacent != 0) {
            wentAdjacent = true;
        }
    }

    public String aloneCorner(String[] choices) {
        String[] corners = corners(choices);
        for (String corner : corners) {
            if (corner != null) {
                if (corner.equals("00")) {
                    if (table[1][0].equals("  ") && table[0][1].equals("  ")) {
                        return "00";
                    }
                } else if (corner.equals("20")) {
                    if (table[1][0].equals("  ") && table[2][1].equals("  ")) {
                        return "20";
                    }
                } else if (corner.equals("22")) {
                    if (table[2][1].equals("  ") && table[1][2].equals("  ")) {
                        return "22";
                    }
                } else if (corner.equals("02")) {
                    if (table[0][1].equals("  ") && table[1][2].equals("  ")) {
                        return "02";
                    }
                }
            }
        }
        return null;
    }


    // Returns a boolean representing whether Player "player" has a spot that will make them win the
    // game in the next move or not.
    public boolean canWin(Player player) {
        if (winningSpace(player) == null) {
            return false;
        } else {
            return true;
        }
    }


    public boolean gameOver() {
        if (spacesLeft == 0) {
            return true;
        }
        if (verticalWinCheck() != null || diagonalWinCheck() != null
                || horizontalWinCheck() != null) {
            return true;
        }
        return false;
    }

    public String verticalWinCheck() {
        int inARow = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[j][i].equals(table[0][i]) && !table[j][i].equals("  ")) {
                    inARow++;
                }
                if (inARow == 3) {
                    return table[0][i];
                }
            }
            inARow = 0;
        }
        return null;
    }

    public String horizontalWinCheck() {
        int inARow = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals(table[i][0]) && !table[i][j].equals("  ")) {
                    inARow++;
                }
                if (inARow == 3) {
                    return table[i][0];
                }
            }
            inARow = 0;
        }
        return null;
    }

    public String diagonalWinCheck() {
        if (!table[0][0].equals("  ") && table[0][0].equals(table[1][1]) && table[0][0].equals(
                table[2][2])) {
            return table[0][0];
        }
        if (!table[2][0].equals("  ") && table[2][0].equals(table[1][1]) && table[2][0].equals(
                table[0][2])) {
            return table[2][0];
        }
        return null;
    }

    public void getWinner() {
        String winnerPiece = null;
        if (verticalWinCheck() != null) {
            winnerPiece = verticalWinCheck();
        } else if (horizontalWinCheck() != null) {
            winnerPiece = horizontalWinCheck();

        } else {
            winnerPiece = diagonalWinCheck();
        }
        if (winnerPiece == null) {
            System.out.println("The game ends in a tie! Close game!");
        } else if (winnerPiece.equals(p1.getPiece() + " ")) {
            System.out.println("The winner is " + p1.name() + "!");
        } else {
            System.out.println("The winner is " + p2.name() + "!");
        }
    }

    public static boolean validNumPlayers(String num) {
        try {
            int check = Integer.parseInt(num);
            if (check == 1 || check == 2) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validDifficulty(String difficulty) {
        try {
            int diff = Integer.parseInt(difficulty);
            if (diff <= 3 && diff >= 1) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validFirst(String first) {
        try {
            int num = Integer.parseInt(first);
            if (num < 1 || num > 3) {
                System.out.println("Not a valid choice. Try again: ");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Not a valid number. Try again: ");
            return false;
        }
    }

    public int getRow(String space) {
        return Integer.parseInt(space.trim()) / 10;
    }

    public int getCol(String space) {
        return Integer.parseInt(space.trim()) % 10;
    }

    public String getFirstPiece() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!table[i][j].equals("  ")) {
                    return Integer.toString(i) + Integer.toString(j);
                }
            }
        }
        return null;
    }

    public boolean linedUp() {
        if ((getRow(firstPiece) == getRow(lastOppPiece)) || (getCol(firstPiece) == getCol(
                lastOppPiece))) {
            return true;
        }
        return false;
    }

    public int emptyColNum() {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[j][i].equals("  ")) {
                    num++;
                }
            }
            if (num == 3) {
                return i;
            } else {
                num = 0;
            }
        }
        return -1;
    }


    public int emptyRowNum() {
        int num = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals("  ")) {
                    num++;
                }
            }
            if (num == 3) {
                return i;
            } else {
                num = 0;
            }
        }
        return -1;
    }

    public int semiEmptyRowNum(Player player) {
        int numEmptySpaces = 0;
        int numPlayerPieces = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[i][j].equals("  ")) {
                    numEmptySpaces++;
                } else if (table[i][j].equals(player.getPiece() + " ")) {
                    numPlayerPieces++;
                }
            }
            if (numEmptySpaces == 2 && numPlayerPieces == 1) {
                return i;
            } else {
                numEmptySpaces = 0;
                numPlayerPieces = 0;
            }
        }
        return -1;
    }


    public int semiEmptyColNum(Player player) {
        int numEmptySpaces = 0;
        int numPlayerPieces = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (table[j][i].equals("  ")) {
                    numEmptySpaces++;
                } else if (table[j][i].equals(player.getPiece() + " ")) {
                    numPlayerPieces++;
                }
            }
            if (numEmptySpaces == 2 && numPlayerPieces == 1) {
                return i;
            } else {
                numEmptySpaces = 0;
                numPlayerPieces = 0;
            }
        }
        return -1;
    }


    /**
     * Prints the current state of the table
     **/
    public void printTable() {
        String toPrint;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                toPrint = " | " + table[i][j];
                System.out.print(toPrint);
            }
            System.out.print(" |");
            System.out.println();
        }
    }
}
