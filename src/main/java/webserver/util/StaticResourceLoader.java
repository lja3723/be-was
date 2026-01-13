package webserver.util;

import app.exception.InternalServerErrorException;
import app.exception.ResourceNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 정적 리소스를 로드하는 유틸리티 클래스
 */
public class StaticResourceLoader {

    /**
     * 주어진 경로에 해당하는 정적 리소스를 바이트 배열로 로드한다.
     *
     * @param path 리소스의 경로
     * @return 리소스의 바이트 배열
     * @throws ResourceNotFoundException    리소스를 찾을 수 없는 경우
     * @throws InternalServerErrorException 입출력 오류가 발생한 경우
     */
    public static byte[] loadResource(String path) {

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (FileExtensionExtractor.get(path) == null) {
            path += "/index.html";
        }

        try (InputStream is = StaticResourceLoader.class.getClassLoader().getResourceAsStream(path)) {
            // 요청에 맞는 리소스가 존재하지 않음
            if (is == null) {
                throw new ResourceNotFoundException("Resource not found: " + path);
            }
            return is.readAllBytes();

        } catch (IOException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }
}
