package com.bugbuddy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

public class URIFileHandler {
    File file;
    StandardIO io;

    public URIFileHandler(StandardIO io) {
        this.file = new File("DatabaseURI.txt");
        this.io = io;
    }

    public boolean hasFileBeenCreated() {
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }

    public void createFile() {
        try {  
            String uri = io.getPromptedInput("Please enter the database URI: ");
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(uri);
            writer.close();
        } catch (Exception e) {
            io.update(e.getMessage() + "\n");
        }
    }

    public String getURI() {
        try {
            if (hasFileBeenCreated()) {
                return readURI();
            } else {
                createFile();
                return readURI();
            }
        } catch (Exception e) {
            io.update(e.getMessage() + "\n");
        }
        return null;
    }

    private String readURI() throws FileNotFoundException {
        Scanner s = new Scanner(file);
        String uri = s.nextLine();
        s.close();
        return uri;
    }
}