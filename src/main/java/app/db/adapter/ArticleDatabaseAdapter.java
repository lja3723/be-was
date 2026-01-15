package app.db.adapter;

import app.db.ArticleDatabase;
import app.model.Article;
import java.util.Collection;
import java.util.Optional;

public class ArticleDatabaseAdapter implements DatabaseAdapter<Integer, Article> {

    @Override
    public void add(Article article) {
        ArticleDatabase.addArticle(article);
    }

    @Override
    public Optional<Article> findById(Integer id) {
        return Optional.ofNullable(ArticleDatabase.findArticleById(id));
    }

    @Override
    public Collection<Article> findAll() {
        return ArticleDatabase.findAll();
    }
}
