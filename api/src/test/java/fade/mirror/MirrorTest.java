package fade.mirror;

import fade.mirror.filter.Filter;
import fade.mirror.mock.MockAnnotation;
import fade.mirror.mock.MockClass;
import fade.mirror.mock.MockUser;
import fade.mirror.mock.MockUserSubClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
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
        int expected = 7;
        MClass<MockClass> mockClass = mirror(MockClass.class);
        assertEquals(expected, mockClass.getMethodCount(), "'mockClass.getMethodCount()' should return '" + expected + "'");
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
        Optional<MMethod<Void>> method = mirror(MockClass.class).getMethod(Filter.forMethods()
                .withName("mockMethodStatic")
                .ofType(void.class));

        assertTrue(method.isPresent(), "'method' should be present");
        method.get().makeAccessible() // this is so intellij shuts the fuck up about the method being unused
                .invokeWithNoInstance();
    }

    @Test
    @DisplayName("invoke method with parameters")
    void testInvokeMethodWithParameters() {
        Optional<MMethod<Void>> method = mirror(MockClass.class).getMethod(Filter.forMethods()
                .withName("mockMethod")
                .withNoParameters()
                .ofType(void.class));

        assertTrue(method.isPresent(), "'method' should be present");

        MockClass instance = new MockClass();
        method.get().invokeWithInstance(instance);
    }

    @Test
    @DisplayName("Filter for annotation does not include elements without annotations -- Methods")
    void testNonAnnotatedMethods() {
        List<? extends MMethod<?>> userMethods = mirror(MockUser.class).getMethods(Filter.forMethods()
                .withAnnotation(MockAnnotation.class)).toList();

        assertTrue(userMethods.isEmpty(), "'userMethods' should not include any MMethods");

        List<? extends MMethod<?>> mockClassMethods = mirror(MockClass.class).getMethods(Filter.forMethods()
                .withAnnotation(MockAnnotation.class)).toList();

        assertFalse(mockClassMethods.isEmpty(), "'mockClassMethods' should include MMethods");
    }

    @Test
    @DisplayName("Filter for annotation does not include elements without annotations -- Constructors")
    void testNonAnnotatedConstructors() {
        List<? extends MConstructor<?>> userConstructors = mirror(MockUser.class).getConstructors(Filter.forConstructors()
                .withAnnotation(MockAnnotation.class)::test).toList();

        assertTrue(userConstructors.isEmpty(), "'userConstructors' should not include any MConstructors");

        List<? extends MConstructor<?>> mockClassConstructors = mirror(MockClass.class).getConstructors(Filter.forConstructors()
                .withAnnotation(MockAnnotation.class)::test).toList();

        assertFalse(mockClassConstructors.isEmpty(), "'mockClassConstructors' should include MConstructors");
    }

    @Test
    @DisplayName("Filter for annotation does not include elements without annotations -- Fields")
    void testNonAnnotatedFields() {
        List<? extends MField<?>> userFields = mirror(MockUser.class).getFields(Filter.forFields()
                .withAnnotation(MockAnnotation.class)).toList();

        assertTrue(userFields.isEmpty(), "'userFields' should not include any MFields");

        List<? extends MField<?>> mockClassFields = mirror(MockUserSubClass.class).getFields(Filter.forFields()
                .withAnnotation(MockAnnotation.class)).toList();

        assertFalse(mockClassFields.isEmpty(), "'mockClassFields' should include MFields");
    }

    @Test
    @DisplayName("Filter for annotation does not include elements without annotations -- Parameters")
    void testNonAnnotatedParameters() {
        List<? extends MParameter<?>> userParameters = mirror(MockUser.class).getMethods(Filter.forMethods()
                        .withName("getUsername"))
                .flatMap(MMethod::getParameters)
                .filter(Filter.forParameters().withAnnotation(MockAnnotation.class))
                .toList();

        assertTrue(userParameters.isEmpty(), "'userParameters' should be empty");

        List<? extends MParameter<?>> mockClassParameters = mirror(MockClass.class)
                .getMethods(Filter.forMethods()
                        .withName("mockMethod")
                        .withParameters(List.of(int.class, String.class)))
                .flatMap(MMethod::getParameters)
                .toList();

        mockClassParameters = mockClassParameters.stream()
                .filter(Filter.forParameters().withAnnotation(MockAnnotation.class))
                .toList();

        assertFalse(mockClassParameters.isEmpty(), "'mockClassParameters' shouldn't be empty");
    }

    @Test
    @DisplayName("allow subclasses of restricted types to be used")
    void testWithParametersAllowsSubclasses() {
        mirror(MockClass.class).getMethod(Filter.forMethods()
                .withName("mockMethodWithClassParameter")
                .withParameter(MockUser.class)).ifPresent(m -> m.invokeWithNoInstance(new MockUserSubClass()));
    }
}
