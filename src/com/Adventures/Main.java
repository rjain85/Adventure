package com.Adventures;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.util.Scanner;

import static com.Adventures.Adventures.makeApiRequest;

public class Main {
    public static void main(String [] arguments) {
        String url = "https://courses.engr.illinois.edu/cs126/adventure/siebel.json";

        // Make an HTTP request to the above URL
        try {
            makeApiRequest(url);
        } catch (UnirestException e) {
//            e.printStackTrace();
            System.out.println("Network not responding");
        } catch (MalformedURLException e) {
            System.out.println("Bad URL: " + url);
        }

        Adventures gamePlay = new Adventures();

        Scanner gameInput = new Scanner(System.in);

        while (gamePlay.gameRunning) { //Note to me: if check the user input and adjust gameRunning accordingly in every method that looks at user input.
            System.out.println(gamePlay.initializeGame());
        }

    }
}
