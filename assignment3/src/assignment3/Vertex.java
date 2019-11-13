/* EE422C Assignment #3 submission by
 * Isaac Lee
 * itl96
 */

package assignment3;

import java.util.HashMap;
import java.util.Map;

public class Vertex<T> {
    private T name;  //name of vertex
    private Map<T, Integer> edges = new HashMap<>();  //a hash map, with key being the vertex name, integer being the number of edges

    Vertex(T name){
        this.name = name;  //retrieves name of the vertex in question
    }

    public T getName(){
        return this.name;
    }
    public Map<T, Integer> getMap(){  //retrieve the vertex's map
        return this.edges;
    }
    public void addBranch(T word){  //add branch means to add a followup word
        if(edges.containsKey(word)){  //if word already exists, increment the edge count
            int count = edges.remove(word);
            count++;
            edges.put(word, count);
        }
        else{
            edges.put(word, 1);  //add the word with edge 1 if previously nonexistent
        }
    }
}
