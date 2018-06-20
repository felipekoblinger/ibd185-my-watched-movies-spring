package br.gov.sp.fatec.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class JsonIO {
    public JsonNode load(String stringURL) {
        URL url;
        JsonNode jsonNode;
        try {
            url = new URL(stringURL);
            jsonNode = new ObjectMapper().readTree(url);
        } catch (IOException e) {
            return null;
        }
        return jsonNode;
    }
}
