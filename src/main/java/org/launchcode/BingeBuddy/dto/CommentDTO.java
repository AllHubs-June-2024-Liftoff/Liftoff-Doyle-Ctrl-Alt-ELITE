package org.launchcode.BingeBuddy.dto;

import java.time.LocalDateTime;

public class CommentDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdAt;
    private String username;

    public CommentDTO(Integer id, String content, LocalDateTime createdAt, String username) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.username = username;
    }

    public Integer getId() { return id; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getUsername() { return username; }
}
