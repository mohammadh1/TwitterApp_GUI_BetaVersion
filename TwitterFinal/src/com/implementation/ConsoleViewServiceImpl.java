package com.implementation;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.interfaces.ConsoleViewService;
import com.tools.LocalDateDeserializer;
import com.tools.LocalDateSerializer;
import com.tools.LocalDateTimeDeserializer;
import com.tools.LocalDateTimeSerializer;
import com.twitter.server.Tweet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private Gson gsonBuilder;
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
        this.gson = new Gson();
        this.gsonBuilder =new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class,new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    public Object terminalStart(File file) {
        try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
            JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
            String method = jsonObj.get("method").getAsString();
            switch (method) {
                case "login" -> login(file);
                case "signup" -> signup(file);
                case "timeline" -> timeline(file);
                case "showTweetsOf" -> showTweetOfPerson(file);
                case "like" -> like(file);
                case "follow" -> follow(file);
                case "unfollow" -> unfollow(file);
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
    public Boolean signup(File file){
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
    public String timeline(File file){
        ArrayList<Tweet> timelineTweets = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (file.length() != 0) {
            try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))) {
                JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
                boolean errorState = jsonObj.get("hasError").getAsBoolean();
                if (!errorState) {
                    JsonArray result = jsonObj.get("result").getAsJsonArray();
                    Iterator<JsonElement> iterator = result.iterator();
                    int countInternal = result.size();
                    for (int i = 0; i < countInternal; i++) {
                        JsonElement element = iterator.next();
                        String sender = element.getAsJsonObject().get("sender").getAsJsonObject().get("username").getAsString();
                        String text = element.getAsJsonObject().get("text").getAsString();
                        stringBuilder.append(sender + " : ");
                        stringBuilder.append(System.lineSeparator());
                        stringBuilder.append(text);
                        stringBuilder.append(System.lineSeparator());
                        stringBuilder.append(System.lineSeparator());
                    }
                }
                else {
                    stringBuilder.append("");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String string = String.valueOf(stringBuilder);
        return string;
    }
    public ArrayList<Tweet> showTweetOfPerson(File file){
        return null;
    }
    public Boolean like(File file){
        return true;
    }
    public Boolean follow(File file){
        return true;
    }
    public Boolean unfollow(File file){
        return true;
    }

}
