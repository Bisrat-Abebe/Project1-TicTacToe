import java.util.Scanner;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
import java.lang.Math;


// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    // to redo the turn
    public static void main(String[] args) {
        boolean playAgain = true;

        while (playAgain) {
            TicTacToe game;
            Scanner numPlayersChosen = new Scanner(System.in);
            System.out.println(
                    "\nOne Player or Two Players?\nPress 1 for One player, 2 for Two Players: ");

            String numPlayers = numPlayersChosen.nextLine();

            while (!TicTacToe.validNumPlayers(numPlayers)) {
                Scanner numPlayersAgain = new Scanner(System.in);
                System.out.println("Invalid number of players chosen, try again: ");
                numPlayers = numPlayersAgain.nextLine();
            }
            //Initializing for 1 player
            if (Integer.parseInt(numPlayers) == 1) {
                Scanner getName = new Scanner(System.in);
                System.out.println("Enter your name: ");
                String chosenName = getName.nextLine();

                Scanner getPiece = new Scanner(System.in);
                System.out.println("Choose 'X' or 'O' as your piece: ");
                String pieceChosen = getPiece.nextLine();

                while (!pieceChosen.equals("X") && !pieceChosen.equals("O")) {
                    Scanner chooseAnotherPiece = new Scanner(System.in);
                    System.out.println("Invalid piece chosen, try again: ");
                    pieceChosen = chooseAnotherPiece.nextLine();
                }

                Scanner difficultyChosen = new Scanner(System.in);
                System.out.println("Enter the difficulty level of the bot.");
                System.out.println("| 1 - Easy | 2 - Medium | 3 - Hard | :");

                String difficulty = difficultyChosen.nextLine();

                while (!TicTacToe.validDifficulty(difficulty)) {
                    Scanner newChosenDifficulty = new Scanner(System.in);
                    System.out.println("Invalid difficulty chosen, try again: ");
                    difficulty = newChosenDifficulty.nextLine();
                }

                int diff = Integer.parseInt(difficulty);
                Human p1 = new Human(chosenName, pieceChosen);
                Computer opp = new Computer(pieceChosen, diff);

                System.out.println("\nYou'll be going up against " + opp.name() + "!");
                System.out.println(opp.name() + "'s piece is " + opp.getPiece() + ".");

                Scanner whoFirst = new Scanner(System.in);
                System.out.println("\nChoose who should go first.");
                System.out.println(
                        "| 1 - " + p1.name() + " | 2 - " + opp.name() + " | 3 - Random | :");
                String first = whoFirst.nextLine();

                while (!TicTacToe.validFirst(first)) {
                    Scanner whoFirstAgain = new Scanner(System.in);
                    first = whoFirstAgain.nextLine();
                }
                int firstChosen = Integer.parseInt(first);

                game = new TicTacToe(p1, opp);
                System.out.println("\nCurrent Game State:");
                game.printTable();

                //Loop for the game once the game is ready to be started
                if (firstChosen == 1) {
                    opp.setFirst(firstChosen);
                    while (!game.gameOver()) {
                        System.out.println();
                        System.out.println(p1.name() + "'s Turn!");
                        game.makeMove(p1);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                        System.out.println();
                        System.out.println(opp.name() + "'s Turn!");
                        game.computerMove(opp);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                    }
                } else if (firstChosen == 2) {
                    opp.setFirst(firstChosen);
                    while (!game.gameOver()) {
                        System.out.println();
                        System.out.println(opp.name() + "'s Turn!");
                        game.computerMove(opp);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                        System.out.println();
                        System.out.println(p1.name() + "'s Turn!");
                        game.makeMove(p1);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                    }
                } else {
                    int randomChoice = (((int) (100 * Math.random())) % 2) + 1;
                    opp.setFirst(randomChoice);
                    if (randomChoice == 1) {
                        while (!game.gameOver()) {
                            System.out.println();
                            System.out.println(p1.name() + "'s Turn!");
                            game.makeMove(p1);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                            System.out.println();
                            System.out.println(opp.name() + "'s Turn!");
                            game.computerMove(opp);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                        }
                    } else {
                        while (!game.gameOver()) {
                            System.out.println();
                            System.out.println(opp.name() + "'s Turn!");
                            game.computerMove(opp);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                            System.out.println();
                            System.out.println(p1.name() + "'s Turn!");
                            game.makeMove(p1);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                        }
                    }
                }
                //Game is over for 1 player
                System.out.println();
                System.out.println("Current Game State: ");
                game.printTable();
                System.out.println();
                game.getWinner();

                //Initializing for 2 player game
            } else {
                Scanner getFirstName = new Scanner(System.in);
                System.out.println("Enter the first player's name: ");
                String chosenFirstName = getFirstName.nextLine();

                Scanner getSecondName = new Scanner(System.in);
                System.out.println("Enter the second player's name: ");
                String chosenSecondName = getSecondName.nextLine();

                Scanner getFirstPiece = new Scanner(System.in);
                System.out.println(chosenFirstName + "'s choice between 'X' or 'O': ");
                String pieceFirstChosen = getFirstPiece.nextLine();

                String pieceSecondChosen;
                if (pieceFirstChosen.equals("X")) {
                    pieceSecondChosen = "O";
                    System.out.println("\n" + chosenSecondName + "'s piece will be '0'.");

                } else {
                    pieceSecondChosen = "X";
                    System.out.println("\n" + chosenSecondName + "'s piece will be 'X'.");
                }

                Player p1 = new Human(chosenFirstName, pieceFirstChosen);
                Player p2 = new Human(chosenSecondName, pieceSecondChosen);

                Scanner whoFirst = new Scanner(System.in);
                System.out.println("\nChoose who should go first.");
                System.out.println(
                        "| 1 - " + p1.name() + " | 2 - " + p2.name() + " | 3 - Random | :");
                String first = whoFirst.nextLine();

                while (!TicTacToe.validFirst(first)) {
                    Scanner whoFirstAgain = new Scanner(System.in);
                    first = whoFirstAgain.nextLine();
                }
                int firstChosen = Integer.parseInt(first);

                game = new TicTacToe(p1, p2);
                System.out.println();
                System.out.println("Current Game State:");
                game.printTable();

                //Loop for the game once the game is ready to be started
                if (firstChosen == 1) {
                    while (!game.gameOver()) {
                        System.out.println();
                        System.out.println(p1.name() + "'s Turn!");
                        game.makeMove(p1);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                        System.out.println();
                        System.out.println(p2.name() + "'s Turn!");
                        game.makeMove(p2);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                    }
                } else if (firstChosen == 2) {
                    while (!game.gameOver()) {
                        System.out.println();
                        System.out.println(p2.name() + "'s Turn!");
                        game.makeMove(p2);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                        System.out.println();
                        System.out.println(p1.name() + "'s Turn!");
                        game.makeMove(p1);
                        if (game.gameOver()) {
                            break;
                        }
                        System.out.println();
                        System.out.println("Current Game State: ");
                        game.printTable();
                    }
                } else {
                    int randomChoice = (((int) (100 * Math.random())) % 2) + 1;
                    if (randomChoice == 1) {
                        while (!game.gameOver()) {
                            System.out.println();
                            System.out.println(p1.name() + "'s Turn!");
                            game.makeMove(p1);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                            System.out.println();
                            System.out.println(p2.name() + "'s Turn!");
                            game.makeMove(p2);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                        }
                    } else {
                        while (!game.gameOver()) {
                            System.out.println();
                            System.out.println(p2.name() + "'s Turn!");
                            game.makeMove(p2);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                            System.out.println();
                            System.out.println(p1.name() + "'s Turn!");
                            game.makeMove(p1);
                            if (game.gameOver()) {
                                break;
                            }
                            System.out.println();
                            System.out.println("Current Game State: ");
                            game.printTable();
                        }
                    }
                }
                System.out.println();
                System.out.println("Current Game State: ");
                game.printTable();
                System.out.println();
                game.getWinner();
            }

            String playChosen = null;
            boolean validNum = false;
            while (!validNum) {
                Scanner play = new Scanner(System.in);
                System.out.println("\nDo you want to play again?\n| 1 - Yes | 2 - No | : ");
                playChosen = play.nextLine().trim();
                if (!playChosen.equals("1") && !playChosen.equals("2")) {
                    validNum = false;
                    System.out.println("\nInvalid choice, try again: ");
                } else {
                    validNum = true;
                }
            }
            if (playChosen.equals("1")) {
                playAgain = true;
            } else {
                playAgain = false;
            }
        }
        System.out.println("\nThanks for playing!");
    }

}
