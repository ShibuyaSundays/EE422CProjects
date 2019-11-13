package assignment2;/* EE422C Assignment #2
 * Isaac Lee
 * itl96
 */

//package assignment2;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private boolean mode;  //choice of either testing or non testing mode
    private String choice; //choice of Yes or No
    Game(boolean testingMode){
        mode =  testingMode;
        String welcomeMessage = "Welcome to Mastermind. Here are the rules.\n\n" +
                "This is a text version of the classic board game Mastermind.\n\n" +
                "The computer will think of a secret code. The code consists of 4\n" +
                "colored pegs. The pegs MUST be one of six colors: blue, green,\n" +
                "orange, purple, red, or yellow. A color may appear more than once in\n" +
                "the code. You try to guess what colored pegs are in the code and\n" +
                "what order they are in. After you make a valid guess the result\n" +
                "(feedback) will be displayed.\n\n" +
                "The result consists of a black peg for each peg you have guessed\n" +
                "exactly correct (color and position) in your guess. For each peg in\n" +
                "the guess that is the correct color, but is out of position, you get\n" +
                "a white peg. For each peg, which is fully incorrect, you get no\n" +
                "feedback.\n\n" +
                "Only the first letter of the color is displayed. B for Blue, R for\n" +
                "Red, and so forth. When entering guesses you only need to enter the\n" +
                "first character of each color as a capital letter.\n\n" +
                "You have 12 guesses to figure out the secret code or you lose the\n" +
                "game. Are you ready to play? (Y/N):\n";
        System.out.print(welcomeMessage); //welcome message to start the game with
    }
    public void runGame(){
        Scanner scan = new Scanner(System.in);
        choice = scan.next();
        while(choice.equals("Y")){ //only execute run program if Y
            String secretCode = SecretCodeGenerator.getInstance().getNewSecretCode();  //returns the generated code
            String userGuess;
            String pegStatus;
            String[] checkArray;
            String[] checkSecretCode = secretCode.split(""); //split for later character comparison
            List<String> userHistory = new ArrayList<String>(); //lists for iteration later under HISTORY command
            List<String> pegHistory = new ArrayList<String>();
            if(mode)
                System.out.println("Generating secret code ...(for this example the secret code is " + secretCode + "\n");
            else
                System.out.println("Generating secret code ... \n");  //different messages depending on testing mode
            scan.nextLine();  //get rid of lingering newline character
            for(int i = GameConfiguration.guessNumber; i > 0; i--){  //for loop extends for however many guesses as defined
                System.out.println("You have " + i + " guesses left.");
                Boolean validAnswer;
                do{
                    validAnswer = true;
                    System.out.print("What is your next guess?\n" +
                            "Type in the characters for your guess and press enter.\n" +
                            "Enter guess: ");
                    userGuess = scan.nextLine();
                    checkArray = userGuess.split("");

                    if(checkArray.length == 0)  //if nothing is inputted
                        validAnswer = false;
                    for(int j = 0; j < checkArray.length; j++){  //if input has non-valid characters
                        if((!Arrays.asList(GameConfiguration.colors).contains(checkArray[j]))){
                            validAnswer = false;
                            break;
                        }
                    }
                    if(checkArray.length != GameConfiguration.pegNumber)  //if input too long/short
                        validAnswer = false;
                    if(userGuess.equals("HISTORY")){  //opens up history
                        validAnswer = true;
                        for(int k = 0; k < userHistory.size(); k++){
                            System.out.print(userHistory.get(k) + " " + pegHistory.get(k) + "\n");
                        }
                    }
                    if(!validAnswer)
                        System.out.print(userGuess + " -> INVALID GUESS\n\n");
                    if(userGuess.equals("HISTORY"))
                        validAnswer = false;
                }while(!validAnswer);  //keeps prompting until valid answer

                int blackPeg = 0;
                int whitePeg = 0;  //start counters at zero
                checkArray = userGuess.split("");;
                checkSecretCode = secretCode.split("");
                for(int j = 0; j < GameConfiguration.pegNumber; j++) {
                    if (checkArray[j].equals(checkSecretCode[j])) {  //if character and postion match
                        blackPeg += 1;
                        checkArray[j] = "-";  //set affected spaces to unavailable to prevent duplicates
                        checkSecretCode[j] = "-";
                    }
                }
                for(int j = 0; j < GameConfiguration.pegNumber; j++) {
                    if (!(checkArray[j].equals("-"))) {  //if the spot isn't already crossed out
                        for (int k = 0; k < GameConfiguration.pegNumber; k++) {
                            if ((checkArray[j].equals(checkSecretCode[k]))&&(!(checkSecretCode[k].equals("-")))) {  //if color same but not position
                                whitePeg += 1;
                                checkArray[j] = "-";
                                checkSecretCode[k] = "-";
                                break;
                            }
                        }
                    }
                }
                pegStatus = blackPeg + "B_" + whitePeg + "W";
                userHistory.add(userGuess);
                pegHistory.add(pegStatus);

                if(blackPeg == 4) {  //printing out messages for peg feedback, and breaks if wins
                    System.out.println(userGuess + " -> Result: 4 black pegs - You win !!\n");
                    break;
                }
                if(blackPeg == 0 && whitePeg == 0)
                    System.out.println(userGuess + " -> Result: No pegs");
                if(blackPeg > 0 && whitePeg == 0)
                    System.out.println(userGuess + " -> Result: " + blackPeg + " black pegs");
                if(blackPeg == 0 && whitePeg > 0)
                    System.out.println(userGuess + " -> Result: " + whitePeg + " white pegs");
                if(blackPeg > 0 && whitePeg > 0)
                    System.out.println(userGuess + " -> Result: " + blackPeg + " black pegs and " + whitePeg + " white pegs");
                if(i == 1){
                    System.out.println("Sorry, you are out of guesses. You lose, boo-hoo.");  //lose message if out of turns
                }
                System.out.println();
            }

            System.out.print("Are you ready for another game (Y/N): ");  //reprompt for another game
            choice = scan.next();
        }
    }
}
