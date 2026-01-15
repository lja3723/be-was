package app.db.adapter;

import app.db.CommentDatabase;
import app.model.Comment;
import java.util.Collection;
import java.util.Optional;

public class CommentDatabaseAdapter implements DatabaseAdapter<Integer, Comment> {

    @Override
    public void add(Comment comment) {
        CommentDatabase.addComment(comment);
    }

    @Override
    public Optional<Comment> findById(Integer id) {
        return Optional.ofNullable(CommentDatabase.findCommentById(id));
    }

    @Override
    public Collection<Comment> findAll() {
        return CommentDatabase.findAll();
    }
}
