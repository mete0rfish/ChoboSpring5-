package main;

import chap07.Calculator;
import chap07.RecCalculator;
import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {

    public static void main(String[] args){
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext(AppCtx.class);

        // 수정 전
        Calculator cal = ctx.getBean("calculator", Calculator.class); // 프록시 받아옴.

        // calculator 빈 실제 타입은 Calculator를 상속한 프록시 타입이므로
        // RecCalculator로 타입 변환을 할 수 없어서 익셉션 발생.
//        RecCalculator cal = ctx.getBean("calculator", RecCalculator.class);
        long fiveFact = cal.factorial(5); // 프록시.factorial
        System.out.println("cal.factorial(5) = "+fiveFact);
        System.out.println(cal.getClass().getName());
        ctx.close();

    }
}
