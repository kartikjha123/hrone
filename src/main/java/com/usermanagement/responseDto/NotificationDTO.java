package com.usermanagement.responseDto;



public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private String type;
    private Long recipientId; // just the ID, no circular reference

    public NotificationDTO(Long id, String title, String message, String type, Long recipientId) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.type = type;
        this.recipientId = recipientId;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public Long getRecipientId() { return recipientId; }
}
