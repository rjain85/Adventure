package com.Adventures;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.net.MalformedURLException;
import java.net.URL;

public class Adventures {
    private static final int STATUS_OK = 200;

    private static Layout gameLayout;

    String beginGame = (gameLayout.getRooms().get(0).getDescription() + "\n" + "Your journey begins here");

    private String exitGame = ("EXIT");

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

    private StringBuilder createCantGoMessage(String whereToGo) {
        StringBuilder cantGoMessage =  new StringBuilder("I can't go to " + whereToGo);
        return cantGoMessage;
    }

}
