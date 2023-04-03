package fade.mirror.mock;

public class MockClass {

    public MockClass() {
    }

    public MockClass(int mockInt) {
    }

    @MockAnnotation
    public MockClass(String mockString) {
    }

    public MockClass(int mockInt, String mockString) {
    }

    public static void mockMethodStatic() {
    }

    public void mockMethod() {
    }

    public void mockMethod(int mockInt) {
    }

    @MockAnnotation
    public void mockMethod(String mockString) {
    }

    public void mockMethod(int mockInt, String mockString) {
    }

    public int mockMethodWithReturn() {
        return 0;
    }
}
