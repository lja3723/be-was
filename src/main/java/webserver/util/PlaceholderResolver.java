package webserver.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Byte 배열 기반의 텍스트 문서 템플릿 치환기
 * <p>AI로 작성되었음</p>
 */
public class PlaceholderResolver {

    private static final Logger log = LoggerFactory.getLogger(PlaceholderResolver.class);
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{\\{(.+?)\\}\\}");

    /**
     * @param templateBytes 원본 HTML이 담긴 byte[]
     * @param values 치환할 키-값 맵
     * @return 치환 완료된 HTML의 byte[]
     */
    public static byte[] resolve(byte[] templateBytes, Map<String, Object> values) {

        if (templateBytes == null || templateBytes.length == 0) return null;
        if (values == null) return templateBytes;

        String template = new String(templateBytes, StandardCharsets.UTF_8);
        StringBuilder result = new StringBuilder(templateBytes.length);
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);

        while (matcher.find()) {
            String key = matcher.group(1).trim();
            Object value = values.getOrDefault(key, "");
            if (values.get(key) == null) {
                log.warn("No value found for placeholder key: {}", key);
            }

            matcher.appendReplacement(result, Matcher.quoteReplacement(value.toString()));
        }
        matcher.appendTail(result);

        return result.toString().getBytes(StandardCharsets.UTF_8);
    }
}