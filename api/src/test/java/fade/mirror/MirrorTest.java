package fade.mirror;

import fade.mirror.mock.MockUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static fade.mirror.Mirror.mirror;

class MirrorTest {

    @Test
    @DisplayName("instantiate user with username and email")
    void testAccessDefaultConstructorAndInstantiate() {
        MockUser user = mirror(MockUser.class)
                .getConstructor(ConstructorPredicate.withTypes(String.class, String.class))
                .orElseThrow()
                .requireAccessible()
                .invoke("bob", "bob@example.com");

        assertNotNull(user, "'user' is null");
        assertEquals("bob", user.getUsername(), "'username' did not match");
        assertEquals("bob@example.com", user.getEmail(), "'email' did not match");
    }
}
