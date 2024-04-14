package hkmu.comps380f.onlinebookstoreapp.exception;

public class BookUserNotFound extends Exception {
    public BookUserNotFound(String username) {
        super("User " + username + " does not exist.");
    }
}
