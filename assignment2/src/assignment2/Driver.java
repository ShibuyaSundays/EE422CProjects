/* EE422C Assignment #2
 * Isaac Lee
 * itl96
 */

package assignment2;

public class Driver {
    public static void main(String[] args){ //main method w cmd line arg of testing mode
        boolean testingMode;
        if(args[0].equals("1"))  //testing mode on at 1, off on everything else
            testingMode = true;
        else
            testingMode = false;
        Game newRound = new Game(testingMode);  //create a new game (that repeats)
        newRound.runGame();  //run it
    }
}
