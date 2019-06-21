package code.controller;

import code.Validator.UserValidator;
import code.model.User;
import code.service.SecurityService;
import code.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        String username = securityService.findLoggedInUsername();
        if (username == null){
            modelAndView.setViewName("user/registration");
            modelAndView.addObject("userForm", new User());
        }else {
            modelAndView.setViewName("index");
            modelAndView.addObject("user",username);
        }
        return modelAndView;
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/home";
    }

    @GetMapping("/login")
    public ModelAndView login(String error) {
        ModelAndView modelAndView = new ModelAndView();
        String username = securityService.findLoggedInUsername();
        if (username == null){
            modelAndView.setViewName("user/login");
            modelAndView.addObject("userForm", new User());
            if (error != null)
                modelAndView.addObject("error", "Your username and password is invalid.");
        }else {
            modelAndView.setViewName("index");
            modelAndView.addObject("user",username);
        }

        return modelAndView;
    }

    @GetMapping("/my-account")
    public ModelAndView viewAccount(){
        ModelAndView modelAndView = new ModelAndView("user/view");
        return modelAndView;
    }


}
