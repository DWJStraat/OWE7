package org.fundamentals;

import net.sf.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class Config {
    private final static JSONObject conf;

    static {
        try {
            conf = JSONObject.fromObject(new JSONParser().parse(
                    new FileReader("src/Main/resources/config/config.json")
            ));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return (String) conf.get(key);
    }


}
