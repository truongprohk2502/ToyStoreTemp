package code.controller;

import code.model.Account;
import code.model.Ordered;
import code.service.AccountService;
import code.service.OrderedService;
import code.session.OrderSession;
import code.service.ToyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Controller
@SessionAttributes("cart")
public class OrderController {

    @Autowired
    private ToyService toyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderedService orderedService;

    @ModelAttribute("cart")
    public OrderSession orderSession() {
        return new OrderSession();
    }

    @GetMapping("/cart")
    public ModelAndView cart() {
        ModelAndView modelAndView = new ModelAndView("cart");
        return modelAndView;
    }

    @GetMapping("/add-to-cart/{id}")
    public ModelAndView addToCart(@PathVariable Long id, @RequestParam("qty") String qty, @ModelAttribute("cart") OrderSession orderSession, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("cart");
        Ordered order = new Ordered();
        Date date = new Date(System.currentTimeMillis());
        order.setOrderDate(date);
        order.setQuantity(Long.parseLong(qty));
        order.setToy(toyService.findById(id));
        if (principal != null){
            order.setAccount(accountService.findAccountByUsername(principal.getName()));
        }
        orderSession.add(order);
        return modelAndView;
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("removes") String[] removes, @ModelAttribute("cart") OrderSession orderSession) {
        for (String i : removes) {
            orderSession.removeOrder(Long.parseLong(i));
        }
        return "redirect: cart";
    }

    @PostMapping("/checkout")
    public ModelAndView checkout(@RequestParam(value = "cQty", required = false) String[] cQty, @RequestParam("paymentType") String paymentType, @ModelAttribute("cart") OrderSession orderSession, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("cart");
        if (cQty == null) {
            modelAndView.addObject("message", "You have no any orders in your cart!");
            return modelAndView;
        }
        int i = 0;
        Account account = accountService.findAccountByUsername(principal.getName());

        for (Ordered order : orderSession.getOrders()) {
            order.setQuantity(Long.parseLong(cQty[i]));
            i++;
            order.setAccount(account);
        }

        ModelAndView payment = new ModelAndView("payment");
        payment.addObject("account", account);
        return payment;
    }

    @PostMapping("/ordered")
    public ModelAndView ordered(@ModelAttribute("cart") OrderSession orderSession) {
        ModelAndView modelAndView = new ModelAndView("ordered");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Long currentTime = System.currentTimeMillis();
        Long deliveryTime1 = currentTime + 3 * 24 * 60 * 60 * 1000;
        Long deliveryTime2 = currentTime + 5 * 24 * 60 * 60 * 1000;
        String date1 = format.format(new Date(deliveryTime1));
        String date2 = format.format(new Date(deliveryTime2));
        String message = "Thank you for buying toys in ToyStore.com!</br>Your order will be delivery on from " + date1 + " to " + date2 + "</br><a href='/home'>Return to Homepage</a>";
        modelAndView.addObject("message", message);
        for (Ordered order : orderSession.getOrders()) {
            order.setStatus("DELIVERING");
            orderedService.save(order);
        }
        orderSession.setOrders(new ArrayList<>());
        return modelAndView;
    }
}
