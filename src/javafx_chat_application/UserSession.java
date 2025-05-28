package javafx_chat_application;

// This defines a public class named UserSession. It is used to
// manage and store the currently logged-in user's session data
//(like their username and ID) while the application is running.
public class UserSession {
	// This is a static variable that holds the single instance of
	// the UserSession class — making this a Singleton Pattern.
	//It ensures there is only one session object at any time in the app.
    private static UserSession instance;
    private String username;
    private int userId;

    // his is a private constructor. It prevents other classes from
    // creating new instances using new UserSession(). Only
    // getInstance() can create the single instance.
    private UserSession() {}

    // This is the accessor method for the Singleton instance. It:
    // Checks if the instance is null (i.e. not yet created),
    // If so, it creates a new UserSession object,
    // Then returns the single UserSession instance.
    // This ensures there's only one active session object.
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // This sets the user info for the session. It stores the username
    // and user ID when a user logs in or registers.
    public void setUser(String username, int userId) {
        this.username = username;
        this.userId = userId;
    }

    // This method logs the user out by clearing their session
    // info — effectively "ending" the session in the app.
    public void clearSession() {
        this.username = null;
        this.userId = -1;
    }

    // This is a getter for the username. It allows other classes
    // to retrieve the current user's name.
    public String getUsername() {
        return username;
    }

    // This is a getter for the user ID. It allows access to the ID
    // used in database operations like saving messages.
    public int getUserId() {
        return userId;
    }

    // This returns true if a user is logged in
    public boolean isLoggedIn() {
        return username != null;
    }
}


