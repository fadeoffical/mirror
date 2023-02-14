package fade.mirror.mock;

public class MockUser {

    private final String username;
    private final String email;

    public MockUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }
}
