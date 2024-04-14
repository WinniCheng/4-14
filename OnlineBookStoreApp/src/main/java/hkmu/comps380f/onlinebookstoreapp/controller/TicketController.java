package hkmu.comps380f.onlinebookstoreapp.controller;

import hkmu.comps380f.onlinebookstoreapp.dao.TicketService;
import hkmu.comps380f.onlinebookstoreapp.exception.TicketNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.Ticket;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/ticket")
public class TicketController {
    @Resource
    private TicketService tService;

    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("ticketDatabase", tService.getTickets());
        return "comment/comment";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("comment/add", "ticketForm", new Form());
    }

    public static class Form {
        private String subject;
        private String body;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {

        long ticketId = tService.createTicket(principal.getName(),
                form.getSubject(), form.getBody());
        return new RedirectView("/ticket/view/" + ticketId, true);
    }

    @GetMapping("/view/{ticketId}")
    public String view(@PathVariable("ticketId") long ticketId,
                       ModelMap model)
            throws TicketNotFound {
        Ticket ticket = tService.getTicket(ticketId);
        model.addAttribute("ticketId", ticketId);
        model.addAttribute("ticket", ticket);
        return "comment/view";
    }


    @GetMapping("/delete/{ticketId}")
    public String deleteTicket(@PathVariable("ticketId") long ticketId)
            throws TicketNotFound {
        tService.delete(ticketId);
        return "redirect:/ticket/list";
    }


    @ExceptionHandler({TicketNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}