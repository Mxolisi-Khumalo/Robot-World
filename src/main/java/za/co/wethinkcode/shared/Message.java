package za.co.wethinkcode.shared;

public class Message {
    private String command;

    public Message(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}