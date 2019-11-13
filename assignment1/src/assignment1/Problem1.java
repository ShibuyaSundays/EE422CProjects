package assignment1;
/*
Name: Isaac Lee
Eid: itl96
Lab Section: 16165
By signing your name and eid, you affirm that this is your own work. */
import java.util.Scanner;

public class Problem1 {
    public static void main(String args[]) {
            Scanner scan = new Scanner(System.in);
            int n = scan.nextInt();
            scan.nextLine();
            String s = scan.nextLine();
            scan.close();
            int result = problem1(n, s);
    	    System.out.println(result);
    }
    public static int problem1(int m, String s){
        int n = m;
        if(n > s.length()){
            n = s.length();
        }
        int most = 0;
        for(int i = 0; i <= (s.length() - n); i++){
            int sum = 1;
            for(int j = 0; j < n; j++){
                sum *= Character.getNumericValue(s.charAt(i + j));
                if(sum > most){
                    most = sum;
                }
            }
        }
        return most;
    }
}

