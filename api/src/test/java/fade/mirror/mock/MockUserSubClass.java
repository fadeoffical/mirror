package fade.mirror.mock;

public class MockUserSubClass extends MockUser {
	@MockAnnotation
	private final String role;
	private int score;

	public MockUserSubClass(String username, String email, String role) {
		super(username, email);
		this.role = role;
	}

	public MockUserSubClass() {
		this("username", "email", "role");
	}

	public String getRole() {
		return this.role;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
