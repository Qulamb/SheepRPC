package com.xiaoyang.RPCVersion0.server;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class ServiceProvider {
    private Map<String,Object> interfaceProvider;

    public ServiceProvider() {
        this.interfaceProvider=new HashMap<>();
    }

    /**
     * 根据实例获取接口内的每一个方法并将（方法名，实例）存到map中用于后续查询
     * @param service
     * 接口的实现类的实例
     */
    public void provideServiceInterface(Object service){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(),service);
        }
    }

    /**
     * 根据接口名称返回对应的实现类的实例
     * @param interfaceName
     * 接口名称
     * @return
     * 接口的对应实现类的实例
     */
    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
