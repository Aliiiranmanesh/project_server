package database;

import Utils.Convertor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    private String path;

    public Table(String path) {
        this.path = path;
    }

    public void insert(HashMap<String, String> row) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        fileWriter.write(Convertor.MapToString(row) + '\n');
        fileWriter.close();
    }

    public ArrayList<HashMap<String, String>> get() {
        return new ArrayList<>();
    }
}
