package persistence;

import model.Calculator;
import org.json.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that writes file to calculator
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads calculator from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Calculator read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCalculator(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses calculator from JSON object and returns it
    private Calculator parseCalculator(JSONObject jsonObject) {
        Calculator c = new Calculator();
        addCalculator(c, jsonObject);
        return c;
    }

    // MODIFIES: c
    // EFFECTS: parses inputList and resultHistory from JSON object and adds them to calculator
    private void addCalculator(Calculator c, JSONObject jsonObject) {
        JSONArray inputListArray = jsonObject.getJSONArray("inputList");
        for (Object json : inputListArray) {
            if (!json.equals(0)) {
                c.addInput((Double.parseDouble(json.toString())));
            }
        }
        JSONArray resultListArray = jsonObject.getJSONArray("resultHistory");
        for (Object json : resultListArray) {
            c.getHistory().addResult((Double.parseDouble(json.toString())));
        }
    }
}
