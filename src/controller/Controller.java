package controller;

import Utils.Convertor;
import database.DataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {

    private String register(HashMap<String, String> data) {
        try {
            ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get("src/Data/User&Pass.txt")));
            if (lines.contains(Convertor.MapToString(data)))
                return "account is already created";
            else {
                DataBase.getInstance("register").getTable("register").insert(data);
                return "account successfully created";
            }
        } catch (IOException e) {
            return "something went wrong";
        }
    }

    private String addToWallet(HashMap<String, String> data) throws IOException {
        ArrayList<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get("src/Data/AccountsNetWorth.txt")));
        String line = Convertor.MapToString(data);
        String[] line1 = line.split(",,");
        String[] line2 = line1[1].split(":");
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).contains(line1[0])) {
                String[] line3 = fileContent.get(i).split(":");
                String newLine = line3[0] + ':' + line3[1] + ':' + (Integer.parseInt(line3[2]) + Integer.parseInt(line2[1]));
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
            default -> "Error";
        };

    }
}
