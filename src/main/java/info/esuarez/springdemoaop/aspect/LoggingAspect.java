package info.esuarez.springdemoaop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    @Before("AopExpressions.forDaoPackageNoGetterSetter()")
    public void beforeAddAccountAdvice(JoinPoint joinPoint) {

        System.out.println("\n===>>> Executing LoggingAspect advice on addAccount()");

        // Access method signature
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        System.out.println("Calling method: " + methodSignature);

        // Access parameters
        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            System.out.println("Method arg: " + arg.toString());
        }

    }

}
