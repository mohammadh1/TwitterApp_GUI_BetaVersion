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
    static Scanner scanner = new Scanner(System.in);
    private static FileWriter file;
    private String name;   // name of file
    private static int fileNumber = 0;   // the number of files ( we can create more than one request file)

    /**
     * commandParserService method will start a long switch case operation to get information from user
     *
     * @return the name of json file as a request
     */
    public String commandParser() {
        JSONObject objMethod = new JSONObject();
        JSONArray value = new JSONArray();
        System.out.println("""
                Enter the method you want to run :\s
                 1-sign up\s
                 2-login\s
                 3-add tweet\s
                 4-show tweet of\s
                 5-timeline\s
                 6-delete tweet\s
                 7-like\s
                 8-reply\s
                9-follow\s
                10-unfollow\s
                """);
        String method = scanner.nextLine();
        String username = "";
        String password = "";
        String text = "";
        switch (method) {
            case "1" ->   // signup
                    signupLines(objMethod,
                            username,
                            value,
                            password);
            case "2" ->   // login
                    loginLines(objMethod,
                            username,
                            value,
                            password);
            case "3" -> // send a tweet
                    sendTweetLines(objMethod,
                            username,
                            value,
                            text);
            case "4" ->  // show tweets.ap of a user
                    showTweetsOfLines(objMethod,
                            username,
                            value);
            case "5" ->   // show timeline of the user
                    timelineLines(objMethod,
                            username,
                            value);
            case "6" ->  // delete a tweet
                    deleteTweetLines(objMethod,
                            username,
                            value,
                            text);
            case "7" ->  // like a tweet
                    likeLines(objMethod,
                            username,
                            value,
                            text);
            case "8" ->  // reply
                    replyLines(objMethod,
                            username,
                            value,
                            text);
            case "9" ->  // follow
                    followLines(objMethod,
                            username,
                            value);
            case "10" ->  // unfollow
                    unfollowLines(objMethod,
                            username,
                            value);
            default -> throw new IllegalStateException("Unexpected value, try again ");
        }
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
    private void signupLines(JSONObject objMethod, String username, JSONArray value, String password) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "signup");
        System.out.println("signup :");
        System.out.println("first name :");
        String firstName = scanner.nextLine();
        jsonObjectTemp.put("firstName", firstName);
        System.out.println("last name :");
        String lastName = scanner.nextLine();
        jsonObjectTemp.put("lastName", lastName);
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("password :");
        password = scanner.nextLine();
        jsonObjectTemp.put("password", password);
        System.out.println("bio :");
        String bio = scanner.nextLine();
        jsonObjectTemp.put("bio", bio);
        System.out.println("birth date :");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine());
        jsonObjectTemp.put("birthDate", birthDate);
        LocalDate registrationDate = LocalDate.now();
        jsonObjectTemp.put("registrationDate", registrationDate);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void loginLines(JSONObject objMethod, String username, JSONArray value, String password) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "login");
        System.out.println("login :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("password :");
        password = scanner.nextLine();
        jsonObjectTemp.put("password", password);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void sendTweetLines(JSONObject objMethod, String username, JSONArray value, String text) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "sendTweet");
        System.out.println("tweet :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("text of tweet :");
        text = scanner.nextLine();
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void showTweetsOfLines(JSONObject objMethod, String username, JSONArray value) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "showTweetsOf");
        System.out.println("show tweet of :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void timelineLines(JSONObject objMethod, String username, JSONArray value) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "timeline");
        System.out.println("timeline :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void deleteTweetLines(JSONObject objMethod, String username, JSONArray value, String text) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "deleteTweet");
        System.out.println("delete tweet :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("text of tweet :");
        text = scanner.nextLine();
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void replyLines(JSONObject objMethod, String username, JSONArray value, String text) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "reply");
        System.out.println("reply :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("text of tweet :");
        text = scanner.nextLine();
        jsonObjectTemp.put("text", text);
        System.out.println("text of reply :");
        String reply = scanner.nextLine();
        jsonObjectTemp.put("reply", reply);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void followLines(JSONObject objMethod, String username, JSONArray value) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "follow");
        System.out.println("follow :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("username that must be followed :");
        String usernameFollowed = scanner.nextLine();
        jsonObjectTemp.put("usernameFollowed", usernameFollowed);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void unfollowLines(JSONObject objMethod, String username, JSONArray value) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "unfollow");
        System.out.println("unfollow :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("username that must be unfollowed :");
        String usernameUnfollowed = scanner.nextLine();
        jsonObjectTemp.put("usernameFollowed", usernameUnfollowed);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
    private void likeLines(JSONObject objMethod, String username, JSONArray value, String text) {
        JSONObject jsonObjectTemp = new JSONObject();
        objMethod.put("method", "like");
        System.out.println("like tweet :");
        System.out.println("username :");
        username = scanner.nextLine();
        jsonObjectTemp.put("username", username);
        System.out.println("text of tweet :");
        text = scanner.nextLine();
        jsonObjectTemp.put("text", text);
        value.add(jsonObjectTemp);
        objMethod.put("parameterValue", value);
    }
}
