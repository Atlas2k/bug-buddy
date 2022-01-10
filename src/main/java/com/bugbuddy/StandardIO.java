package com.bugbuddy;

import java.io.*;

public class StandardIO {

    BufferedReader console;

    public StandardIO() {
        console = new BufferedReader(new InputStreamReader(System.in));
    }

    public String getInput() throws IOException {
        return console.readLine();
    }

    public String getPromptedInput(String prompt) throws IOException {
        update(prompt);
        return console.readLine();
    }

    public void update(String message) {
        System.out.print(message);
    }
}
