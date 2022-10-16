package aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(2)
public class CacheAspect {
    private Map<Long, Object> cache = new HashMap<>();

    @Pointcut("execution(public * chap07..*(long))") // 첫번째 인자가 long
    public void cacheTarget(){
    }

    @Around("cacheTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long)joinPoint.getArgs()[0]; // 첫번째 인자를 Long으로 구함.
        if(cache.containsKey(num)){ // num의 키값이 있으면 키의 해당값을 구해서 리턴.
            System.out.printf("CacheAspect: Cache에서 구함[%d]\n", num);
            return cache.get(num);
        }

        Object result = joinPoint.proceed(); // 프록시 대상 객체 실행.
        cache.put(num, result); // 프록시 객체를 실행한 결과를 cache에 추가.
        System.out.printf("CacheAspect: Cache에 추가[%d]\n", num);
        return result; // 프록시 실행결과 리턴.
    }
}
