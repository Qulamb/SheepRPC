package com.xiaoyang.Server.provider;

import com.xiaoyang.Client.serviceCenter.ServiceCenter;
import com.xiaoyang.Client.serviceCenter.ZkServiceCenter;
import com.xiaoyang.Server.ServiceRegister.ServiceRegister;
import com.xiaoyang.Server.ServiceRegister.impl.ZKServiceRegister;
import com.xiaoyang.Server.ratelimit.RateLimit;
import com.xiaoyang.Server.ratelimit.provider.RateLimitProvider;
import lombok.Data;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
@Data
public class ServiceProvider {
    private Map<String,Object> interfaceProvider;
    private ServiceRegister serviceRegister;
    private int port;
    private String host;
    private RateLimitProvider rateLimitProvider;

    public ServiceProvider(String host,int port) throws InterruptedException {
        this.host=host;
        this.port=port;
        this.interfaceProvider=new HashMap<>();
        this.serviceRegister=new ZKServiceRegister();
        this.rateLimitProvider=new RateLimitProvider();
        };


    /**
     * 根据实例获取接口内的每一个方法并将（方法名，实例）存到map中用于后续查询
     * @param service
     * 接口的实现类的实例
     */
    public void provideServiceInterface(Object service,boolean canRetry){
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            interfaceProvider.put(clazz.getName(),service);
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port),canRetry);
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
