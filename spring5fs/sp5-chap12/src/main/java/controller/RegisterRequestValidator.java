package controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;
import spring.RegisterRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterRequestValidator implements Validator {
    private static final String emailRegExp =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;

    public RegisterRequestValidator(){
        pattern = Pattern.compile(emailRegExp);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest regReq = (RegisterRequest) target; // target을 실제 타ㅣㅂ으로 변행
        if(regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()){ // email 프로퍼티가 유효한지 검사
            errors.rejectValue("email", "required"); // 에러 코드로 required 추가
        }else{
            Matcher matcher = pattern.matcher(regReq.getEmail());
            if(matcher.matches()){
                errors.rejectValue("email", "bad");
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
        if(!regReq.getPassword().isEmpty()){
            if(!regReq.isPasswordEqualToConfirmPassword()){
                errors.rejectValue("confirmPassword","nomatch");
            }
        }
    }
}

