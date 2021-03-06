package model;

public class Request {
    private final int Id;
    private static int numOfAllRequest;
    private final String senderName;
    private final String topic;
    private final String description;
    private String status;

    public Request(String senderName, String topic, String description, String status) {
        this.Id = numOfAllRequest;
        numOfAllRequest += 1;
        this.senderName = senderName;
        this.topic = topic;
        this.description = description;
        this.status = status;
        DataBase.getDataBase().setAllRequests(this);
    }

    public int getId() {
        return Id;
    }

    public String getSenderName() {
        return senderName;
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

    public static void setNumOfAllRequest(int numOfAllRequest) {
        Request.numOfAllRequest = numOfAllRequest;
    }
}
