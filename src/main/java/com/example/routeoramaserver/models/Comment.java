package com.example.routeoramaserver.models;

import java.sql.Date;
import java.util.Objects;

public class Comment {
    private static final long serialVersionUID = 6529685098267757700L;
    private int userId;
    private int postId;
    private String content;
    private Date timestamp;

    public Comment(int userId, int postId, String content, Date timestamp) {
        this.userId = userId;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return userId == comment.userId && postId == comment.postId && Objects.equals(content, comment.content) && Objects.equals(timestamp, comment.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId, content, timestamp);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", content='" + content + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
