package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

public class Comment implements Serializable {
    private static final long serialVersionUID = 6529685098267757700L;
    private int userId;
    private String displayName;
    private int postId;
    private String content;
    private Timestamp timestamp;

    public Comment(int userId, String displayName, int postId, String content, Timestamp timestamp) {
        this.userId = userId;
        this.displayName = displayName;
        this.postId = postId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return userId == comment.userId && postId == comment.postId && Objects.equals(displayName, comment.displayName) && Objects.equals(content, comment.content) && Objects.equals(timestamp, comment.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, postId, content, timestamp);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userId=" + userId +
                ", displayName='" + displayName + '\'' +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
