package app.business;

import app.model.Article;
import java.util.Optional;

/**
 * 게시글 관련 비즈니스 로직을 처리하는 인터페이스
 */
public interface ArticleBusiness {

    /**
     * 가장 최신의 게시글을 조회합니다.
     *
     * @return 최신 게시글
     */
    Optional<Article> getNewestArticle();
}
