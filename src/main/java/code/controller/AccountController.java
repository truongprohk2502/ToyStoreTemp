package code.controller;

import code.model.Account;
import code.validation.AccountValidator;
import code.session.OrderSession;
import code.model.Password;
import code.service.AccountService;
import code.validation.PasswordValidator;
import code.validation.UpdateAccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@SessionAttributes("cart")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private UpdateAccountValidator updateAccountValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @ModelAttribute("cart")
    public OrderSession orderSession() {
        return new OrderSession();
    }

    @GetMapping("/login-form")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("account", new Account());
        if (error != null) {
            modelAndView.addObject("message", "Username or password is incorrect");
        }
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("/profile");
        Account account = accountService.findAccountByUsername(principal.getName());
        modelAndView.addObject("account", account);
        Password password = new Password();
        password.setOldPassword(account.getPassword());
        modelAndView.addObject("password", password);
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView updateProfile(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/profile");
        updateAccountValidator.validate(account, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        accountService.update(account);
        modelAndView.addObject("account", account);
        Password password = new Password();
        password.setOldPassword(account.getPassword());
        modelAndView.addObject("password", password);
        modelAndView.addObject("message", "Update profile information successful!");
        return modelAndView;
    }

    @GetMapping("/change-password/{id}")
    public ModelAndView changePasswordForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/change-password");
        Password password = new Password();
        password.setId(id);
        modelAndView.addObject("password", password);
        return modelAndView;
    }

    @PostMapping("/change-password")
    public ModelAndView changePassword(@Valid @ModelAttribute("password") Password password, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/change-password");
        password.setOldPassword(accountService.getPasswordById(password.getId()));
        passwordValidator.validate(password, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return modelAndView;
        }
        accountService.updatePassword(password.getId(), password.getNewPassword());
        modelAndView.addObject("message", "Changed password successful!");
        modelAndView.addObject("password", password);
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView registerForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("account", new Account());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult) {
        accountValidator.validate(account, bindingResult);
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("register");
        }
        account.setRole("ROLE_USER");
        accountService.save(account);
        return new ModelAndView("redirect: login-form");

    }

    @GetMapping("/denied")
    public String denied() {
        return "denied";
    }
}
