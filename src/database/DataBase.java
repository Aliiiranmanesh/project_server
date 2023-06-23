package database;

import java.util.HashMap;

public class DataBase {
    private static DataBase instance = null;
    private HashMap<String, Table> tables;

    private DataBase(String name) {
        tables = new HashMap<>();
        switch (name){
            case "addToWallet":
                tables.put("addToWallet",new Table("src/data/AccountsNetWorth.txt"));
                break;
            case "register":
                tables.put("register",new Table("src/data/User&Pass.txt"));
                break;
        }

    }

    public static DataBase getInstance(String name) {
        if (instance == null)
            instance = new DataBase(name);
        return instance;
    }

    public Table getTable(String name) {
        return tables.get(name);
    }
}
