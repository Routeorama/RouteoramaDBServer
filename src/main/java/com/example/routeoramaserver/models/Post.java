package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class Post implements Serializable {
    private static final long serialVersionUID = 6529685098267757693L;
    private int userId;
    private int postId;
    private String title;
    private String content;
    private byte[] photo;
    private int likeCount;
    private Date dateOfCreation;
    private int placeId;

    public Post(int userId, int postId, String title, String content, byte[] photo, int likeCount, Date dateOfCreation, int placeId) {
        this.userId = userId;
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.likeCount = likeCount;
        this.dateOfCreation = dateOfCreation;
        this.placeId = placeId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return userId == post.userId && postId == post.postId && likeCount == post.likeCount && placeId == post.placeId && title.equals(post.title) && content.equals(post.content) && Arrays.equals(photo, post.photo) && dateOfCreation.equals(post.dateOfCreation);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(userId, postId, title, content, likeCount, dateOfCreation, placeId);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId=" + userId +
                ", postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", photo=" + Arrays.toString(photo) +
                ", likeCount=" + likeCount +
                ", dateOfCreation=" + dateOfCreation +
                ", placeId=" + placeId +
                '}';
    }
}
