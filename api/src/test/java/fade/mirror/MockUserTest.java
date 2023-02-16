package fade.mirror;

import fade.mirror.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static fade.mirror.Mirror.mirror;
import static org.junit.jupiter.api.Assertions.*;

class MockUserTest {

    @Test
    @DisplayName("get raw class")
    void testGetRawClass() {
        Class<?> clazz = mirror(MockUser.class).getRawClass();

        assertNotNull(clazz, "'clazz' is null");
        assertEquals(MockUser.class, clazz, "'clazz' did not match");
    }

    @Test
    @DisplayName("get constructor")
    void testGetConstructor() {
        Optional<MConstructor<MockUser>> constructor = mirror(MockUser.class).getConstructor();

        assertNotNull(constructor, "'constructor' is null");
        assertTrue(constructor.isPresent(), "'constructor' is absent");
        assertEquals(constructor.get()
                .getDeclaringClass()
                .getRawClass(), MockUser.class, "'constructor class' did not match");
    }

    @Test
    @DisplayName("instantiate user with username and email")
    void testAccessDefaultConstructorAndInstantiate() {
        MockUser user = mirror(MockUser.class).getConstructorWithTypes(String.class, String.class)
                .orElseThrow()
                .requireAccessible()
                .invoke("bob", "bob@example.com");

        assertNotNull(user, "'user' is null");
        assertEquals("bob", user.getUsername(), "'username' did not match");
        assertEquals("bob@example.com", user.getEmail(), "'email' did not match");
    }

    @Test
    @DisplayName("ci test")
    void testCiTest() {

    }
}
