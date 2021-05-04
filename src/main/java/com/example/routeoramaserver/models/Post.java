package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Post implements Serializable {
    private static final long serialVersionUID = 6529685098267757693L;
    private int userId;
    private int postId;
    private String title;
    private String content;
    private String photo;
    private int likeCount;
    private Date dateOfCreation;
    private int placeId;

    public Post(int postId,int userId, String title, String content, String photo, int likeCount, Date dateOfCreation, int placeId) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.photo = photo;
        this.likeCount = likeCount;
        this.dateOfCreation = dateOfCreation;
        this.placeId = placeId;
        this.userId = userId;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
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
        return postId == post.postId && likeCount == post.likeCount && placeId == post.placeId && title.equals(post.title) && content.equals(post.content) && Objects.equals(photo, post.photo) && dateOfCreation.equals(post.dateOfCreation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, title, content, photo, likeCount, dateOfCreation, placeId);
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", photo='" + photo + '\'' +
                ", likeCount=" + likeCount +
                ", dateOfCreation=" + dateOfCreation +
                ", placeId=" + placeId +
                '}';
    }
}
