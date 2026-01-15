package app.db;

import app.model.Comment;
import java.util.HashMap;
import java.util.Map;

public class CommentDatabase {

    private static final Map<Integer, Comment> comments = new HashMap<>();

    public static void addComment(Comment comment) {
        comments.put(comment.id(), comment);
    }

    public static Comment findCommentById(Integer id) {
        return comments.get(id);
    }

    public static Map<Integer, Comment> findAll() {
        return comments;
    }
}
