package hkmu.comps380f.onlinebookstoreapp.controller;

import hkmu.comps380f.onlinebookstoreapp.dao.BookService;
import hkmu.comps380f.onlinebookstoreapp.exception.BookNotFound;
import hkmu.comps380f.onlinebookstoreapp.model.Book;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ShoppingCartController {
    private final Map<Integer, String> products = new ConcurrentHashMap<>();
    private final Map<Integer, Float> prices = new ConcurrentHashMap<>();


    @Resource
    private BookService bService;



    @GetMapping("/shop")
    public String shop(HttpServletRequest request, HttpSession session, ModelMap model) {
        String action = request.getParameter("action");

        if (action == null)
            action = "browse";

        switch (action) {
            case "addToCart":
                return addToCart(request, session);
            case "viewCart":
                return viewCart(request);
            case "emptyCart":
                return emptyCart(session);
            case "deleteFromCart":
                return deleteFromCart(request, session);
            case "browse":
            default:
                return browse(request);
        }
    }

    private String addToCart(HttpServletRequest request, HttpSession session) {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (Exception e) {
            return "redirect:/book";
        }

        try {
            Book book = bService.getBook((long)productId);

            if (book != null && !book.isAvailability()) {
                return "bookNotAvailable";
            }
        } catch (BookNotFound e) {
            return "bookNotAvailable";
        }

        if (session.getAttribute("cart") == null)
            session.setAttribute("cart", new ConcurrentHashMap<>());

        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (!cart.containsKey(productId))
            cart.put(productId, 0);
        cart.put(productId, cart.get(productId) + 1);

        return "redirect:/shop?action=viewCart";
    }

    private String viewCart(HttpServletRequest request) {
        List<Book> bookList = bService.getBooks();

        for (Book book : bookList) {
            this.products.put((int) book.getId(), book.getBookName());
            this.prices.put((int) book.getId(), book.getPrice());
        }

        request.setAttribute("products", this.products);
        request.setAttribute("prices", this.prices);
        return "viewCart";
    }

    private String browse(HttpServletRequest request) {
        List<Book> bookList = bService.getBooks();

        for (Book book : bookList) {
            this.products.put((int)book.getId(), book.getBookName());
        }
        request.setAttribute("products", this.products);
        return "browse";
    }


    private String emptyCart(HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/shop?action=viewCart";
    }

    private String deleteFromCart(HttpServletRequest request, HttpSession session) {
        int productId;
        try {
            productId = Integer.parseInt(request.getParameter("productId"));
        } catch (NumberFormatException e) {
            return "redirect:/book";
        }
        @SuppressWarnings("unchecked")
        Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
        if (cart != null) {
            if (cart.containsKey(productId)) {
                int quantity = cart.get(productId);
                if (quantity > 1) {
                    cart.put(productId, quantity - 1);
                } else {
                    cart.remove(productId);
                }
            }
        }
        return "redirect:/shop?action=viewCart";
    }

    @GetMapping("/checkout")
    public String checkout(HttpServletRequest request){
        double totalPrice = 0.0;
        Map<Integer, Integer> cart = (Map<Integer, Integer>) request.getSession().getAttribute("cart");
        List<Book> bookList = bService.getBooks();

        for (Book book : bookList) {
            this.products.put((int) book.getId(), book.getBookName());
            this.prices.put((int) book.getId(), book.getPrice());
        }

        for (Map.Entry<Integer, Integer> cartItem : cart.entrySet()) {
            int productId = cartItem.getKey();
            int quantity = cartItem.getValue();
            Float price = prices.getOrDefault(productId, (float)0.0);
            totalPrice += price * quantity;
        }


        request.setAttribute("products", this.products);
        request.setAttribute("prices", this.prices);
        request.setAttribute("totalPrice", totalPrice);
        return "checkout";
    }

}
