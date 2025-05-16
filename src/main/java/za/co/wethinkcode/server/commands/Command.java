package za.co.wethinkcode.server.commands;

import com.google.gson.JsonObject;

public interface Command {
    JsonObject execute();
}
