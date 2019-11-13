/* EE422C Assignment #3 submission by
 * Isaac Lee
 * itl96
 */

//package assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class GraphPoet {
    /**
     *
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    private ArrayList<Vertex<String>> completeGraph = new ArrayList<Vertex<String>>();

    public GraphPoet(File corpus) throws IOException {
        Scanner scan = new Scanner(corpus);
        String poemLine;
        String[] poemWords;
        int a = 1;

        while(scan.hasNextLine()) {  //put all the words in the hashes for the corpus
            poemLine = scan.nextLine();
            System.out.println(poemLine + " number " + a);
            a++;
            poemLine = poemLine.toLowerCase();
            poemWords = poemLine.split(" ");
            for (int i = 0; i < poemWords.length - 1; i++) {  //look at pairs of words at a time to put the edges
                if (completeGraph.isEmpty()) {
                    completeGraph.add(new Vertex<>(poemWords[i]));
                }
                for (int j = 0; j < completeGraph.size(); j++) {
                    if ((completeGraph.get(j).getName()).equals(poemWords[i])) {//if not in linked list
                        completeGraph.get(j).addBranch(poemWords[i + 1]);  //update/add next word
                        //System.out.println(poemWords[i] + "  " + completeGraph.get(j).getMap()); //debugging
                        break;
                    } else {//is in list already
                        completeGraph.add(new Vertex<>(poemWords[i]));  //create new vertex
                        completeGraph.get(0).addBranch(poemWords[i + 1]);  //add the next word
                    }

                }
            }
        }
    }

    /**
     * Generate a poem.
     *
     * @param input File from which to create the poem
     * @return poem (as described above)
     */
    public String poem(File input) throws IOException {  //while file != EOF
        Scanner scan = new Scanner(input);
        String wordLine;
        String[] words;
        String poem = "";  //poem is empty at start
        String bridgeWord;
        while(scan.hasNextLine()){
            wordLine = scan.nextLine();
            //if(wordLine != null && !wordLine.isEmpty()){
            wordLine = wordLine.toLowerCase();
            words = wordLine.split(" ");  //break up the now lower case string into an array of words
            for(int i = 0; i < (words.length - 1); i++){
                poem = poem + words[i] + " ";  //add the word itself first, then bridge words if applicable.
                int most = 0;
                bridgeWord = "";  //bridge word is empty for now
                for(int j = 0; j < completeGraph.size(); j++){
                    if(completeGraph.get(j).getName().equals(words[i])){ //if the word is found
                        //check if any of the next edges lead to words[i+1]
                        Map<String, Integer> test = completeGraph.get(j).getMap();
                        for(HashMap.Entry<String, Integer> entry : test.entrySet()){  //find the next words that lead to words[i+1]
                            for(int k = 0; k < completeGraph.size(); k++){
                                if(completeGraph.get(k).getName().equals(entry.getKey())){ //if the graph contains the next word
                                    Map<String, Integer> test2 = completeGraph.get(k).getMap();  //get the map of the next word's next words
                                    for(HashMap.Entry<String, Integer> entry2 : test2.entrySet()){
                                        if((entry2.getKey().equals(words[i+1])) && (entry.getValue() > most)){  //if it matches words[i+1] and has most edges
                                            most = entry.getValue();  //this is to filter out words with higher edge values
                                            bridgeWord = entry.getKey();  //set the bridge word, which is the first map's key
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(!(bridgeWord.equals(""))){  //if bridge word is not empty
                    poem = poem + bridgeWord + " ";
                }

            }
            poem += words[words.length - 1];  //end with the final word
        }
 //       }
        return poem;
    }

}
