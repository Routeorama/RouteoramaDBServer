package com.example.routeoramaserver.models;

import java.util.List;
import java.util.Objects;

public class CommentContainer {
    private static final long serialVersionUID = 6529685098267757701L;
    private List<Comment> comments;
    private boolean hasMoreComments;

    public CommentContainer(List<Comment> comments, boolean hasMoreComments) {
        this.comments = comments;
        this.hasMoreComments = hasMoreComments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public boolean isHasMoreComments() {
        return hasMoreComments;
    }

    public void setHasMoreComments(boolean hasMoreComments) {
        this.hasMoreComments = hasMoreComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentContainer that = (CommentContainer) o;
        return hasMoreComments == that.hasMoreComments && Objects.equals(comments, that.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comments, hasMoreComments);
    }

    @Override
    public String toString() {
        return "CommentContainer{" +
                "comments=" + comments +
                ", hasMoreComments=" + hasMoreComments +
                '}';
    }
}
