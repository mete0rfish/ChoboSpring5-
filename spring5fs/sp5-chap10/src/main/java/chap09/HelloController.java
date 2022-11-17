package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 스프링 MVC에서 컨트롤러로 사용.
public class HelloController {

    @GetMapping("/hello") // 메서드가 처리할 요청 경로를 지정.
    public String hello(Model model, // Model : 컨트롤러의 처리 결과를 뷰에 전달할 때 사용
                        @RequestParam(value = "name", required = false) String name) { // HTTP 요청 파라미터의 값을 메서드의 파라미터로 전달.
                model.addAttribute("greeting","안녕하세요, "+name); // "greeting" 모델 속성에 값을 설정,
                return "hello"; // 컨트롤러의 처리 결과를 보여줄 뷰.
    }
}
