package app.business.impl;

import app.business.ArticleBusiness;
import app.db.adapter.DatabaseAdapter;
import app.model.Article;
import java.util.Comparator;
import java.util.Optional;

public class ArticleBusinessImpl implements ArticleBusiness {

    private final DatabaseAdapter<Integer, Article> articleDatabaseAdapter;

    public ArticleBusinessImpl(
        DatabaseAdapter<Integer, Article> articleDatabaseAdapter
    ) {
        this.articleDatabaseAdapter = articleDatabaseAdapter;
    }

    @Override
    public Optional<Article> getNewestArticle() {
        return articleDatabaseAdapter.findAll().stream()
            .max(Comparator.comparingInt(Article::id));
    }
}
