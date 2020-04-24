package model;

public class Request {
    private Buyer sender;
    private String topic;
    private String description;
    private String status;

    public Request(Buyer sender, String topic, String description, String status) {
        this.sender = sender;
        this.topic = topic;
        this.description = description;
        this.status = status;
        DataBase.getDataBase().setAllRequests(this);
    }

    public Buyer getSender() {
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
}
