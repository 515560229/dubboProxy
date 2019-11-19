package vip.javacoder.dubboproxy.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vip.javacoder.dubboproxy.model.Input;

@Component
public class DubboServiceFactory {
    @Autowired
    private ApplicationConfig application;

    public Object genericInvoke(Input input) {
        return input.getParameterTypes() != null && !input.getParameterTypes().isEmpty() ? this.genericInvoke(input.getInterfaceName(), input.getMethodName(), (String[]) input.getParameterTypes().toArray(new String[0]), input.getParameters().toArray(), input.getUrl(), input.getVersion(), input.getTimeout(), input.getGroup()) : this.genericInvoke(input.getInterfaceName(), input.getMethodName(), (String[]) null, input.getParameters().toArray(), input.getUrl(), input.getVersion(), input.getTimeout(), input.getGroup());
    }

//    public Object genericInvoke(String interfaceClass, String methodName, String[] parameterTypes, Object[] parameters) {
//        return this.genericInvoke(interfaceClass, methodName, parameterTypes, parameters);
//    }

    private Object genericInvoke(String interfaceClass, String methodName, String[] parameterTypes, Object[] parameters, String url, String version, Integer timeout, String group) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig();
        reference.setApplication(this.application);
        reference.setInterface(interfaceClass);
        reference.setGeneric(true);

        if (timeout == null) {
            reference.setTimeout(60000);
        } else {
            reference.setTimeout(timeout * 1000);
        }

        if (url != null && url.trim().length() > 0) {
            if (url.trim().startsWith("dubbo:")) {
                reference.setUrl(url);
            } else {
                RegistryConfig registryConfig = new RegistryConfig(url);
                reference.setRegistry(registryConfig);
            }
        }

        if (version != null && !version.trim().isEmpty()) {
            reference.setVersion(version);
        }

        if (group != null) {
            reference.setGroup(group);
        }

        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(reference);

        Object result;
        try {
            result = genericService.$invoke(methodName, parameterTypes, parameters);
        } finally {
            cache.destroy(reference);
        }
        return result;
    }
}