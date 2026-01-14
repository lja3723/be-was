package app.handler.view;

import app.exception.ResourceNotFoundException;
import app.handler.ApplicationHandler;
import java.util.Map;
import webserver.http.ContentType;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.http.HttpStatus;
import webserver.http.HttpVersion;
import webserver.http.header.HttpResponseHeader;
import webserver.util.FileExtensionExtractor;
import webserver.util.PlaceholderResolver;
import webserver.util.StaticResourceLoader;

/**
 * 뷰(View) 관련 요청을 처리하는 핸들러의 추상 클래스
 * 본 핸들러는 PlaceHolder가 존재하는 정적 리소스를 로드하고, 적절하게 치환을 시도한 후 응답을 생성합니다.
 */
public abstract class ViewHandler extends ApplicationHandler {

    public ViewHandler(String path) {
        super(HttpMethod.GET, path);
    }

    @Override
    public final HttpResponse handleResponse(HttpRequest httpRequest) {
        HttpResponse preResponse = preHandle(httpRequest);
        if (preResponse != null) {
            return preResponse;
        }

        byte[] body = StaticResourceLoader.loadResource(endpoint.url());
        if (body == null) {
            throw new ResourceNotFoundException("Resource not found: " + endpoint.url());
        }

        byte[] replacedBody = PlaceholderResolver.resolve(body, getTemplateValues(httpRequest));
        return getHttpResponse(replacedBody);
    }

    /**
     * 본격적인 핸들링 전 사전 조건을 검사할 수 있는 메서드. 하위 클래스에서 오버라이딩 가능
     *
     * @param httpRequest 처리할 HttpRequest 객체
     * @return 사전 조건에 부합할 때 응답하는 HttpResponse 객체, 사전 조건이 부합하지 않을 때는 null 반환
     */
    protected HttpResponse preHandle(HttpRequest httpRequest) {
        return null;
    }

    /**
     * 주어진 바디를 사용하여 HttpResponse 객체를 생성한다. 하위 클래스에서 오버라이딩 가능
     *
     * @param body 응답 바디
     * @return 생성된 HttpResponse 객체
     */
    protected HttpResponse getHttpResponse(byte[] body) {
        return new HttpResponse(
            HttpResponseHeader.builder()
                .version(HttpVersion.HTTP_1_1)
                .status(HttpStatus.OK)
                .contentType(ContentType.fromFileExtension(
                    FileExtensionExtractor.get(StaticResourceLoader.getStaticResourcePath(endpoint.url()))))
                .body(body)
                .build(),
            body);
    }

    /**
     * 서브 클래스에서 구현해야 하는 메서드로, 템플릿 치환에 사용할 키-값 맵을 반환합니다.
     *
     * @return 템플릿 치환에 사용할 키-값 맵
     */
    protected abstract Map<String, Object> getTemplateValues(HttpRequest httpRequest);
}
