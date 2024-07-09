package com.myproject.Collection.controller;

import com.myproject.Collection.entity.StaffEntity;
import com.myproject.Collection.service.PaypalService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/paypal")
public class PaypalController {
    @Autowired
    private PaypalService paypalService;

    private String createOrderResponse = null;
    private String showOrderDetailResponse = null;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/createOrder")
    //@ResponseBody
    public String createOrder(Model theModel){
        logger.info("Send paypal order");
        createOrderResponse = paypalService.createOrder();
        theModel.addAttribute("paypalCreateOrderJson", createOrderResponse);
        return "paypal-page";
    }

    @GetMapping("/showOrderDetail")
    public String showOrderDetail(Model theModel){
        logger.info("Show Order Detail");
        showOrderDetailResponse = paypalService.showOrderDetail();
        theModel.addAttribute("paypalCreateOrderJson", createOrderResponse);
        theModel.addAttribute("paypalShowOrderDetailJson", showOrderDetailResponse);
        return "paypal-page";
    }


}
