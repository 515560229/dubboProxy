package vip.javacoder.dubboproxy.model;

import lombok.Data;

import java.util.List;

@Data
public class Input {
    private String interfaceName;
    private String methodName;
    private List<String> parameterTypes;
    private List<Object> parameters;
    private String url;
    private String version;
    private Integer timeout;
    private String group;
}