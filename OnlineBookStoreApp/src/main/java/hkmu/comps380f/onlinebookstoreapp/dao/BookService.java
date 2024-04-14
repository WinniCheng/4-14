package hkmu.comps380f.onlinebookstoreapp.dao;

import hkmu.comps380f.onlinebookstoreapp.exception.AttachmentNotFound;
import hkmu.comps380f.onlinebookstoreapp.exception.BookNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.Attachment;
import hkmu.comps380f.onlinebookstoreapp.model.Book;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    @Resource
    private BookRepository bRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Transactional
    public List<Book> getBooks() {
        return bRepo.findAll();
    }

    @Transactional
    public Book getBook(long id)
            throws BookNotFound {
        Book book = bRepo.findById(id).orElse(null);
        if (book == null) {
            throw new BookNotFound(id);
        }
        return book;
    }

    @Transactional
    public Attachment getAttachment(long bookId, UUID attachmentId)
            throws BookNotFound, AttachmentNotFound {
        Book book = bRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        Attachment attachment = aRepo.findById(attachmentId).orElse(null);
        if (attachment == null) {
            throw new AttachmentNotFound(attachmentId);
        }
        return attachment;
    }

    @Transactional(rollbackFor = BookNotFound.class)
    public void delete(long id) throws BookNotFound {
        Book deletedBook = bRepo.findById(id).orElse(null);
        if (deletedBook == null) {
            throw new BookNotFound(id);
        }
        bRepo.delete(deletedBook);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long bookId, UUID attachmentId)
            throws BookNotFound, AttachmentNotFound {
        Book book = bRepo.findById(bookId).orElse(null);
        if (book == null) {
            throw new BookNotFound(bookId);
        }
        for (Attachment attachment : book.getAttachments()) {
            if (attachment.getId().equals(attachmentId)) {
                book.deleteAttachment(attachment);
                bRepo.save(book);
                return;
            }
        }
        throw new AttachmentNotFound(attachmentId);
    }

    @Transactional
    public long createBook(String bookName, String author, float price,
                             String description, boolean availability, List<MultipartFile> attachments)
            throws IOException {
        Book book = new Book();
        book.setBookName(bookName);
        book.setAuthor(author);
        book.setPrice(price);
        book.setDescription(description);
        book.setAvailability(availability);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBook(book);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                book.getAttachments().add(attachment);
            }
        }
        Book savedBook = bRepo.save(book);
        return savedBook.getId();
    }

    @Transactional(rollbackFor = BookNotFound.class)
    public void updateBook(long id, String bookName, String author, float price,
                           String description, boolean availability, List<MultipartFile> attachments)
            throws IOException, BookNotFound {
        Book updatedBook = bRepo.findById(id).orElse(null);
        if (updatedBook == null) {
            throw new BookNotFound(id);
        }
        updatedBook.setBookName(bookName);
        updatedBook.setAuthor(author);
        updatedBook.setPrice(price);
        updatedBook.setDescription(description);
        updatedBook.setAvailability(availability);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setBook(updatedBook);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedBook.getAttachments().add(attachment);
            }
        }
        bRepo.save(updatedBook);
    }
}
