package hkmu.comps380f.onlinebookstoreapp.dao;

import hkmu.comps380f.onlinebookstoreapp.exception.TicketNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.Ticket;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
public class TicketService {
    @Resource
    private TicketRepository tRepo;

    @Transactional
    public List<Ticket> getTickets() {
        return tRepo.findAll();
    }

    @Transactional
    public Ticket getTicket(long id)
            throws TicketNotFound {
        Ticket ticket = tRepo.findById(id).orElse(null);
        if (ticket == null) {
            throw new TicketNotFound(id);
        }
        return ticket;
    }



    @Transactional(rollbackFor = TicketNotFound.class)
    public void delete(long id) throws TicketNotFound {
        Ticket deletedTicket = tRepo.findById(id).orElse(null);
        if (deletedTicket == null) {
            throw new TicketNotFound(id);
        }
        tRepo.delete(deletedTicket);
    }


    @Transactional
    public long createTicket(String customerName, String subject,
                             String body)
            throws IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(customerName);
        ticket.setSubject(subject);
        ticket.setBody(body);

        Ticket savedTicket = tRepo.save(ticket);
        return savedTicket.getId();
    }

    @Transactional(rollbackFor = TicketNotFound.class)
    public void updateTicket(long id, String subject,
                             String body)
            throws IOException, TicketNotFound {
        Ticket updatedTicket = tRepo.findById(id).orElse(null);
        if (updatedTicket == null) {
            throw new TicketNotFound(id);
        }
        updatedTicket.setSubject(subject);
        updatedTicket.setBody(body);

        tRepo.save(updatedTicket);
    }
}