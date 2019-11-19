package vip.javacoder.dubboproxy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import vip.javacoder.dubboproxy.dubbo.DubboServiceFactory;
import vip.javacoder.dubboproxy.model.Input;

import java.util.Arrays;
import java.util.List;

@RestController
public class DubboProxyController {

    @Autowired
    private DubboServiceFactory factory;

    @PostMapping({"/dubboRpcProxy"})
    public Object dubboRpcProxy(@RequestBody Input input) {
        return this.factory.genericInvoke(input);
    }

    @PostMapping({"/dubboRpcProxyV2"})
    public Object dubboRpcProxy(
            @RequestHeader(value = "dubbo-url") String url,
            @RequestHeader(value = "dubbo-group", required = false) String group,
            @RequestHeader(value = "dubbo-interfaceName") String interfaceName,
            @RequestHeader(value = "dubbo-methodName") String methodName,
            @RequestHeader(value = "dubbo-parameterTypes") String parameterTypes,
            @RequestHeader(value = "dubbo-version", required = false) String version,
            @RequestHeader(value = "dubbo-timeout", required = false) Integer timeout,
            @RequestBody List<Object> parameters) {
        Input input = new Input();
        input.setInterfaceName(interfaceName);
        input.setMethodName(methodName);
        input.setParameterTypes(Arrays.asList(parameterTypes.split(",")));
        input.setParameters(parameters);
        input.setUrl(url);
        input.setVersion(version);
        input.setTimeout(timeout);
        input.setGroup(group);
        return this.factory.genericInvoke(input);
    }
}
