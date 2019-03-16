package com.shijia.aop;

import com.shijia.dao.UserDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SpringAOP {
    private static final Logger log=LoggerFactory.getLogger(SpringAOP.class);
    public  SpringAOP(){
        log.info("SpringAOP被扫描加载");
    }
    @Pointcut("execution(* com.shijia.service.impl.MySerivceTestImpl.getUserById())")
    public void getUserById(){}

    @Pointcut("execution(* com.shijia.service.impl.MySerivceTestImpl.getUserByNum())")
    public void getUserByNum(){}

    @Before("getUserById()")
    public  void sayHello(){
        log.info("注解AOP前置通知");
        System.out.println("注解AOP前置通知");
    }

    @After("getUserById()")
    public  void sayBey(){
        log.info("注解AOP后置通知");
        System.out.println("注解AOP后置通知");
    }

    @Around("getUserById()")
    public UserDao sayAround(ProceedingJoinPoint pro) throws Throwable {
        System.out.println("注解类型环绕通知..环绕前");
        UserDao user= (UserDao) pro.proceed();//执行方法
        System.out.println("注解类型环绕通知..环绕后");
        return  user;
    }

    /*@Around("getUserByNum()")
    public  UserDao ChangeUser(){

    }*/
}
