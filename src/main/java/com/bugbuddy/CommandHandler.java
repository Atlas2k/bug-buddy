package com.bugbuddy;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;

public class CommandHandler {

    MongoCollection<Document> bugsCollection;
    StandardIO io;

    public CommandHandler(MongoCollection<Document> bugsCollection, StandardIO io) {
        this.bugsCollection = bugsCollection;
        this.io = io;
    }

    public void handleCommand(String command) {
        switch (Integer.parseInt(command)) {
            case 1: // Create bug report
                try {
                    String reportHeading = io.getPromptedInput("Enter bug report heading: ");
                    String reportBody = io.getPromptedInput("Enter details for bug report body: ");
                    Document entry;
                    int uniqueId;
                    if (bugsCollection.countDocuments() > 0) {
                        // Fetches _id field of the last entry added to the collection and formats the data type accordingly
                        uniqueId = Integer.parseInt(bugsCollection.find().sort(new Document("_id", -1)).first().get("_id").toString()) + 1;
                        entry = new Document("_id", String.valueOf(uniqueId));
                    } else {
                        entry = new Document("_id", "0");
                        uniqueId = 0;
                    }
                    entry.append("title", reportHeading);
                    entry.append("initial description", reportBody);
                    entry.append("solved", false);
                    bugsCollection.insertOne(entry);
                    io.update("Bug report has been created with an ID of: " + uniqueId + "\n");
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 2: // Add updates to bug report by ID
                try {
                    String bugId = io.getPromptedInput("Enter the bug report ID to add updates to: ");
                    String updateText = io.getPromptedInput("Enter details to be added to bug report: ");
                    if (0 < bugsCollection.countDocuments(new Document("_id", bugId))) {
                        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        bugsCollection.updateOne(new Document("_id", bugId), Updates.set(date, updateText));
                        io.update("Update has been added to bug report with ID " + bugId + "\n");
                    } else {
                        io.update("Bug report with ID " + bugId + " was not found." + "\n");
                    }
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 3: // Mark bug report as solved by ID
                try {
                    String bugId = io.getPromptedInput("Enter the bug report ID to mark as solved: ");
                    if (0 < bugsCollection.countDocuments(new Document("_id", bugId))) {
                        bugsCollection.updateOne(new Document("_id", bugId), Updates.set("solved", true));
                        io.update("Bug report with ID " + bugId + " has been marked as solved." + "\n");
                    } else {
                        io.update("Bug report with ID " + bugId + " was not found." + "\n");
                    }
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 4: // Mark bug report as not solved by ID
                try {
                    String bugId = io.getPromptedInput("Enter the bug report ID to mark as unsolved: ");
                    if (0 < bugsCollection.countDocuments(new Document("_id", bugId))) {
                        bugsCollection.updateOne(new Document("_id", bugId), Updates.set("solved", false));
                        io.update("Bug report with ID " + bugId + " has been marked as unsolved." + "\n");
                    } else {
                        io.update("Bug report with ID " + bugId + " was not found." + "\n");
                    }
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 5: // Print one bug report by ID
                try {
                    String bugId = io.getPromptedInput("Enter the bug report ID to print: ");
                    if (0 < bugsCollection.countDocuments(new Document("_id", bugId))) {
                        // Using Json library to pretty print the Document returned from database
                        JsonWriterSettings.Builder settingsBuilder = JsonWriterSettings.builder().indent(true);
                        io.update(bugsCollection.find(new Document("_id", bugId)).first().toJson(settingsBuilder.build()) + "\n");
                    } else {
                        io.update("Bug report with ID " + bugId + " was not found." + "\n");
                    }
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 6: // Delete bug report by ID
                try {
                    String bugId = io.getPromptedInput("Enter the bug report ID to be deleted: ");
                    if (bugsCollection.deleteOne(new Document("_id", bugId)).getDeletedCount() > 0) {
                        io.update("Bug report with ID " + bugId + " has been deleted." + "\n");
                    } else {
                        io.update("Bug report with ID " + bugId + " was not found." + "\n");
                    }
                } catch (Exception e) {
                    io.update(e.getMessage() + "\n");
                }
                break;
            case 0: // Exit program
                System.exit(0);
                break;
        }
    }
}
