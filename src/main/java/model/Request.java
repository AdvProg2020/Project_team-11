package model;

public class Request {
    private int Id;
    private static int numOfAllRequest = 1;
    private Account sender;
    private String topic;
    private String description;
    private String status;

    public Request(Account sender, String topic, String description, String status) {
        this.Id = numOfAllRequest;
        numOfAllRequest += 1;
        this.sender = sender;
        this.topic = topic;
        this.description = description;
        this.status = status;
        DataBase.getDataBase().setAllRequests(this);
    }

    public int getId() {
        return Id;
    }

    public Account getSender() {
        return sender;
    }

    public String getTopic() {
        return topic;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
