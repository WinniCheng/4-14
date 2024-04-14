package hkmu.comps380f.onlinebookstoreapp.controller;

import hkmu.comps380f.onlinebookstoreapp.dao.BookService;
import hkmu.comps380f.onlinebookstoreapp.exception.AttachmentNotFound;
import hkmu.comps380f.onlinebookstoreapp.exception.BookNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.Attachment;
import hkmu.comps380f.onlinebookstoreapp.model.Book;
import hkmu.comps380f.onlinebookstoreapp.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bService;

    // Controller methods, Form-backing object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("bookDatabase", bService.getBooks());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "bookForm", new Form());
    }

    public static class Form {
        private String bookName;
        private String author;
        private float price;
        private String description;
        private boolean availability;
        private List<MultipartFile> attachments;

        // Getters and Setters of customerName, subject, body, attachments
        public String getBookName() {
            return bookName;
        }

        public void setBookName(String bookName) {
            this.bookName = bookName;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public boolean isAvailability() {
            return availability;
        }

        public void setAvailability(boolean availability) {
            this.availability = availability;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/create")
    public View create(Form form) throws IOException {
        long bookId = bService.createBook(form.getBookName(), form.getAuthor(), form.getPrice(),
                form.getDescription(), form.isAvailability(), form.getAttachments());
        return new RedirectView("/book/view/" + bookId, true);
    }

    @GetMapping("/view/{bookId}")
    public String view(@PathVariable("bookId") long bookId,
                       ModelMap model) throws BookNotFound {

        Book book = bService.getBook(bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("book", book);
        return "view";
    }

    @GetMapping("/{bookId}/attachment/{attachment:.+}")
    public View download(@PathVariable("bookId") long bookId,
                         @PathVariable("attachment") UUID attachmentId)
            throws BookNotFound, AttachmentNotFound {
        Attachment attachment = bService.getAttachment(bookId, attachmentId);
        return new DownloadingView(attachment.getName(),
                attachment.getMimeContentType(), attachment.getContents());
    }

    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") long bookId)
            throws BookNotFound {
        bService.delete(bookId);
        return "redirect:/book/list";
    }

    @GetMapping("/{bookId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("bookId") long bookId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws BookNotFound, AttachmentNotFound {
        bService.deleteAttachment(bookId, attachmentId);
        return "redirect:/book/edit/" + bookId;
    }

    @GetMapping("/edit/{bookId}")
    public ModelAndView showEdit(@PathVariable("bookId") long bookId,
                                 Principal principal, HttpServletRequest request)
            throws BookNotFound {
        Book book = bService.getBook(bookId);
        if (book == null
                || (!request.isUserInRole("ROLE_ADMIN"))) {
            return new ModelAndView(new RedirectView("/book/list", true));
        }
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("book", book);
        Form bookForm = new Form();

        bookForm.setBookName(book.getBookName());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setPrice(book.getPrice());
        bookForm.setDescription(book.getDescription());
        bookForm.setAvailability(book.isAvailability());

        modelAndView.addObject("bookForm", bookForm);
        return modelAndView;
    }

    @PostMapping("/edit/{bookId}")
    public String edit(@PathVariable("bookId") long bookId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, BookNotFound {
        Book book = bService.getBook(bookId);
        if (book == null
                || (!request.isUserInRole("ROLE_ADMIN"))) {
            return "redirect:/book/list";
        }

        bService.updateBook(bookId, form.getBookName(),form.getAuthor(), form.getPrice(), form.getDescription(), form.isAvailability(), form.getAttachments());
        return "redirect:/book/view/" + bookId;
    }

    @ExceptionHandler({BookNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}