package code.validation;

import code.model.Account;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Date;

@Component
public class UpdateAccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Account.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Account account = (Account) o;

        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "dob", "dob.empty");
        ValidationUtils.rejectIfEmpty(errors, "gender", "gender.empty");

        if (account.getProvince() == null) {
            errors.rejectValue("province", "province.empty");
        }

        if (account.getDistrict() == null) {
            errors.rejectValue("district", "district.empty");
        }

        if (account.getVillage() == null) {
            errors.rejectValue("village", "village.empty");
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

    }

}
