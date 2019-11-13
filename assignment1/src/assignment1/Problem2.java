package assignment1;
/*
Name: Isaac Lee
Eid: itl96
Lab Section: 16165
By signing your name and eid, you affirm that this is your own work. */
import java.util.Scanner;

public class Problem2 {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        scan.close();
        String[] outputList = problem2(s);
	String newLine = System.getProperty("line.separator");
	for(int i = 0; i < outputList.length; i++){
	    System.out.println(outputList[i]);
	}
    }
    public static String[] problem2(String input){
        String[] words = input.split("\\s+");
	String output = "";
	String[] result;
        for(int i = 0; i < words.length; i++){
            int wordSum = 0;
            for(int j = 0; j < words[i].length(); j++){
                int value = Character.toUpperCase(words[i].charAt(j));
                value -= 64;
                if(value <= 26 && value >= 1) {
                    wordSum += value;
                }
            }
            if(wordSum == 100){
                output += words[i];
		output += " ";
            }
        }
	result = output.split("\\s+");
	return result;
    }
}
