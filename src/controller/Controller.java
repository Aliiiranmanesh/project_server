package controller;

import Utils.Convertor;
import database.DataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {


    private String Authorize(HashMap<String, String> data) throws IOException {
        //Authorize
        //User:.....,,Pass:.....
        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("src/Data/User&Pass.txt"));
        for (String str : lines)
            if (str.equals(("User:" + data.get("User") + ",,Pass:" + data.get("Pass")))) {
                ArrayList<String> line = new ArrayList<>();
                line.add(data.get("User"));
                Files.write(Paths.get("src/Data/CurrentUser.txt"), line);
                return "logged in successfully";
            }
        return "Username or Password is wrong";

    }

    private boolean conditions(String str, int n) {
        for (int i = 0; i < n; i++) {

            if ((str.charAt(i) >= '0' || str.charAt(i) <= '9') && (str.charAt(i) >= 'A' || str.charAt(i) <= 'Z')) {
                return true;
            }
        }
        return false;
    }

    private String register(HashMap<String, String> data) {
        //register
        //User:....,,Email:......,,Pass:...
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("src/Data/User&Pass.txt")));
            for (String str : lines) {
                if (str.contains(data.get("User")))
                    return "account is already created";
            }
            if (data.get("Pass").length() < 8 || data.get("Pass").contains(data.get("User")) || !data.get("Email").contains("@yahoo.com")  || !this.conditions(data.get("Pass"), data.get("Pass").length()))
                return "Password or email isn't right!";
            DataBase.getInstance("register").getTable("register").insert(data);
            return "account successfully created";
        } catch (IOException e) {
            return "something went wrong";
        }
    }

    private String RateBook(HashMap<String, String> data) throws IOException {
        //RateBook
        //Name:....,,Rate:...,,Type:...
        if (data.get("Type").equalsIgnoreCase("AudioBooks")) {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("src/Data/AudioBooks/" + data.get("Name") + "/Info.txt"));
            lines.set(0, String.valueOf((Integer.parseInt(lines.get(0)) + Integer.parseInt(data.get("Rate")))));
            lines.set(1, "Count:" + (Integer.parseInt(lines.get(1).split(":")[1]) + 1));
            Files.write(Paths.get("src/Data/AudioBooks/" + data.get("Name") + "/Info.txt"), lines);
        } else {
            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(Paths.get("src/Data/Books/" + data.get("Name") + "/Info.txt"));
            lines.set(0, String.valueOf((Integer.parseInt(lines.get(0)) + Integer.parseInt(data.get("Rate")))));
            lines.set(1, "Count:" + (Integer.parseInt(lines.get(1).split(":")[1]) + 1));
            Files.write(Paths.get("src/Data/Books/" + data.get("Name") + "/Info.txt"), lines);
        }
        return "rate successfully added";
    }

    private String addToWallet(HashMap<String, String> data) throws IOException {
        //addToWallet
        //User:....,,Money:......
        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("src/Data/AccountsNetWorth.txt")));
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(data.get("User"))) {
                String newLine = "Money:" + (Integer.parseInt(data.get("Money")) + Integer.parseInt(fileContent.get(i).split(",,")[0].split(":")[1])) + ",," + "User:" + data.get("User");
                fileContent.set(i, newLine);
                Files.write(Paths.get("src/Data/AccountsNetWorth.txt"), fileContent);
                return "Money added successfully";
            }
        }
        DataBase.getInstance("addToWallet").getTable("addToWallet").insert(data);
        return "Money initiated successfully";
    }


    public String run(String command, String data) throws IOException {
        HashMap<String, String> dataMap = Convertor.StringToMap(data);
        return switch (command) {
            case "register" -> register(dataMap);
            case "addToWallet" -> addToWallet(dataMap);
            case "RateBook" -> RateBook(dataMap);
            case "Authorize" -> Authorize(dataMap);
            default -> "Error";
        };

    }
}
