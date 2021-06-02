package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.db.DatabaseConnection;
import com.example.routeoramaserver.models.Comment;
import com.example.routeoramaserver.models.CommentContainer;
import com.example.routeoramaserver.models.Post;
import com.example.routeoramaserver.models.PostContainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PostDAOManager implements IPostDAO {

    private DatabaseConnection databaseConnection;

    public PostDAOManager() {
        try {
            databaseConnection = DatabaseConnection.getInstance();
        } catch (SQLException e) {
            System.out.println("Could not connect to database" + e.getMessage());
        }
    }

    @Override
    public Post NewPost(Post post, List<String> tags) {
        Connection connection = null;
        PreparedStatement statement = null;
        Post newPost = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"Post\" (\"title\", \"content\", \"photo\", " +
                    "\"dateOfCreation\", \"placeid\", \"userid\", \"photoType\")" +
                    " values (?,?,?,?,?,?,?)");
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getContent());
            statement.setBytes(3, post.getPhoto());
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            post.setDateOfCreation(date);

            statement.setDate(4, post.getDateOfCreation());
            statement.setInt(5, post.getPlaceId());
            statement.setInt(6, post.getUserId());
            statement.setString(7, post.getPhotoType());
            int m = statement.executeUpdate();
            if (m == 1) {
                newPost = GetPost(post.getTitle());
                addTagsToSpecificPost(newPost, addTagsFromContent(tags));
                return newPost;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Post already exists" + e.getMessage());
        } finally {
            if (statement != null) try { statement.close(); } catch (Exception e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return newPost;
    }

    private void addTagsToSpecificPost(Post post, List<Integer> tagsids) {
        Connection connection = null;
        PreparedStatement statement = null;

        for (int tagid : tagsids) {
            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("INSERT INTO \"PostTag\" (\"postid\", \"tagid\") values (?,?)");
                statement.setInt(1, post.getPostId());
                statement.setInt(2, tagid);

                int m = statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("PostTag already exists -> " + tagid + ", " + post.getPostId() + ". " + e.getMessage());
            } finally {
                if (statement != null) try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<Integer> addTagsFromContent(List<String> tags) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Integer> idsOfInsertedTags = new ArrayList<>();

        for (String tag : tags) {
            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("INSERT INTO \"Tag\" (\"tagcontent\") values (?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, tag);
                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    int id = resultSet.getInt(2);
                    if (id > 0) {
                        idsOfInsertedTags.add(id);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Tag already exists -> " + tag + ". " + e.getMessage());
            } finally {
                if (resultSet != null) try {
                    resultSet.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (statement != null) try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return (idsOfInsertedTags);
    }

    @Override
    public boolean DeletePost(int postID) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("DELETE FROM \"Post\" WHERE \"postid\" = ?");
            statement.setInt(1, postID);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to delete the post " + e.getMessage());
        }
        finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Post GetPost(int postID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Post post = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Post\" WHERE \"postid\" = ? ");
            statement.setInt(1, postID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int newPostID = resultSet.getInt("postid");
                String newPostTitle = resultSet.getString("title");
                String newPostContent = resultSet.getString("content");
                byte[] newPostPhoto = resultSet.getBytes("photo");
                int newPostLikes = resultSet.getInt("likecount");
                Date newPostDate = resultSet.getDate("dateOfCreation");
                int newPlaceID = resultSet.getInt("placeid");
                int newUserID = resultSet.getInt("userid");
                String photoType = resultSet.getString("photoType");
                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType, getPostCreator(newUserID));
            }
        } catch (SQLException e) {
            System.out.println("Could not find specified post " + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return post;
    }

    private String getPostCreator(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String displayName = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT \"displayname\" FROM \"user\" WHERE \"userid\" = ?");
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                displayName = resultSet.getString("displayname");
            }
        } catch (SQLException ex) {
            System.out.println("Could not fetch name of creator" + ex.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return displayName;
    }

    @Override
    public Post GetPost(String title) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Post post = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Post\" WHERE \"title\" = ? ");
            statement.setString(1, title);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int newPostID = resultSet.getInt("postid");
                String newPostTitle = resultSet.getString("title");
                String newPostContent = resultSet.getString("content");
                byte[] newPostPhoto = resultSet.getBytes("photo");
                int newPostLikes = resultSet.getInt("likecount");
                Date newPostDate = resultSet.getDate("dateOfCreation");
                int newPlaceID = resultSet.getInt("placeid");
                int newUserID = resultSet.getInt("userid");
                String photoType = resultSet.getString("photoType");
                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType, getPostCreator(newUserID));
            }
        } catch (SQLException e) {
            System.out.println("Could not find specified post " + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return post;
    }

    @Override
    public PostContainer LoadPostsFromChannel(int placeID, int postID) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Post post = null;
        List<Post> posts = new ArrayList<>();
        PostContainer postContainer = new PostContainer();

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            if (postID == 0) {
                statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Post\"\n" +
                        "WHERE placeid = ?\n" +
                        "ORDER BY \"postid\" DESC\n" +
                        "LIMIT 6;");
                statement.setInt(1, placeID);
            } else {
                statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Post\"\n" +
                        "WHERE placeid = ?\n and postid < ?" +
                        "ORDER BY \"postid\" DESC\n" +
                        "LIMIT 6;");
                statement.setInt(1, placeID);
                statement.setInt(2, postID);
            }
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int newPostID = resultSet.getInt("postid");
                String newPostTitle = resultSet.getString("title");
                String newPostContent = resultSet.getString("content");
                byte[] newPostPhoto = resultSet.getBytes("photo");
                int newPostLikes = resultSet.getInt("likecount");
                Date newPostDate = resultSet.getDate("dateOfCreation");
                int newPlaceID = resultSet.getInt("placeid");
                int newUserID = resultSet.getInt("userid");
                String photoType = resultSet.getString("photoType");

                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType, getPostCreator(newUserID));
                posts.add(post);
            }

            if (posts.size() == 0) {
                postContainer.setPosts(null);
                postContainer.setHasMorePosts(false);
            } else if (posts.size() > 5) {
                posts.remove(5);
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(true);
            } else {
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(false);
            }

        } catch (SQLException e) {
            System.out.println("Could not load posts " + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postContainer;
    }

    @Override
    public void LikeThePost(int postId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        if (!IsAlreadyLiked(postId, userId)) {

            try {
                connection = databaseConnection.getConnection();
                connection.setSchema("Routeourama");
                statement = connection.prepareStatement("INSERT INTO \"Likes\" VALUES (?,?)");
                statement.setInt(1, userId);
                statement.setInt(2, postId);

                statement.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Creating like request failed" + e.getMessage());
            } finally {
                if (statement != null) try {
                    statement.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (connection != null) try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void UnlikeThePost(int postId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("DELETE FROM \"Likes\" WHERE \"userid\" = ? AND \"postid\" = ?");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Deleting unlike request failed" + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean IsAlreadyLiked(int postId, int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Likes\" WHERE \"userid\" = ? AND \"postid\" = ?");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userid = resultSet.getInt("userid");
                int postid = resultSet.getInt("postid");
                if (postId == postid && userId == userid) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not fetch the likes for user" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public PostContainer GetPostsForNewsFeed(int userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Post post = null;
        List<Post> posts = new ArrayList<>();
        PostContainer postContainer = new PostContainer();

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");

            statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Post\"\n" +
                    "INNER JOIN \"Routeourama\".\"Follow\"  on \"Follow\".placeid = \"Post\".placeid\n" +
                    "WHERE \"Follow\".userid = ?\n" +
                    "ORDER BY \"postid\" DESC\n" +
                    "LIMIT 11;\n");
            statement.setInt(1, userId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int newPostID = resultSet.getInt("postid");
                String newPostTitle = resultSet.getString("title");
                String newPostContent = resultSet.getString("content");
                byte[] newPostPhoto = resultSet.getBytes("photo");
                int newPostLikes = resultSet.getInt("likecount");
                Date newPostDate = resultSet.getDate("dateOfCreation");
                int newPlaceID = resultSet.getInt("placeid");
                int newUserID = resultSet.getInt("userid");
                String photoType = resultSet.getString("photoType");

                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType, getPostCreator(newUserID));
                posts.add(post);
            }

            if (posts.size() == 0) {
                postContainer.setPosts(null);
                postContainer.setHasMorePosts(false);
            } else if (posts.size() > 10) {
                posts.remove(10);
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(true);
            } else {
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(false);
            }

        } catch (SQLException e) {
            System.out.println("Could not load posts for news feed" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postContainer;
    }


    @Override
    public PostContainer LoadMorePostsForNewsFeed(int userId, int postId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Post post = null;
        List<Post> posts = new ArrayList<>();
        PostContainer postContainer = new PostContainer();

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");

            statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Post\"\n" +
                    "INNER JOIN \"Routeourama\".\"Follow\"  on \"Follow\".placeid = \"Post\".placeid\n" +
                    "WHERE \"Follow\".userid = ? AND \"Post\".postid < ?\n" +
                    "ORDER BY \"postid\" DESC\n" +
                    "LIMIT 11;");
            statement.setInt(1, userId);
            statement.setInt(2, postId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int newPostID = resultSet.getInt("postid");
                String newPostTitle = resultSet.getString("title");
                String newPostContent = resultSet.getString("content");
                byte[] newPostPhoto = resultSet.getBytes("photo");
                int newPostLikes = resultSet.getInt("likecount");
                Date newPostDate = resultSet.getDate("dateOfCreation");
                int newPlaceID = resultSet.getInt("placeid");
                int newUserID = resultSet.getInt("userid");
                String photoType = resultSet.getString("photoType");

                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType, getPostCreator(newUserID));
                posts.add(post);
            }

            if (posts.size() == 0) {
                postContainer.setPosts(null);
                postContainer.setHasMorePosts(false);
            } else if (posts.size() > 10) {
                posts.remove(10);
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(true);
            } else {
                postContainer.setPosts(posts);
                postContainer.setHasMorePosts(false);
            }

        } catch (SQLException e) {
            System.out.println("Could not load more posts for news feed" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return postContainer;
    }

    @Override
    public void Comment(Comment comment) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("INSERT INTO \"Comment\" VALUES (?,?,?,?)");
            statement.setInt(1, comment.getUserId());
            statement.setInt(2, comment.getPostId());
            statement.setString(3, comment.getContent());

            Date date = new Date(Calendar.getInstance().getTime().getTime());
            long time = date.getTime();
            Timestamp timestamp = new Timestamp(time);

            statement.setTimestamp(4, timestamp);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Creating comment request failed" + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void DeleteComment(Comment comment) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("DELETE FROM \"Comment\" WHERE \"userid\" = ? AND \"postid\" = ? AND \"timestamp\" = ?");
            statement.setInt(1, comment.getUserId());
            statement.setInt(2, comment.getPostId());
            statement.setTimestamp(3, comment.getTimestamp());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Deleting comment request failed" + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public CommentContainer GetCommentForPost(int postId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Comment comment;
        List<Comment> list = new ArrayList<>();
        CommentContainer container = new CommentContainer(new ArrayList<>(), false);

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Comment\"\n" +
                    "WHERE postid = ?\n" +
                    "ORDER BY \"timestamp\" DESC\n" +
                    "LIMIT 6");
            statement.setInt(1, postId);
            resultSet = statement.executeQuery();

            while(resultSet.next()){
                int userId = resultSet.getInt("userid");
                int postId1 = resultSet.getInt("postid");
                String displayName = getPostCreator(userId);
                String content = resultSet.getString("content");
                Timestamp date = resultSet.getTimestamp("timestamp");
                Comment comment1 = new Comment(userId, displayName, postId1, content, date);
                list.add(comment1);
            }
            List<Comment> first5 = new ArrayList<>();
            if (list.size() == 6) {
                list.remove(5);
                container.setHasMoreComments(true);
            }
            if(list.size() > 0) {
                first5.addAll(list);
                container.setComments(first5);
            }
            return container;

        } catch (SQLException e) {
            System.out.println("Fetching comments request failed" + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public CommentContainer LoadMoreComments(int postId, Comment lastComment) {
        Connection connection = null;
        PreparedStatement statement = null;
        Comment comment;
        List<Comment> list = new ArrayList<>();
        CommentContainer container = new CommentContainer(new ArrayList<>(), false);

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT * FROM \"Routeourama\".\"Comment\" \n" +
                    "WHERE postid = ?\n " +
                    "AND timestamp < ?\n" +
                    "ORDER BY \"timestamp\" DESC\n" +
                    "LIMIT 6");
            statement.setInt(1, postId);
            statement.setTimestamp(2, lastComment.getTimestamp());

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                int userId = resultSet.getInt("userid");
                int postId1 = resultSet.getInt("postid");
                String displayName = getPostCreator(userId);
                String content = resultSet.getString("content");
                Timestamp date = resultSet.getTimestamp("timestamp");
                Comment comment1 = new Comment(userId, displayName, postId1, content, date);
                list.add(comment1);
            }
            List<Comment> first5 = new ArrayList<>();
            if (list.size() == 6) {
                list.remove(5);
                container.setHasMoreComments(true);
            }
            if(list.size() > 0) {
                first5.addAll(list);
                container.setComments(first5);
            }
            return container;

        } catch (SQLException e) {
            System.out.println("Fetching more comments request failed" + e.getMessage());
        } finally {
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int GetCommentCount(int postId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = databaseConnection.getConnection();
            connection.setSchema("Routeourama");
            statement = connection.prepareStatement("SELECT COUNT(\"Routeourama\".\"Comment\".postid)\n" +
                    "FROM \"Routeourama\".\"Comment\"\n" +
                    "WHERE postid = ?");
            statement.setInt(1, postId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int commentCount = resultSet.getInt("count");
                return commentCount;
                }
        } catch (SQLException e) {
            System.out.println("Could not find specified post " + e.getMessage());
        } finally {
            if (resultSet != null) try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (statement != null) try {
                statement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (connection != null) try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}
