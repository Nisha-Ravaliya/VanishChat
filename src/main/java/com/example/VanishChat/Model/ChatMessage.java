package com.example.VanishChat.Model;

public class ChatMessage {
    private long id;
    private String sender;
    private String receiver;
    private String message;
    private int vanishTime;

    // getters and setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }

    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getVanishTime() { return vanishTime; }
    public void setVanishTime(int vanishTime) { this.vanishTime = vanishTime; }
}
