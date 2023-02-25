package fade.mirror;

import fade.mirror.filter.Filter;
import fade.mirror.mock.MockClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static fade.mirror.Mirror.mirror;
import static org.junit.jupiter.api.Assertions.*;

class MirrorTest {

    @Test
    @DisplayName("mirror of class not null")
    void testMirrorOfClassNotNull() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertNotNull(mockClass, "'mockClass' is null");
    }

    @Test
    @DisplayName("mirror of class has correct raw class")
    void testMirrorOfClassHasCorrectRawClass() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals(MockClass.class, mockClass.getRawClass(), "'mockClass.getRawClass()' should return 'MockClass.class'");
    }

    @Test
    @DisplayName("mirror of class has correct number of constructors")
    void testMirrorOfClassHasCorrectNumberOfConstructors() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals(4, mockClass.getConstructorCount(), "'mockClass.getConstructorCount()' should return '4'");
    }

    @Test
    @DisplayName("mirror of class has correct number of fields")
    void testMirrorOfClassHasCorrectNumberOfFields() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals(0, mockClass.getFieldCount(), "'mockClass.getFieldsCount()' should return '0'");
    }

    @Test
    @DisplayName("mirror of class has fields")
    void testMirrorOfClassHasFields() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertFalse(mockClass.hasFields(), "'mockClass.hasFields()' should return 'false'");
    }

    @Test
    @DisplayName("mirror of class has correct number of methods")
    void testMirrorOfClassHasCorrectNumberOfMethods() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals(6, mockClass.getMethodCount(), "'mockClass.getMethodCount()' should return '6'");
    }

    @Test
    @DisplayName("mirror of class has methods")
    void testMirrorOfClassHasMethods() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertTrue(mockClass.hasMethods(), "'mockClass.hasMethods()' should return 'true'");
    }

    @Test
    @DisplayName("mirror of class has correct name")
    void testMirrorOfClassHasCorrectName() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals("fade.mirror.mock.MockClass", mockClass.getName(), "'mockClass.getName()' should return 'fade.mirror.mock.MockClass'");
    }

    @Test
    @DisplayName("mirror of class has correct simple name")
    void testMirrorOfClassHasCorrectSimpleName() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals("MockClass", mockClass.getSimpleName(), "'mockClass.getSimpleName()' should return 'MockClass'");
    }

    @Test
    @DisplayName("mirror of class has correct canonical name")
    void testMirrorOfClassHasCorrectCanonicalName() {
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals("fade.mirror.mock.MockClass", mockClass.getCanonicalName(), "'mockClass.getCanonicalName()' should return 'fade.mirror.mock.MockClass'");
    }

    @Test
    @DisplayName("invoke static method with no parameters")
    void testInvokeStaticMethodWithNoParameters() {
        Optional<MMethod<Void>> method = mirror(MockClass.class)
                .getMethod(Filter.forMethods().withName("mockMethodStatic").withReturnType(void.class));

        assertTrue(method.isPresent(), "'method' should be present");
        method.get()
                .makeAccessible() // this is so intellij shuts the fuck up about the method being unused
                .invoke();
    }

    @Test
    @DisplayName("invoke method with parameters")
    void testInvokeMethodWithParameters() {
        Optional<MMethod<Void>> method = mirror(MockClass.class)
                .getMethod(Filter.forMethods().withName("mockMethod").withReturnType(void.class));

        assertTrue(method.isPresent(), "'method' should be present");
        MockClass instance = new MockClass();
        method.get()
                .makeAccessible()
                .bindToObject(instance)
                .invoke();
    }
}
