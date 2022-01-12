package com.bugbuddy;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App {
    public static void main(String[] args) {
        try {
            // Initializing
            StandardIO io = new StandardIO();
            URIFileHandler fileHandler = new URIFileHandler(io);
            MongoClient dbClient = MongoClients.create(fileHandler.getURI());
            MongoCollection<Document> bugsCollection = dbClient.getDatabase("bugs").getCollection("main");
            CommandHandler commandHandler = new CommandHandler(bugsCollection, io);

            // Main program
            io.update("Choose one of the following options:\n1 - Create bug report\n2 - Update bug report by ID\n"
                    + "3 - Mark bug report solved by ID\n4 - Mark bug report as unsolved by ID\n5 - Print bug report by ID"
                    + "\n6 - Delete bug report by ID\n0 - Exit\n");
            while (true) {
                commandHandler.handleCommand(io.getInput());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
