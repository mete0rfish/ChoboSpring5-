package com.spring5fs.sp5chap02;

//자바 설정에서 정보를 읽어와 빈 객체를 생성하고 관리
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class); //AppContext에 정의한 @Bean 불러와 Greeter 객체 생성
        Greeter g = ctx.getBean("greeter", Greeter.class); //위에 생성한 Bean 객체 검색
        String msg = g.greet("스프링");
        System.out.println(msg);
        ctx.close();
    }
}
