package assignment1;
/*
Name: Isaac Lee
Eid: itl96
Lab Section: 16165
By signing your name and eid, you affirm that this is your own work. */
import java.util.Scanner;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Problem3 {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        String s = scan.nextLine();
        scan.close();
        String result = problem3(s);
	System.out.println(result);
    }
    public static String problem3(String input){
	String path = System.getProperty("user.dir");
        MaxentTagger tagger = new MaxentTagger(path +"/assignment1/english-left3words-distsim.tagger");
        String tagS = (tagger.tagString(input)).trim();
        return tagS;
    }
}
