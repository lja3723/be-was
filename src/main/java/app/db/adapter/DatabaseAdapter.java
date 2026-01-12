package app.db.adapter;

import java.util.Collection;
import java.util.Optional;

public interface DatabaseAdapter<T, Key> {

    /**
     * 새로운 사용자를 데이터베이스에 추가
     *
     * @param t 추가할 객체
     */
    void add(T t);

    /**
     * userId로 {}
     * @param key 객체를 검색할 key
     * @return 검색된 객체
     */
    Optional<T> findById(Key key);

    /**
     * 데이터베이스에 저장된 모든 사용자 반환
     *
     * @return 모든 객체의 컬렉션
     */
    Collection<T> findAll();
}
