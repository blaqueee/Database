package com.FileService;

import com.Line;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReaderAndWriter {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = Paths.get("./database.json");

    public static Line[] readFile(){
        String json = null;
        try {
            json = Files.readString(PATH);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return GSON.fromJson(json, Line[].class);
    }
}
