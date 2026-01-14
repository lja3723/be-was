package app.business;

import app.model.User;
import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 인터페이스
 */
public interface UserBusiness {

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param userId   사용자 ID
     * @param password 비밀번호
     * @param name     이름
     * @param email    이메일
     * @throws app.exception.BadRequestException 사용자 ID가 이미 존재하는 경우 발생
     */
    void register(String userId, String password, String name, String email);

    /**
     * 사용자 로그인 처리
     *
     * @param userId   사용자 ID
     * @param password 비밀번호
     * @return 로그인 성공 시 세션 토큰 반환, 실패 시 null 반환
     */
    LoginResult login(String userId, String password);

    /**
     * 사용자 ID로 사용자를 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 사용자 정보, 존재하지 않으면 빈 Optional 반환
     */
    Optional<User> findById(String userId);
}
