package model;

public class Request {
    private String topic;
    private String description;
    private String status;

    public Request(String topic, String description, String status) {
        this.topic = topic;
        this.description = description;
        this.status = status;
        DataBase.getDataBase().setAllRequests(this);
    }
}
