//package assignment3;

import java.io.File;
import java.io.IOException;

public class Main {

    private static File weirdCasing = new File("test/weirdCasing.txt");
    private static File weirdCasingInput = new File("test/weirdCasingInput.txt");

    private static File multipleBridges = new File("test/multipleBridges.txt");
    private static File multipleBridgesInput = new File("test/multipleBridgesInput.txt");
    
    private static File examplePoem = new File("test/examplePoem.txt");
    private static File examplePoemInput = new File("test/examplePoemInput.txt");

    private static File multiWeightTwo = new File("test/multipleWeights2.txt");
    private static File multiWeightTwoInput = new File("test/multipleWeights2Input.txt"); 

    public static void main(String[] args) throws IOException {
	GraphPoet graph = new GraphPoet(weirdCasing);
        String output = graph.poem(weirdCasingInput);
	System.out.println("student output: " + output);
	System.out.println( "correct output: " + "explore magnificent new world");

	graph = new GraphPoet(multipleBridges);
	output = graph.poem(multipleBridgesInput);
	System.out.println("student output: " + output);
	System.out.println("correct output: " + "To venture through many strange new exciting worlds tomorrow");

	graph = new GraphPoet(examplePoem);
	output = graph.poem(examplePoemInput);
	System.out.println("student output: " + output);
	System.out.println("correct output: " + "Seek to explore strange new life and exciting synergies");

	graph = new GraphPoet(multiWeightTwo);
	output = graph.poem(multiWeightTwoInput);
	System.out.println("student output: " + output);
	System.out.println("correct output: " + "explore magnificent new");
    }
    
}
