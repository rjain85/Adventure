package com.Adventures;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Adventures {
    private static final int STATUS_OK = 200;

    public static Layout gameLayout;

    public String beginGame = (gameLayout.getRooms().get(0).getDescription() + "\n" + "Your journey begins here");

    public String exitGame = ("EXIT");

    public boolean gameRunning = false;

    public static void makeApiRequest(String url) throws UnirestException, MalformedURLException {
        final HttpResponse<String> stringHttpResponse;

        // This will throw MalformedURLException if the url is malformed.
        new URL(url);

        stringHttpResponse = Unirest.get(url).asString();
        // Check to see if the request was successful; if so, convert the payload JSON into Java objects
        if (stringHttpResponse.getStatus() == STATUS_OK) {
            String json = stringHttpResponse.getBody();
            Gson gson = new Gson();
            try {
                gameLayout = gson.fromJson(json, Layout.class);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }
    }

    public StringBuilder createCantGoMessage(String whereToGo) {
        return new StringBuilder("I can't go to " + whereToGo);
    }

    public StringBuilder initializeGame() {
        return new StringBuilder(beginGame + "\n" + gameLayout.getRooms().get(0).createOptions());
    }

    public String takeUserInput(Scanner scanner) {

    }

}
