package com.example.routeoramaserver.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PostContainer implements Serializable {
    private static final long serialVersionUID = 6529685098267757694L;
    private boolean hasMorePosts;
    private List<Post> posts = new ArrayList<>();;

    public PostContainer() {
    }

    public PostContainer(boolean hasMorePosts, List<Post> posts) {
        this.hasMorePosts = hasMorePosts;
        this.posts = posts;
    }

    public void addAPost(Post post){
        posts.add(post);
    }
    public boolean getHasMorePosts() {
        return hasMorePosts;
    }

    public void setHasMorePosts(boolean hasMorePosts) {
        this.hasMorePosts = hasMorePosts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostContainer that = (PostContainer) o;
        return hasMorePosts == that.hasMorePosts && posts.equals(that.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hasMorePosts, posts);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("(");
        for (Post post : posts)
            builder.append(post).append(",");
        String postsToDisplay = builder.toString();

        return "PostContainer{" +
                "hasMorePosts=" + hasMorePosts +
                ", posts= " + postsToDisplay +
                '}';
    }
}
