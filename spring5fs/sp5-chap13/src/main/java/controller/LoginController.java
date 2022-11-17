package controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {
    private AuthService authService;

    public void setAuthService(AuthService authService){
        this.authService = authService;
    }

    @GetMapping
    public String form(LoginCommand loginCommand,
                       @CookieValue(value="REMEMBER", required = false) Cookie rCookie){
        if(rCookie != null){
            loginCommand.setEmail(rCookie.getValue());
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }


    @PostMapping
    public String submit(LoginCommand loginCommand, Errors errors, HttpSession session,
                         HttpServletResponse response){
        new LoginCommandValidator().validate(loginCommand, errors);
        if(errors.hasErrors()){
            return "login/loginForm";
        }

        try{
            AuthInfo authInfo = authService.authenticate(
                    loginCommand.getEmail(),
                    loginCommand.getPassword());

            //TODO 세션에 authInfo 저장해야함.
            session.setAttribute("authInfo", authInfo);

            Cookie rememberCookie =
                    new Cookie("REMEMBER", loginCommand.getEmail()); // REMEMBER 이름으로 이메일 저장.
            rememberCookie.setPath("/"); // "/xx" 경로 요청시 쿠키 전송 (= "/main")
            if(loginCommand.isRememberEmail()){
                rememberCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 동안 유지되는 쿠키
            }else{
                rememberCookie.setMaxAge(0); // 0초 동안 유지 (==삭제)
            }
            response.addCookie(rememberCookie); // response에 쿠키 추가

            return "login/loginSuccess";

        } catch(WrongIdPasswordException e){
            errors.reject("IdPasswordNotMatching");
            return "login/loginForm";
        }
    }
}
