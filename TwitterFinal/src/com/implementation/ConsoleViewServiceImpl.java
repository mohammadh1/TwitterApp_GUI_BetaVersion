package com.implementation;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.interfaces.ConsoleViewService;
import com.twitter.server.Tweet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * show the response of server in terminal
 * just print everything that is in json file
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class ConsoleViewServiceImpl implements ConsoleViewService {
    private Gson gson;
    private Object object;
    /*public void terminalStart(File file) {
        Gson gson = new Gson();
        if (file.length() != 0) {
            JsonArray result = null;
            try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
                JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
                boolean errorState = jsonObj.get("hasError").getAsBoolean();
                if (!errorState) {
                    result = jsonObj.get("result").getAsJsonArray();
                    Iterator<JsonElement> iterator = result.iterator();
                    int countInternal = result.size();
                    System.out.println("size of Json array : " + countInternal);
                    System.out.println("JSON OBJECT : ");
                    for (int i = 0; i < countInternal; i++) {
                        JsonElement element = iterator.next();
                        System.out.println(element.getAsJsonObject());
                    }
                } else {
                    int errorCode = jsonObj.get("errorCode").getAsInt();
                    System.err.println("program execution faced an error ( ERROR CODE : " + errorCode + " )");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println(" File is empty");
        }
    }*/

    public ConsoleViewServiceImpl() {
        gson = new Gson();
    }

    public Object terminalStart(File file) {
        try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
            JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
            String method = jsonObj.get("method").getAsString();
            switch (method) {
                case "login" -> login(file);
                case "signup" -> signup();
                case "timeline" -> timeline();
                case "showTweetsOf" -> showTweetOfPerson();
                case "like" -> like();
                case "follow" -> follow();
                case "unfollow" -> unfollow();
                default -> object= null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
    public Boolean login(File file) {
        Boolean flag = true;
        if (file.length() != 0) {
            try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
                JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
                boolean errorState = jsonObj.get("hasError").getAsBoolean();
                if (!errorState) {
                    object = flag;
                }
                else {
                    flag = false;
                    object = flag;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }
    public Boolean signup(){
        return true;
    }
    public ArrayList<Tweet> timeline(){
        return null;
    }
    public ArrayList<Tweet> showTweetOfPerson(){
        return null;
    }
    public Boolean like(){
        return true;
    }
    public Boolean follow(){
        return true;
    }
    public Boolean unfollow(){
        return true;
    }

}
