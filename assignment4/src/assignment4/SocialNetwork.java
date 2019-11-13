/*
Name: Isaac Lee
EID: itl96
Date: 10/31/2019
 */
package assignment4;

import java.util.*;

/** Social Network consists of methods that filter users matching a condition.
 * DO NOT change the method signatures and specifications of these methods, but you should implement their method
 * bodies, and you may add new public or private methods or classes if you like.
 */
public class SocialNetwork {
    /** Get K most followed Users.
     * @param tweets list of tweets with distinct ids, not modified by this method.
     * @param k integer of most popular followers to return
     * @return the set of usernames who are most mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getName()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like ethomaz@utexas.edu does NOT
     *         contain a mention of the username.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static List<String> findKMostFollower(List<Tweets> tweets, int k) {
        List<String> mostFollowers = new ArrayList<>();
        List<String> usersList = new ArrayList<>();
        int most;
        String lowerUserName;
        ArrayList<ArrayList<String>> followerList = new ArrayList<ArrayList<String>>(); //maps the list of followers to a user
        for(int i = 0; i < tweets.size(); i++){
            lowerUserName = tweets.get(i).getName().toLowerCase();
            if(!(usersList.contains(lowerUserName))) {  //if user not in the list yet
                usersList.add(lowerUserName); //add to list
            }
        }
        for(int i = 0; i < usersList.size(); i++){  //for every user, add followers
            ArrayList<String> tempList = new ArrayList<>();
            for(int j = 0; j < tweets.size(); j++){  //check in every tweet
                String followingLower = usersList.get(i);
                String textLowerSpace = tweets.get(j).getText().toLowerCase() + " ";
                String[] list = tweets.get(j).getText().toLowerCase().split(" ");
                String start = " " + list[0].toLowerCase() + " ";
                if(textLowerSpace.contains(" @"+followingLower+" ")||
                        start.contains(" @"+followingLower+" ")||
                        textLowerSpace.contains("&@"+followingLower+" ")||
                        textLowerSpace.contains("^@"+followingLower+" ")){
                    String followerName = tweets.get(j).getName().toLowerCase();
                    if(!(tempList.contains(followerName))&&(!followerName.equals(followingLower)))
                        tempList.add(followerName);
                }
            }
            followerList.add(tempList);
        }
        for(int i = 0; i < k; i++){  //check k most followers
            most = 0;
            int mostIndex = 0;
            for(int j = 0; j < usersList.size(); j++){  //look at all the users
                if(followerList.get(j).size() > most){
                    mostIndex = j;
                    most = followerList.get(j).size();
                }
            }
            mostFollowers.add(usersList.get(mostIndex));
            usersList.remove(mostIndex); //remove that user for no future consideration
            followerList.remove(mostIndex);  //same for the followers
        }
        return mostFollowers;
    }

    /** Find all cliques in the social network.
     * @param tweets
     * list of tweets with distinct ids, not modified by this method.
     * @return list of set of all cliques in the graph */
    List<Set<String>> findCliques(List<Tweets> tweets) {
        List<Set<String>> result = new ArrayList<Set<String>>();
        List<String> users = new ArrayList<>();
        ArrayList<ArrayList<String>> completeMentions = new ArrayList<ArrayList<String>>();
        List<String> cliqueGroup = new ArrayList<>();
        int flag = 0;

        for(int i = 0; i < tweets.size(); i++){
            String lowerUserName = tweets.get(i).getName().toLowerCase();
            if(!(users.contains(lowerUserName))) //if user not in the list yet
                users.add(lowerUserName); //add to list
        }
        Collections.sort(users);
        for(int i = 0; i < users.size(); i++) {  //we dont evaluate last user for cliques
            ArrayList<String> mentions = new ArrayList<>();
            for (int j = 0; j < tweets.size(); j++) {  //look through all the tweets and see which users current mentions
                if (tweets.get(j).getName().toLowerCase().equals(users.get(i))) {  //if tweet written by current
                    for (int k = 0; k < users.size(); k++) {  //start one after i to save time
                        String textLowerSpace = tweets.get(j).getText().toLowerCase() + " ";
                        String[] list = tweets.get(j).getText().toLowerCase().split(" ");
                        String start = " " + list[0] + " ";
                        //System.out.println(textLowerSpace);
                        //System.out.println(users.get(i));
                        if (textLowerSpace.contains(" @" + users.get(k) + " ") ||
                                start.contains(" @" + users.get(k) + " ") ||
                                textLowerSpace.contains("&@" + users.get(k) + " ") ||
                                textLowerSpace.contains("^@" + users.get(k) + " ")) {
                            if (!(mentions.contains(users.get(k))))
                                mentions.add(users.get(k));  //add that user to the current's mentions
                        }
                    }
                }
            }
            completeMentions.add(mentions);  //create whole mentions list
            //System.out.println(mentions);

        }
        for(int i = 0; i < users.size(); i++) {
            for (int j = 0; j < completeMentions.get(i).size(); j++) {  //check all mentioned to see if mutual follow back
                cliqueGroup.clear();  //empty out set collection
                cliqueGroup.add(users.get(i));  //add the current user first
                //if (completeMentions.get(i).get(j).compareTo(users.get(i)) >= 0) {
                    for (int b = j; b < completeMentions.get(i).size(); b++) {
                        //if (completeMentions.get(i).get(b).compareTo(users.get(i)) >= 0) {  //disregard ones that
                            // came before it
                        //System.out.println(cliqueGroup);
                        outerloop:
                        for (int k = 0; k < tweets.size(); k++) {
                            if (tweets.get(k).getName().toLowerCase().equals(completeMentions.get(i).get(b))) { //check
                                // mentioned's tweets
                                String textLowerSpace = tweets.get(k).getText().toLowerCase() + " ";
                                String[] list = tweets.get(k).getText().toLowerCase().split(" ");
                                String start = " " + list[0] + " ";
                                if (((textLowerSpace.contains(" @" + users.get(i) + " ") ||
                                        start.contains(" @" + users.get(i) + " ") ||
                                        textLowerSpace.contains("&@" + users.get(i) + " ") ||
                                        textLowerSpace.contains("^@" + users.get(i) + " ")))){  //if the
                                    // mention mentions back
                                    //check clique list for cliques with users.get(i), run the clique size for loop
                                    // to check each clique element, and if unsuccessful make a new clique with just
                                    // this element and users.get(i)
                                    int tempFlag;
                                    int index;
                                    int setFlag = 0;
                                    for (int a = 0; a < result.size(); a++){
                                        if(result.get(a).contains(users.get(i))){
                                            tempFlag = 1;
                                            for(Object o : result.get(a)) {
                                                index = users.indexOf(o.toString());
                                                int getIndex = users.indexOf(completeMentions.get(i).get(b));
                                                if (!(completeMentions.get(getIndex).contains(o.toString()) && (completeMentions.get(index).contains(completeMentions.get(i).get(b))))) {
                                                    tempFlag = 0;
                                                    break;
                                                }
                                            }//if no issues, then add the element to the clique, and exit
                                            if(tempFlag == 1){  //if it passed all the clique elements
                                                Set<String> cliqueGroupSet = new HashSet<>();
                                                for (Object o : result.get(a)) {
                                                    cliqueGroupSet.add(o.toString());
                                                }
                                                cliqueGroupSet.add(completeMentions.get(i).get(b));  //make a new
                                                // group set and add the element in question
                                                if (!result.contains(cliqueGroupSet))
                                                    result.add(cliqueGroupSet);
                                                //System.out.println(cliqueGroupSet);
                                                setFlag = 1;
                                            }
                                        }
                                    }//after looking thru all, if not added to an existing clique
                                    if(setFlag == 0){
                                        Set<String> cliqueGroupSet = new HashSet<>();
                                        cliqueGroupSet.add(users.get(i));
                                        cliqueGroupSet.add(completeMentions.get(i).get(b));
                                        if(!result.contains(cliqueGroupSet)&&cliqueGroupSet.size() > 1)
                                            result.add(cliqueGroupSet);
                                        //System.out.println(cliqueGroupSet);
                                    }
                                }
                            }
                        }
                            /**for (int a = 0; a < cliqueGroup.size(); a++) {
                                int index = users.indexOf(cliqueGroup.get(a));
                                flag = 1;
                                outerloop:
                                for (int k = 0; k < tweets.size(); k++) {
                                    if (tweets.get(k).getName().toLowerCase().equals(completeMentions.get(i).get(b))) { //check
                                        // mentioned's tweets
                                        String textLowerSpace = tweets.get(k).getText().toLowerCase() + " ";
                                        String[] list = tweets.get(k).getText().toLowerCase().split(" ");
                                        String start = " " + list[0] + " ";
                                        if (((textLowerSpace.contains(" @" + cliqueGroup.get(a) + " ") ||
                                                start.contains(" @" + cliqueGroup.get(a) + " ") ||
                                                textLowerSpace.contains("&@" + cliqueGroup.get(a) + " ") ||
                                                textLowerSpace.contains("^@" + cliqueGroup.get(a) + " "))) && (completeMentions.get(index).contains(completeMentions.get(i).get(b)))){//a
                                            flag = 0;  //mention MUST follow every other one in the list
                                            System.out.println(completeMentions.get(index) + " and follows " + completeMentions.get(i).get(b));
                                            break outerloop;
                                        }
                                    }
                                }
                                if(flag == 1)
                                    break;
                            }//after checking with all in current clique group
                            if ((flag == 0) && (!cliqueGroup.contains(completeMentions.get(i).get(b)))) {
                                cliqueGroup.add(completeMentions.get(i).get(b));
                                Set<String> cliqueGroupSet = new HashSet<>();
                                for (int m = 0; m < cliqueGroup.size(); m++) {
                                    cliqueGroupSet.add(cliqueGroup.get(m));
                                }
                                if(!result.contains(cliqueGroupSet))
                                    result.add(cliqueGroupSet);
                            }
                        //}
                        if (cliqueGroup.size() == 1) {
                            break;  //no chain, so end to evaluate next mention start
                        }**/
                    }
                //}
            }
        }
        for(Set<String> component : result){//get rid of subsets
            for(Set<String> compare : result){
                if(!component.equals(compare) && component.containsAll(compare)){
                    List<Set<String>> temp = new ArrayList<Set<String>>();
                    for(Set<String> item : result){
                        if(!item.equals(compare)){
                            temp.add(item);
                        }
                    }
                    result = temp;
                }
            }
        }
        return result;
    }
}


