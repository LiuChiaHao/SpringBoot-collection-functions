package com.myproject.Collection.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionPath {


    // UpdateStaff get data from database path
    @Pointcut("execution(* com.myproject.Collection.controller.StaffListController.updateStaff(..))")
    public void forGetEntityAspectUpdateStaff(){}
    // DeleteStaff get data from database path
    @Pointcut("execution(* com.myproject.Collection.controller.StaffListController.deleteStaff(..))")
    public void forGetEntityAspectDeleteStaff(){}

    // API findById get data from database path
    @Pointcut("execution(* com.myproject.Collection.controller.SteffController.findById(..))")
    public void forGetEntityAspectAPIFindById(){}
    // API deleteStaff get data from database path
    @Pointcut("execution(* com.myproject.Collection.controller.SteffController.deleteStaff(..))")
    public void forGetEntityAspectAPIDeleteStaff(){}

    //EntityNotFoundException path
    @Pointcut("forGetEntityAspectUpdateStaff() || forGetEntityAspectDeleteStaff() " +
            "|| forGetEntityAspectAPIFindById() || forGetEntityAspectAPIDeleteStaff()")
    public void forEntityNotFoundException() {}


    // UpdateStaff get data from database path
    @Pointcut("execution(* com.myproject.Collection.controller.StaffListController.saveStaff(..))")
    public void forNotValidExceptionSaveStaff(){}



}
