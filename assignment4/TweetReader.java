/*
Name: Isaac Lee
EID: itl96
Date: 10/31/2019
 */
package assignment4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TweetReader contains method used to return tweets from method
 * Do not change the method header
 */
public class TweetReader {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    /**
     * Find tweets written by a particular user.
     * @param url
     * url used to query a GET Request from the server
     * @return return list of tweets from the server
     */
    public static List<Tweets> readTweetsFromWeb(String url) throws Exception
    {
        TweetReader obj = new TweetReader();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.UPPER_CAMEL_CASE);
        List<Tweets> tweetList = new ArrayList<>();
        try {
            String result = obj.sendGet(url);
            Tweets[] tweetArray = mapper.readValue(result, Tweets[].class);
            //String prettyStaff1 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tweetArray);
            //System.out.println(prettyStaff1);  //this is for printing the tweetArray correctly
            tweetList = new ArrayList<>(Arrays.asList(tweetArray));
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            obj.close();
        }
        int i = 0;
        while(i < tweetList.size()) {
            try {
                if (!(1 <= tweetList.get(i).getId()) && (tweetList.get(i).getId() <= Math.pow(2, 32)))  // not 1 <= id <= 2^32
                    tweetList.remove(i); //delete
                else if (!(tweetList.get(i).getName().matches("[a-zA-Z0-9_]+")))  //not alphanum + _
                    tweetList.remove(i); //delete
                else if (!(dateValid(tweetList.get(i).getDate())))  //if date is not valid format using dateValid
                    tweetList.remove(i); //delete
                else if (tweetList.get(i).getText().length() > 140)  //more than 140 char
                    tweetList.remove(i); //delete
                else
                    i++;  //then move on to next tweet
            }
            catch (NullPointerException e) {  //if ANYTHING is null, remove from list
                tweetList.remove(i);
            }
        }
        return tweetList;
    }
    private void close() throws IOException {
        httpClient.close();
    }
    private String sendGet(String link) throws Exception {
        HttpGet request = new HttpGet(link);
        String result = "";
        // no need to add headers
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            System.out.println(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            System.out.println(headers);

            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
            }

        }
        return result;
    }
    public static boolean dateValid(String date){
        /**try{
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");  //must be this date format ONLY
            df.setLenient(false);  //interpretation now strict
            df.parse(date);
            return true;
        }
        catch(ParseException e){
            return false;  //if does not match
        }**/
        try{
            Instant.parse(date);
            return true;
        }
        catch(DateTimeException e){
            return false;
        }
    }
}
