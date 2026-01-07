package webserver.http.parser;

/**
 * 일반적인 파서 인터페이스
 * @param <T> 파싱 결과로 반환할 타입
 * @param <V> 값을 읽을 원시 데이터 타입
 */
public interface Parser<T, V> {

    V parse(T rawData);
}
