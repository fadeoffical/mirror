package fade.mirror.filter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MethodFilterTest {

    @Test
    @DisplayName("Filter must not be null")
    void testMethodFilter() {
        MethodFilter<?> filter = Filter.forMethods();
        assertNotNull(filter, "'filter' is null");
    }

    /////////////////////////////////// copy() ///////////////////////////////////

    @Test
    @DisplayName("Filter copy must not be null")
    void testMethodFilterCopy() {
        MethodFilter<?> filter = Filter.forMethods();
        MethodFilter<?> copy = filter.copy();
        assertNotNull(copy, "'copy' is null");
    }

    @Test
    @DisplayName("Filter copy must be different instance")
    void testMethodFilterCopyIsDifferentInstance() {
        Filter<?> filter = Filter.forMethods();
        Filter<?> copy = filter.copy();
        assertNotSame(filter, copy, "'copy' is same instance as 'filter'");
    }

    @Test
    @DisplayName("Filter copy must be equal")
    void testMethodFilterCopyIsEqual() {
        Filter<?> filter = Filter.forMethods();
        Filter<?> copy = filter.copy();
        assertEquals(filter, copy, "'copy' is not equal to 'filter'");
    }

    /////////////////////////////////// ofType() ///////////////////////////////////

    @Test
    @DisplayName("Filter ofType must not be null")
    void testMethodFilterOfType() {
        Filter<?> filter = Filter.forMethods();
        Filter<?> ofType = filter.ofType(Object.class);
        assertNotNull(ofType, "'ofType' is null");
    }

}
