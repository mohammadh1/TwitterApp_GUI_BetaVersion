package com.implementation;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.interfaces.CommandParserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * commandParser class is responsible to create a json file as a request
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 */
public class CommandParserServiceImpl implements CommandParserService {
    private static FileWriter file;
    private String name;   // name of file
    private static int fileNumber = 0;   // the number of files ( we can create more than one request file)

    public String getName() {
        return name;
    }

    /**
     * commandParserService method will start a long switch case operation to get information from user
     *
     * @return the name of json file as a request
     */
    public String commandParser(JSONObject objMethod) {
        // store taken inputs to a json file :
        try {
            // constructs a FileWriter given a file name
            name = "./files/Request/" + objMethod.get("method").toString() + "-Request-" + fileNumber++ + ".json";
            //name = "./files/Request/Request.json";
            file = new FileWriter(name);
            file.write(objMethod.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + objMethod);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return name;
    }

    /**
     * signup json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param objMethod method of json request
     * @param username username
     * @param value array that contains value of json request
     * @param password password
     * @param firstName firstName
     * @param lastName lastName
     * @param birth birthdate
     * @param bio bio
     * @return name of request file
     */
    public String signupLines(JSONObject objMethod, String username, JSONArray value, String password,
                             String firstName,
                             String lastName,
                             String birth,
                             String bio) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "signup");
        jsonObjectTemp.put("firstName", firstName);
        jsonObjectTemp.put("lastName", lastName);
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("password", password);
        jsonObjectTemp.put("bio", bio);
        LocalDate birthDate = LocalDate.parse(birth);
        jsonObjectTemp.put("birthDate", birthDate);
        LocalDate registrationDate = LocalDate.now();
        jsonObjectTemp.put("registrationDate", registrationDate);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * login json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param password password
     * @return name of request file
     */
    public String loginLines(String username, String password) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "login");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("password", password);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * send tweet json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param text text of tweet
     * @return name of request file
     */
    public String sendTweetLines(String username, String text) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "sendTweet");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /** show tweetOf json creator
     *
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @return name of request file
     */
    public String showTweetsOfLines(String username) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "showTweetsOf");
        jsonObjectTemp.put("username", username);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * timeline json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @return name of request file
     */
    public String timelineLines(String username) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "timeline");
        jsonObjectTemp.put("username", username);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * delete tweet json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param text text of tweet
     * @return name of request file
     */
    public String deleteTweetLines(String username, String text) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "deleteTweet");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * reply json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param text text of tweet
     * @param reply text of reply
     * @return name of request file
     */
    public String replyLines(String username, String text, String reply) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "reply");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("text", text);
        jsonObjectTemp.put("reply", reply);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * follow json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param usernameFollowed user that will be followed
     * @return name of request file
     */
    public String followLines(String username, String usernameFollowed) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "follow");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("usernameFollowed", usernameFollowed);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * unfollow json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username
     * @param usernameUnfollowed user that will be unfollowed
     * @return name of request file
     */
    public String unfollowLines(String username, String usernameUnfollowed) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "unfollow");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("usernameFollowed", usernameUnfollowed);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }

    /**
     * like json creator
     *
     * objMethod = method of json request
     * value = array that contains value of json request
     * @param username username
     * @param text text of tweet
     * @return name of request file
     */
    public String likeLines(String username, String text) {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "like");
        jsonObjectTemp.put("username", username);
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
        return commandParser(objMethod);
    }
}
