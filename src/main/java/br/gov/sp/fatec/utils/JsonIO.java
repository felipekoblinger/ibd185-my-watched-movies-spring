package br.gov.sp.fatec.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class JsonIO {
    public JsonObject load(String stringURL) {
        URL url;
        JsonObject jsonObject;
        try {
            url = new URL(stringURL);
            try (InputStreamReader inputStreamReader = new InputStreamReader(url.openStream())) {
                jsonObject = new Gson().fromJson(inputStreamReader, JsonObject.class);
            }
        } catch (IOException e) {
            return null;
        }
        return jsonObject;
    }
}
