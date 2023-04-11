package fade.mirror;

import fade.mirror.filter.Filter;
import fade.mirror.mock.MockUser;
import fade.mirror.mock.MockUserSubClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        MockUser user = mirror(MockUser.class)
                .getConstructor(Filter.forConstructors().withParameters(List.of(String.class, String.class))::test)
                .orElseThrow()
                .requireAccessible()
                .invokeWithNoInstance("bob", "bob@example.com");

        assertNotNull(user, "'user' is null");
        assertEquals("bob", user.getUsername(), "'username' did not match");
        assertEquals("bob@example.com", user.getEmail(), "'email' did not match");
    }

    @Test
    @DisplayName("access package-private field")
    void testAccessPackagePrivateField() {
        MockUser user = new MockUser("bob", "bob@example.com");

        MField<String> field = mirror(MockUser.class).getField(Filter.forFields()
                        .ofType(String.class)
                        .withName("username"))
                .orElseThrow()
                .requireAccessible(user);

        assertNotNull(field, "'field' is null");

        Optional<String> value = field.getValue(user);
        assertTrue(value.isPresent(), "'value' is absent");
        assertEquals("bob", value.get(), "'username' did not match");
    }

    @Test
    @DisplayName("access inherited fields")
    void testInheritedFields() {
        Optional<MField<String>> roleField = mirror(MockUserSubClass.class)
                .getField(Filter.forFields().withName("role").ofType(String.class), MClass.IncludeSuperclasses.Yes);

        Optional<MField<String>> usernameField = mirror(MockUserSubClass.class)
                .getField(Filter.forFields().withName("username").ofType(String.class), MClass.IncludeSuperclasses.Yes);

        assertTrue(roleField.isPresent(), "'roleField' is absent"); // test own field
        assertTrue(usernameField.isPresent(), "'usernameField' is absent"); // test inherited field
    }
}
