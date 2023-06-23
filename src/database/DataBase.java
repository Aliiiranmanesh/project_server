package database;

import java.util.HashMap;

public class DataBase {
    private static DataBase instance = null;
    private HashMap<String, Table> tables;

    private DataBase() {
        tables = new HashMap<>();
        tables.put("register",new Table("src/data/User&Pass.txt"));
    }

    public static DataBase getInstance() {
        if (instance == null)
            instance = new DataBase();
        return instance;
    }

    public Table getTable(String name) {
        return tables.get(name);
    }
}
