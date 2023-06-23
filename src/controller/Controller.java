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
                DataBase.getInstance().getTable("register").insert(data);
                return "account successfully created";
            }
        } catch (IOException e) {
            return "something went wrong";
        }
    }

    private String money() {
        //Todo
        return "";
    }

    public String run(String command, String data) {
        HashMap<String, String> dataMap = Convertor.StringToMap(data);
        return switch (command) {
            case "register" -> register(dataMap);
            case "money" -> money();
            default -> "Error";
        };

    }
}
