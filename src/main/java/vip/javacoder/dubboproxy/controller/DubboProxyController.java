package vip.javacoder.dubboproxy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vip.javacoder.dubboproxy.dubbo.DubboServiceFactory;
import vip.javacoder.dubboproxy.model.Input;

@RestController
public class DubboProxyController {

    @Autowired
    private DubboServiceFactory factory;

    @PostMapping({"/dubboRpcProxy"})
    Object dubboRpcProxy(@RequestBody Input input) {
        return this.factory.genericInvoke(input);
    }
}
