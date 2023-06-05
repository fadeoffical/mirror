package fade.mirror.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstructorFilterTest {

    @Test
    @DisplayName("Filter must not be null")
    void testMethodFilter() {
        Filter<?> filter = Filter.forConstructors();
        assertNotNull(filter, "'filter' is null");
    }

    /////////////////////////////////// copy() ///////////////////////////////////

    @Test
    @DisplayName("Filter copy must not be null")
    void testMethodFilterCopy() {
        Filter<?> filter = Filter.forConstructors();
        Filter<?> copy = filter.copy();
        assertNotNull(copy, "'copy' is null");
    }

    @Test
    @DisplayName("Filter copy must be different instance")
    void testMethodFilterCopyIsDifferentInstance() {
        Filter<?> filter = Filter.forConstructors();
        Filter<?> copy = filter.copy();
        assertNotSame(filter, copy, "'copy' is same instance as 'filter'");
    }

    @Test
    @DisplayName("Filter copy must be equal")
    void testMethodFilterCopyIsEqual() {
        Filter<?> filter = Filter.forConstructors();
        Filter<?> copy = filter.copy();
        assertEquals(filter, copy, "'copy' is not equal to 'filter'");
    }

    /////////////////////////////////// ofType() ///////////////////////////////////

    @Test
    @DisplayName("Filter ofType must not be null")
    void testMethodFilterOfType() {
        ConstructorFilter<?> filter = Filter.forConstructors();
        ConstructorFilter<?> ofType = filter.ofType(Object.class);
        assertNotNull(ofType, "'ofType' is null");
    }

}
