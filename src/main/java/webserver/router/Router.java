package webserver.router;

/**
 * 입력 타입 T에 따라 출력 타입 V를 라우팅하는 Router 인터페이스
 *
 * @param <T> 라우팅의 입력 타입
 * @param <V> 라우팅의 출력 타입
 */
public interface Router<T, V> {

    V route(T t);
}
