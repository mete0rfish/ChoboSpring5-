package com.spring5fs.sp5chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.lang.model.element.AnnotationValue;

public class Main2 {

    public static void main(String[] args){
        //1. 그루비 코드를 통해 설정 정보를 받아 빈 객체 생성
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppContext.class);
        //2. 빈 객체를 제공
        Greeter g1 = ctx.getBean("greeter", Greeter.class);
        Greeter g2 = ctx.getBean("greeter", Greeter.class);
        System.out.println("(g1==g2) = "+(g1==g2));
        ctx.close();
    }
}
