package com.example.VanishChat.DTO;

public class ChatMessageDto {
    private String from;
    private String to;
    private String subject;
    private String message;
    private long expiry; // milliseconds, 0 means never expires

    // Getters and setters

    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public long getExpiry() {
        return expiry;
    }
    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }
}
