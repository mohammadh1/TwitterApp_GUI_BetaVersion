package com.implementation;


import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.interfaces.RequestParserService;
import com.tools.LocalDateDeserializer;
import com.tools.LocalDateSerializer;
import com.tools.LocalDateTimeDeserializer;
import com.tools.LocalDateTimeSerializer;
import com.twitter.server.Account;
import com.twitter.server.LoadingFiles;
import com.twitter.server.Reply;
import com.twitter.server.Tweet;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;


import static com.twitter.server.LoadingFiles.*;
import static com.twitter.server.Server.loginState;

/**
 * mystery method to parse request json in server and do some process on client request
 *
 * @author Mohammad Hoseinkhani
 * @version 0.0
 *
 */
public class RequestParserServiceImpl implements RequestParserService{
    private Boolean hasError;
    private int errorCode;
    private static int fileNumber = 0;
    private static String log = "./files/log/log.txt";
    private static final Logger logger = Logger.getLogger("log");

    /**
     * instantiate RequestParser
     */
    public RequestParserServiceImpl() {
        //currentAccount = null;
        hasError = null;
        errorCode = 0;
        try (FileReader fileReader = new FileReader("./src/com/resources/server-application.properties")) {
            Properties properties = new Properties();
            properties.load(fileReader);
            log = properties.getProperty("server.log.file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * for parsing the request and call specific methods and do what is written on request
     *
     * @param file copied json request file
     * @return response file
     */
    // for parsing and analyzing client's request
    public File requestParse (File file) {
        // loading data :
        new LoadingFiles();
        loadingAccounts();
        loadingTweets();
        loadingFollowingList();
        // start to analyze request :
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDateTime.class,new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
        // response file that server will send to client :
        File response = null;
        // boolean variable to see whether method will be executed successfully or not :
        boolean operationOfMethods = false;
        // starting of parsing
        System.out.println(file.getName());
        try (JsonReader jsonReader = gson.newJsonReader(new FileReader(file))){
            // json stuff :
            JsonObject jsonObj = gson.fromJson(jsonReader, JsonObject.class);
            String method = jsonObj.get("method").getAsString();    // getting "method" value
            JsonArray parameterValue = (JsonArray) jsonObj.get("parameterValue");
            Iterator<JsonElement> jsonElementIterator = parameterValue.iterator();
            JsonElement jsonElement = jsonElementIterator.next();
            // variable that use in more than one scope of cases so defines here to avoid DUPLICATE
            String username = "";
            String password = "";
            Account account = null;
            Tweet tweet = null;
            String text = "";
            ArrayList<Tweet> showedTweets = null;
            ArrayList<Tweet> timeLine = null;
            // json object that supposed to write on file eventually
            JsonObject responseJsonObject = new JsonObject();
            // an arrayList that saves subObjects that supposed to write in result JsonArray
            ArrayList<JsonObject> listOfResponses = new ArrayList<>();
            ///while (flag) {
            try (FileWriter logWriter = new FileWriter(log,true)) {
                switch (method) {
                    // sign up method
                    case "signup" ->  // error code = 1
                            signupLines(username,
                                    jsonElement,
                                    logWriter,
                                    password,
                                    operationOfMethods,
                                    listOfResponses,
                                    responseJsonObject);

                    // login method
                    case "login" -> // error code = 2
                            loginLines(username,
                                    jsonElement,
                                    password,
                                    logWriter,
                                    operationOfMethods,
                                    listOfResponses,
                                    responseJsonObject);

                    // tweeting method
                    case "sendTweet" -> // error code = 3 wrong self username | error code = 4 no information found
                            sendTweetLines(username,
                                    jsonElement,
                                    text,
                                    logWriter,
                                    account,
                                    listOfResponses,
                                    responseJsonObject);

                    // delete method
                    case "deleteTweet" -> // error code = 3 wrong self username | error code = 4 no information found
                            deleteTweetLines(username,
                                    jsonElement,
                                    text,
                                    logWriter,
                                    tweet,
                                    account,
                                    listOfResponses,
                                    responseJsonObject);

                    //reply method
                    case "reply" -> // error code = 3 wrong self username | error code = 4 no information found
                            replyLines(username,
                                    jsonElement,
                                    text,
                                    logWriter,
                                    tweet,
                                    account,
                                    listOfResponses,
                                    responseJsonObject);

                    // (show tweets.ap of) method
                    case "showTweetsOf" -> showTweetsOfLines(username,
                            jsonElement,
                            showedTweets,
                            logWriter,
                            gson,
                            account,
                            listOfResponses,
                            responseJsonObject);

                    // timeline method
                    case "timeline" -> timelineLines(username,
                            jsonElement,
                            timeLine,
                            logWriter,
                            gson,
                            account,
                            listOfResponses,
                            responseJsonObject);

                    // follow method
                    case "follow" -> // error code = 5 followed unsuccessful | error code = 6 unfollowed unsuccessful
                            followLines(username,
                                    jsonElement,
                                    operationOfMethods,
                                    logWriter,
                                    account,
                                    listOfResponses,
                                    responseJsonObject);

                    // unfollow method
                    case "unfollow" -> // error code = 5 followed unsuccessful | error code = 6 unfollowed unsuccessful
                            unfollowLines(username,
                                    jsonElement,
                                    operationOfMethods,
                                    logWriter,
                                    account,
                                    listOfResponses,
                                    responseJsonObject);
                    case "like" -> likeLines(username,
                            jsonElement,
                            text,
                            logWriter,
                            tweet,
                            account,
                            listOfResponses,
                            responseJsonObject);
                    default -> throw new IllegalStateException("Unexpected value: " + method);
                }
            }
            // storing in files with LoadingFiles method
            storingAccounts();
            storingFollowingList();
            storingTweets();
            // writes on json file
            String name = "./files/Response/" + method + "-Response-" + fileNumber++ + ".json";
            //String name = "./files/Response/Response.json";
            try (JsonWriter writer = new JsonWriter(new FileWriter(name))){
                gson.toJson(responseJsonObject,writer);
            }
            response = new File(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.length() != 0 && response != null)
            return response;
        else {
            System.err.println("Response is empty");
            return null;
        }
    }

    /**
     * methods for avoiding duplicate codes to write data on json file
     *
     * @param hasError error is existed or not
     * @param errorCode error code
     * @param responseJsonObject json object that supposed to write on file
     * @param listOfResponses an arrayList that saves subObjects that supposed to write in result JsonArray
     */
    private void errorAndResultSetter(String method,Boolean hasError, int errorCode, JsonObject responseJsonObject, ArrayList<JsonObject> listOfResponses) {
        responseJsonObject.addProperty("method", method);
        responseJsonObject.addProperty("hasError", hasError);
        responseJsonObject.addProperty("errorCode", errorCode);
        JsonArray temp = new JsonArray();
        for (JsonObject jsonObject : listOfResponses) {    // json objects are all results object that add to array and array will be add in response object
            temp.add(jsonObject);  // adding all objects of results in array
        }
        responseJsonObject.add("result", temp);   //adding whole array in main object of json
    }
    private void signupLines(String username, JsonElement jsonElement, FileWriter logWriter, String password, boolean operationOfMethods, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt signup");
        logWriter.write("[op]" + username + "-attempt signup" + "\n");
        ////////////////////////////////////////////////
        password = jsonElement.getAsJsonObject().get("password").getAsString();
        String firstName = jsonElement.getAsJsonObject().get("firstName").getAsString();
        String lastName = jsonElement.getAsJsonObject().get("lastName").getAsString();
        String bio = jsonElement.getAsJsonObject().get("bio").getAsString();
        LocalDate birthDate = LocalDate.parse(jsonElement.getAsJsonObject().get("birthDate").getAsString());
        LocalDate registrationDate = LocalDate.parse(jsonElement.getAsJsonObject().get("registrationDate").getAsString());
        ////////////////////////////////////////////////
        operationOfMethods = authenticationService.signup(username, password, bio, firstName, lastName, birthDate, registrationDate);
        // sees operation is successful or not
        System.out.println(operationOfMethods);
        if (operationOfMethods) {
            //currentAccount = findAccount(username);
            hasError = false;
            errorCode = 0;
            logger.info("signup successful");
            logWriter.write("[done]" + username + "-signup successful" + "\n");
        } else {
            System.err.println("signup failed, if you already have signed up please login");
            logger.info("signup failed, if you already have signed up please login");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            hasError = true;
            errorCode = 1;
        }
        // creates json object and json file :
        JsonObject stateOfSignup = new JsonObject();   // state of whether signup is successful or not
        stateOfSignup.addProperty("state", "done");
        listOfResponses.add(stateOfSignup);
        errorAndResultSetter("signup",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void loginLines(String username, JsonElement jsonElement, String password, FileWriter logWriter, boolean operationOfMethods, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt login");
        logWriter.write("[op]" + username + "-attempt login" + "\n");
        ////////////////////////////////////////////////
        password = jsonElement.getAsJsonObject().get("password").getAsString();
        operationOfMethods = authenticationService.login(username, password);
        // sees operation is successful or not
        if (operationOfMethods) {
            //currentAccount = findAccount(username);
            loginState.put(username, true);
            hasError = false;
            errorCode = 0;
            logger.info("login successful");
            System.out.println("Account : " + username);
            logWriter.write("[done]" + username + "-login successful" + "\n");
        } else {
            System.err.println("login failed");
            hasError = true;
            errorCode = 2;
            logger.info("login failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfLogin = new JsonObject();   // state of whether login is executed or not
        stateOfLogin.addProperty("state", "done");
        listOfResponses.add(stateOfLogin);

        errorAndResultSetter("login",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void sendTweetLines(String username, JsonElement jsonElement, String text, FileWriter logWriter, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TweetingServiceImpl tweetingService = new TweetingServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt sendTweet");
        logWriter.write("[op]" + username + "-attempt sendTweet" + "\n");
        ////////////////////////////////////////////////
        text = jsonElement.getAsJsonObject().get("text").getAsString();
        account = findAccount(username);
        // sees if tweet is for current username
        if (account != null && loginState.get(username).equals(true)) {
            tweetingService.tweeting(new Tweet(account, text));
            hasError = false;
            errorCode = 0;
            logger.info("sendTweet successful");
            logWriter.write("[done]" + username + "-sendTweet successful" + "\n");
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not tweet for others(the username has not logged in");
            hasError = true;
            errorCode = 3;
            logger.info("sendTweet unsuccessful");
            logWriter.write("[error]" + username + "-sendTweet unsuccessful" + "\n");
        } else {
            System.err.println("no account found for this information");
            hasError = true;
            errorCode = 4;
            logger.info("sendTweet unsuccessful");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfTweeting = new JsonObject();   // state of whether send operation is executed or not
        stateOfTweeting.addProperty("state", "done");
        stateOfTweeting.addProperty("Tweet", text);
        listOfResponses.add(stateOfTweeting);
        errorAndResultSetter("sendTweet",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void deleteTweetLines(String username, JsonElement jsonElement, String text, FileWriter logWriter, Tweet tweet, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TweetingServiceImpl tweetingService = new TweetingServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt deleteTweet");
        logWriter.write("[op]" + username + "-attempt deleteTweet" + "\n");
        ////////////////////////////////////////////////
        text = jsonElement.getAsJsonObject().get("text").getAsString();
        tweet = findTweet(username, text);
        account = findAccount(username);
        if (tweet != null && loginState.get(username).equals(true)) {
            tweetingService.deleting(new Tweet(account, text));
            hasError = false;
            errorCode = 0;
            logger.info("deleteTweet successful");
            logWriter.write("[done]" + username + "-deleteTweet successful" + "\n");
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not delete tweets for others(the username has not logged in)");
            hasError = true;
            errorCode = 3;
            logger.info("deleteTweet failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        } else {
            System.err.println("the tweet is not found");
            hasError = true;
            errorCode = 4;
            logger.info("deleteTweet failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfDeleting = new JsonObject();   // state of whether delete operation is executed or not
        stateOfDeleting.addProperty("state", "done");
        stateOfDeleting.addProperty("Tweet", text);
        listOfResponses.add(stateOfDeleting);
        errorAndResultSetter("deleteTweet",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void replyLines(String username, JsonElement jsonElement, String text, FileWriter logWriter, Tweet tweet, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TweetingServiceImpl tweetingService = new TweetingServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt reply");
        logWriter.write("[op]" + username + "-attempt reply" + "\n");
        ////////////////////////////////////////////////
        text = jsonElement.getAsJsonObject().get("text").getAsString();
        String reply = jsonElement.getAsJsonObject().get("reply").getAsString();
        tweet = findTweet(username, text);
        account = findAccount(username);
        if (tweet != null && loginState.get(username).equals(true)) {
            tweetingService.replying(new Tweet(account, text), new Reply(findAccount(username), reply, LocalDateTime.now(), 0, 0));
            hasError = false;
            errorCode = 0;
            logger.info("reply successful");
            logWriter.write("[done]" + username + "-reply successful" + "\n" );
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not reply in role of others(the username has not logged in");
            hasError = true;
            errorCode = 3;
            logger.info("reply failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        } else {
            System.err.println("the tweet is not found");
            hasError = true;
            errorCode = 4;
            logger.info("reply failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfReplying = new JsonObject();   // state of whether reply operation is executed or not
        stateOfReplying.addProperty("state", "done");
        stateOfReplying.addProperty("Tweet", text);
        stateOfReplying.addProperty("reply", reply);
        listOfResponses.add(stateOfReplying);
        errorAndResultSetter("reply",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void showTweetsOfLines(String username, JsonElement jsonElement, ArrayList<Tweet> showedTweets, FileWriter logWriter, Gson gson, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TimelineServiceImpl timelineService = new TimelineServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt showTweetsOf");
        logWriter.write("[op]" + username + "-attempt showTweetsOf" + "\n");
        ////////////////////////////////////////////////
        account = findAccount(username);
        if (loginState.get(username).equals(true)) {
            try {
                showedTweets = timelineService.showMyTweets(account);
                hasError = false;
                errorCode = 0;
                logger.info("showTweetsOf successful");
                logWriter.write("[done]" + username + "-showTweetsOf successful" + "\n");
            } catch (Exception e) {
                errorCode = 7;
                hasError = true;
                e.printStackTrace();
                logger.info("showTweetsOf failed");
                logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            }
        } else {
            try {
                showedTweets = timelineService.showTweetsOf(account);
                hasError = false;
                errorCode = 0;
                logger.info("showTweetsOf successful");
                logWriter.write("[done]" + username + "-showTweetsOf successful" + "\n");
            } catch (Exception e) {
                errorCode = 8;
                hasError = true;
                e.printStackTrace();
                logger.info("showTweetsOf failed");
                logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            }
        }
        // creates json object and json file :
        for (Tweet twt : showedTweets) {
            listOfResponses.add((JsonObject) gson.toJsonTree(twt));
        }
        errorAndResultSetter("showTweetsOf",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void timelineLines(String username, JsonElement jsonElement, ArrayList<Tweet> timeLine, FileWriter logWriter, Gson gson, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TimelineServiceImpl timelineService = new TimelineServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt timeline");
        logWriter.write("[op]" + username + "-attempt timeline" + "\n");
        ////////////////////////////////////////////////
        account = findAccount(username);
        if (loginState.get(username).equals(true)) {
            try {
                timeLine = timelineService.showMyTimeLine(account);
                hasError = false;
                errorCode = 0;
                logger.info("timeline successful");
                logWriter.write("[done]" + username + "-timeline successful" + "\n");
            } catch (Exception e) {
                errorCode = 7;
                hasError = true;
                e.printStackTrace();
                logger.info("timeline failed");
                logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            }
        } else {
            System.err.println("username has not logged in");
            errorCode = 8;
            hasError = true;
            logger.info("timeline failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        for (Tweet twt : timeLine) {
            listOfResponses.add((JsonObject) gson.toJsonTree(twt));
        }
        errorAndResultSetter("timeline",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void followLines(String username, JsonElement jsonElement, boolean operationOfMethods, FileWriter logWriter, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        ObserverServiceImpl observerService = new ObserverServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt follow");
        logWriter.write("[op]" + username + "-attempt follow" + "\n");
        ////////////////////////////////////////////////
        String usernameFollowed = jsonElement.getAsJsonObject().get("usernameFollowed").getAsString();
        account = findAccount(username);                            // doer account
        Account followTarget = findAccount(usernameFollowed);      // target account
        if (loginState.get(username).equals(true) && followTarget != null) {
            // followTarget != null : check if target account existed
            operationOfMethods = observerService.follow(account, followTarget);
            if (operationOfMethods) {
                System.out.println("followed successfully");
                hasError = false;
                errorCode = 0;
                logger.info("follow successful");
                logWriter.write("[done]" + username + "-follow successful" + "\n");
            } else {
                System.out.println("followed unsuccessfully");
                hasError = true;
                errorCode = 6;
                logger.info("follow failed");
                logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            }
        } else if (followTarget == null) {
            System.err.println("we couldn't find any account for this username, no account is followed");
            hasError = true;
            errorCode = 4;
            logger.info("follow failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not access to other's account(the username has not logged in)");
            hasError = true;
            errorCode = 3;
            logger.info("follow failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfFollowing = new JsonObject();   // state of whether follow operation is executed or not
        stateOfFollowing.addProperty("state", "done");
        listOfResponses.add(stateOfFollowing);
        errorAndResultSetter("follow",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void unfollowLines(String username, JsonElement jsonElement, boolean operationOfMethods, FileWriter logWriter, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        ObserverServiceImpl observerService = new ObserverServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt unfollow");
        logWriter.write("[op]" + username + "-attempt unfollow" + "\n");
        ////////////////////////////////////////////////
        String usernameUnfollowed = jsonElement.getAsJsonObject().get("usernameUnfollowed").getAsString();
        account = findAccount(username);          // doer account
        Account unfollowTarget = findAccount(usernameUnfollowed);      // target account
        if (loginState.get(username).equals(true) && unfollowTarget != null) {
            // target != null : check if target account existed
            operationOfMethods = observerService.unfollow(account, unfollowTarget);
            if (operationOfMethods) {
                System.out.println("unfollowed successfully");
                hasError = false;
                errorCode = 0;
                logger.info("unfollow successful");
                logWriter.write("[done]" + username + "-unfollow successful" + "\n");
            } else {
                System.out.println("unfollowed unsuccessfully");
                hasError = true;
                errorCode = 6;
                logger.info("unfollow failed");
                logWriter.write("[error]" + username + "-wrong inputs" + "\n");
            }
        } else if (unfollowTarget == null) {
            System.err.println("we couldn't find any account for this username, no account is followed");
            hasError = true;
            errorCode = 4;
            logger.info("follow failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not access to other's account(the username has not logged in)");
            hasError = true;
            errorCode = 3;
            logger.info("follow failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfUnfollowing = new JsonObject();   // state of whether unfollow operation is executed or not
        stateOfUnfollowing.addProperty("state", "done");
        listOfResponses.add(stateOfUnfollowing);
        errorAndResultSetter("unfollow",hasError, errorCode, responseJsonObject, listOfResponses);
    }
    private void likeLines(String username, JsonElement jsonElement, String text, FileWriter logWriter, Tweet tweet, Account account, ArrayList<JsonObject> listOfResponses, JsonObject responseJsonObject) throws IOException {
        TweetingServiceImpl tweetingService = new TweetingServiceImpl();
        username = jsonElement.getAsJsonObject().get("username").getAsString();
        /////////////////////////////////////////////////
        logger.info("attempt like");
        logWriter.write("[op]" + username + "-attempt like" + "\n");
        ////////////////////////////////////////////////
        text = jsonElement.getAsJsonObject().get("text").getAsString();
        tweet = findTweet(username, text);
        account = findAccount(username);
        if (!tweet.equals(null) && loginState.get(username).equals(true)) {
            tweetingService.liking(new Tweet(account, text),username);
            hasError = false;
            errorCode = 0;
            logger.info("like successful");
            logWriter.write("[done]" + username + "-like successful" + "\n");
        } else if (!loginState.get(username).equals(true)) {
            System.err.println("you can not delete tweets for others(the username has not logged in");
            hasError = true;
            errorCode = 3;
            logger.info("like failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        } else {
            System.err.println("the tweet is not found");
            hasError = true;
            errorCode = 4;
            logger.info("like failed");
            logWriter.write("[error]" + username + "-wrong inputs" + "\n");
        }
        // creates json object and json file :
        JsonObject stateOfLiking = new JsonObject();   // state of whether delete operation is executed or not
        stateOfLiking.addProperty("state", "done");
        stateOfLiking.addProperty("Tweet", text);
        listOfResponses.add(stateOfLiking);
        errorAndResultSetter("like",hasError, errorCode, responseJsonObject, listOfResponses);
    }
}

