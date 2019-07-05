package code.validation;

import code.model.Account;
import code.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
public class AccountValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Account account = (Account) o;

        ValidationUtils.rejectIfEmpty(errors, "username", "username.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");
        ValidationUtils.rejectIfEmpty(errors, "phone", "phone.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.empty");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "password.confirm");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.empty");
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "dob", "dob.empty");

        String username = account.getUsername();

        if (!"".equals(username)) {

            if (username.length() < 8 || username.length() > 20) {
                errors.rejectValue("username", "username.length");
            }

            if (!username.matches("^[a-zA-Z0-9]*$")) {
                errors.rejectValue("username", "username.match");
            }

            if (accountService.findAccountByUsername(username) != null) {
                errors.rejectValue("username", "username.duplicate");
            }

        }

        String password = account.getPassword();
        String confirmPassword = account.getConfirmPassword();

        if (!"".equals(password) && password.length() < 5 || password.length() > 15) {
            errors.rejectValue("password", "password.length");
        }

        if (!"".equals(confirmPassword) && !password.equals(confirmPassword)) {
            errors.rejectValue("password", "password.duplicate");
        }

        String email = account.getEmail();

        if (!"".equals(email)) {

            if (!email.matches("^[a-z][a-z0-9_\\.]{5,32}@[a-z0-9]{2,}(\\.[a-z0-9]{2,4}){1,2}$")) {
                errors.rejectValue("email", "email.invalid");
            }

            if (accountService.findAccountByEmail(email) != null) {
                errors.rejectValue("email", "email.duplicate");
            }

        }

        String name = account.getName();

        if (!"".equals(name) && name.length() > 20) {
            errors.rejectValue("name", "name.length");
        }

        Date dob = account.getDob();

        if (dob != null && dob.getTime() > System.currentTimeMillis()) {
            errors.rejectValue("dob", "dob.invalid");
        }

        String phone = account.getPhone();

        if (!"".equals(phone)) {

            if (phone.length()>11 || phone.length()<10){
                errors.rejectValue("phone", "phone.length");
            }

            if (!phone.startsWith("0")){
                errors.rejectValue("phone", "phone.startsWith");
            }

            if (!phone.matches("(^$|[0-9]*$)")){
                errors.rejectValue("phone", "phone.matches");
            }

        }

        if (account.getProvince() == null) {
            errors.rejectValue("province", "province.empty");
        }

        if (account.getDistrict() == null) {
            errors.rejectValue("district", "district.empty");
        }

        if (account.getVillage() == null) {
            errors.rejectValue("village", "village.empty");
        }

    }
}
