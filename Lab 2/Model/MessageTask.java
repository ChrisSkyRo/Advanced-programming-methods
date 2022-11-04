package Model;

import Utils.Constants;

import java.time.LocalDateTime;

public class MessageTask extends Task {
    private final String message;
    private final String from;
    private final String to;
    private final LocalDateTime date;

    public MessageTask(String taskId, String description, String message, String from, String to, LocalDateTime date) {
        super(taskId, description);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public void execute() {
        System.out.println(this);
    }

    public String toString() {
        return super.toString() + " " + message + " " + " " + from + " " + to + " " + date.format(Constants.DATE_TIME_FORMATTER);
    }
}
