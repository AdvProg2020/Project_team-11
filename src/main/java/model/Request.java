package model;

import java.util.ArrayList;

public class Request {
    private String topic;
    private String description;
    private String status;
    private static ArrayList<Request> allRequests = new ArrayList<>();

    public Request(String topic, String description, String status) {
        this.topic = topic;
        this.description = description;
        this.status = status;
        allRequests.add(this);
    }
}
