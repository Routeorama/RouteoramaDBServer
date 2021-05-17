package com.example.routeoramaserver.dao.places.posts;

import com.example.routeoramaserver.db.DatabaseConnection;
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
            statement = connection.prepareStatement("INSERT INTO \"Post\" (\"title\", \"content\", \"photo\", \"dateOfCreation\", \"placeid\", \"userid\", \"photoType\")" +
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
                System.out.println("Created post successfully");
                newPost = GetPost(post.getTitle());

                addTagsToSpecificPost(newPost,addTagsFromContent(tags));

                return newPost;
            } else {
                System.out.println("Post creation failed");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Post already exists" + e.getMessage());
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

                if (m == 1) {
                    System.out.println("Created PostTag successfully -> " + tagid + ", " + post.getPostId());
                } else {
                    System.out.println("PostTag creation failed -> " + tagid + ", " + post.getPostId());
                }
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
                    if(id > 0) {
                        idsOfInsertedTags.add(id);
                        System.out.println("Inserted tag ID - " + id); // display inserted record
                    }
                    else System.out.println("Tag creation failed -> " + tag);
                }
                /*
                int m = statement.executeUpdate();

                if (m == 1) {
                    System.out.println("Created Tag successfully -> " + tag);
                } else {
                    System.out.println("Tag creation failed -> " + tag);
                }*/
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
            System.out.println("Post deleted.");
            return true;
        } catch (SQLException e) {
            System.out.println("Failed to delete the post " + e.getMessage());
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
                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType);
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
                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType);
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

                post = new Post(newUserID, newPostID, newPostTitle, newPostContent, newPostPhoto, newPostLikes, newPostDate, newPlaceID, photoType);
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

                int affectedRows = statement.executeUpdate();

                if (affectedRows == 0) {
                    System.out.println("Creating like request failed");
                } else
                    System.out.println("Like request successfully executed");

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

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("unlike request failed");
            } else
                System.out.println("Unlike request successfully executed");

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
                    System.out.println("User liked the post already");
                    return true;
                } else {
                    System.out.println("User did not like the post");
                    return false;
                }
            } else {
                System.out.println("Result set was null");
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
}
