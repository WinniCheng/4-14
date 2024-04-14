package hkmu.comps380f.onlinebookstoreapp.exception;

public class TicketNotFound extends Exception {
    public TicketNotFound(long id) {
        super("Ticket " + id + " does not exist.");
    }
}