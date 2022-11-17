package survey;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

public class Question {

    private String title;
    private List<String> options;

    public Question(String title, List<String> options){
        this.title = title;
        this.options = options;
    }

    public Question(String title) {
        this(title, Collections.<String>emptyList());
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isChoice(){
        return options != null && !options.isEmpty();
    }
}

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String form(@ModelAttribute("login") LoginCommand loginCommand){
        return "login/loginForm";
    }

    @PostMapping
    public String form(@ModelAttribute("login") LoginCommand loginCommand){
        ...
    }
}
