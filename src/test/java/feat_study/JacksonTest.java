package feat_study;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Jackson 라이브러리를 활용한 JSON 직렬화 및 역직렬화 테스트 클래스 (AI로 작성)
 */
class JacksonTest {

    private static class User {
        private String userId;
        private String email;

        // Jackson의 역직렬화를 위해 기본 생성자가 반드시 필요함
        public User() {}

        public User(String userId, String email) {
            this.userId = userId;
            this.email = email;
        }

        // Getter/Setter
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    // Jackson의 핵심 엔진인 ObjectMapper 인스턴스 생성
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("객체를 JSON 문자열로 직렬화하는 기능을 테스트한다.")
    void testSerialization() throws Exception {
        // Given
        User user = new User("dev_user", "test@example.com");

        // When
        String jsonResult = objectMapper.writeValueAsString(user);

        // Then
        // 결과 JSON에 필드명이 포함되어 있는지 확인
        assertTrue(jsonResult.contains("\"userId\":\"dev_user\""));
        assertTrue(jsonResult.contains("\"email\":\"test@example.com\""));
        assertEquals("{\"userId\":\"dev_user\",\"email\":\"test@example.com\"}", jsonResult);
    }

    @Test
    @DisplayName("JSON 문자열을 자바 객체로 역직렬화하는 기능을 테스트한다.")
    void testDeserialization() throws Exception {
        // Given
        String jsonInput = "{\"userId\":\"dev_user\",\"email\":\"test@example.com\"}";

        // When
        User user = objectMapper.readValue(jsonInput, User.class);

        // Then
        assertNotNull(user);
        assertEquals("dev_user", user.getUserId());
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    @DisplayName("제네릭 컬렉션(List)의 역직렬화 기능을 테스트한다.")
    void testCollectionDeserialization() throws Exception {
        // Given
        String jsonList = "[{\"userId\":\"user1\"}, {\"userId\":\"user2\"}]";

        // When: TypeReference를 사용하여 런타임 시 소거되는 제네릭 타입을 보존
        List<User> users = objectMapper.readValue(jsonList, new TypeReference<List<User>>() {});

        // Then
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUserId());
        assertEquals("user2", users.get(1).getUserId());
    }

    @Test
    @DisplayName("중첩된 Map 객체를 JSON 문자열로 변환하고 구조를 검증한다.")
    void testNestedMapSerialization() throws JsonProcessingException {
        // Given: 중첩된 데이터 구조 생성
        Map<String, Object> userProfile = new HashMap<>();
        userProfile.put("username", "tech_lead");
        userProfile.put("isActive", true);

        // 하위 Map 생성 (Address)
        Map<String, Object> address = new HashMap<>();
        address.put("city", "Seoul");
        address.put("zipcode", 12345);
        userProfile.put("address", address);

        // List 포함 (Hobbies)
        userProfile.put("hobbies", List.of("Coding", "Piano", "Reading"));

        // 가독성을 위한 Pretty Printing 설정 (선택 사항)
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        // When: 직렬화 수행
        String jsonResult = objectMapper.writeValueAsString(userProfile);

        // Then: 결과 검증
        System.out.println("Generated Nested JSON:\n" + jsonResult);

        // 1. 최상위 필드 확인
        assertTrue(jsonResult.contains("\"username\" : \"tech_lead\""));
        assertTrue(jsonResult.contains("\"isActive\" : true"));

        // 2. 중첩된(Nested) Map 필드 확인
        assertTrue(jsonResult.contains("\"city\" : \"Seoul\""));
        assertTrue(jsonResult.contains("\"zipcode\" : 12345"));

        // 3. 리스트(Array) 데이터 확인
        assertTrue(jsonResult.contains("\"Coding\""));
        assertTrue(jsonResult.contains("\"Piano\""));
    }
}