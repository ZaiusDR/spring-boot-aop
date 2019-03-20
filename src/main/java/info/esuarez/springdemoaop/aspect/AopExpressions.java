package info.esuarez.springdemoaop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AopExpressions {


    @Pointcut("execution(public void info.esuarez.springdemoaop.dao.*.*(..))")
    public void forDaoPackage() {
    }

    @Pointcut("execution(* info.esuarez.springdemoaop.dao.*.get*(..))")
    public void getter() {
    }

    @Pointcut("execution(* info.esuarez.springdemoaop.dao.*.set*(..))")
    public void setter() {
    }

    @Pointcut("forDaoPackage() && !getter() || setter()")
    public void forDaoPackageNoGetterSetter() {
    }
}
