# Spring with AOP

Some notes taken while following the tutorial.


## Config for enabling Aspect scan

```
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

## Creating a @Before Aspect

```
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(public void addAccount())")
    public void beforAddAccountAdvice() {
        System.out.println("\n===>>> Executing @Before advice on addAccount()");
    }
}
```

## Pointcut Expressions

This is the expression for where advice should be applied

```
("execution(public void addAccount())")
```

- execution - applies when the indicated method is executed;

```
executions(modifiers-patter? return-type-pattern declaring-type-pattern? method-name-pattern(param-pattern) throws-pattern?)
```

* modifiers-patter: public or *
* return-type-patter: void, boolean, etc...
* declaring-type-pattern: Class Name of the given method
* method-name-pattern: Method name
* param-pattern: Method's parameters
* throws-pattern: Match methods that throw the exception

Wildcards are allowed (such as *)

I.e:
```
@Before("execution(* * processCreditCard*())")
```

## Defining Pointcut Declarations

To avoid copy-pasting pointcut declarations, we can define them with `@Pointcut` annotation:

```
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(public void info.esuarez.springdemoaop.dao.*.*(..))")
    private void forDaoPackage() {}

    @Before("forDaoPackage()")
    public void beforAddAccountAdvice() {
        System.out.println("\n===>>> Executing @Before advice on addAccount()");
    }
    
}
```

## Combining Pointcut Declarations

This sample excludes getter and setter methods

```
@Pointcut("execution(* info.esuarez.springdemoaop.dao.*.set*(..))")
private void setter() {}

@Pointcut("execution(* info.esuarez.springdemoaop.dao.*.get*(..))")
private void getter() {}

@Pointcut("forDaoPackage() && !(getter() || setter())")
private void forDaoPackageNoGetterSetter() {}

@Before("forDaoPackageNoGetterSetter()")
public void beforAddAccountAdvice() {
...
...
}
```

## Aspects execution order

Aspect classes can be annotated with `@Order(1)` annotation. The annotation number parameter
determines the order.

## Join Points

They can be handy when we are in an Aspect and we want to access the method parameters.
For example for an Aspect which logs information.

```
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

        for(Object arg: args) {
            System.out.println("Method arg: " + arg.toString());
        }

    }

}
```

## Advice types

* `@AfterReturning` - Executed after a successful execution of a method. Apart from the typical
use cases (Logging, Security, Audit, etc...), it can be used for post process data, since it's possible
to access the returned data with `returning=results` in the Pointcut definition. `results` will be passed
as parameter to the Aspect.

* `@AfterThrowing` - Executed after the execution of a method when an Exception is thrown.
Useful for notifying errors, auditing, logging the exeception, etc... It's possible to receive
the Exception as parameter with `throwing=exception` in the Aspect.
__NOTE__: The Exception is not catch by the Aspect!!! Exeception propagation continues.

* `@After` - Executed after the execution of a method regardless of success/failure. It has no access
to the Exception. Also the Aspect code should not rely on the happy path to do something
with the returned data.

* `@Around` - Executed before and after the execution of a method. Nice for code 
Instrumentation/Profiling (I.e: how long does it takes to execute anything). It's possible
to access the method directly with a `ProceedingJoinPoint`, similar to the `JoinPoint`, but
it provides a way to execute the method inside the Aspect with:
```
@Around("execution...")
public Object theMethodAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    long begin = System.currentTimeMillis();
    
    Object result = proceedingJoinPoint.proceed(); // "Executes target method"
    
    long end = System.currentTimeMillis();
    
    long duration = end - begin;
    System.out.println("Duration: " + duration + " milliseconds."); 
    
    return result; // Return the result to the calling program.
}
```
Also, since it's possible to execute the method inside the Aspect, Exception handling is possible
or do something before rethrowing them.

