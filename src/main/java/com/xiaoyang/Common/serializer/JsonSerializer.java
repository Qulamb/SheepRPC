package com.xiaoyang.Common.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoyang.Common.Message.MessageType;
import com.xiaoyang.Common.Message.RPCRequest;
import com.xiaoyang.Common.Message.RPCResponse;

public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        return JSONObject.toJSONBytes(obj);
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj=null;
        switch (messageType){
            case MessageType.MESSAGE_TYPE_REQUEST:
                RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
                Object[] objects=new Object[request.getParams().length];
                // 把json字串转化成对应的对象， fastjson可以读出基本数据类型，不用转化
                for (int i = 0; i < objects.length; i++) {
                    Class<?> paramsType = request.getParamsTypes()[i];
                    if(!paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                        objects[i]=JSONObject.toJavaObject((JSONObject)request.getParams()[i],request.getParamsTypes()[i]);
                    }else {
                        objects[i]=request.getParams()[i];
                    }
                }
                request.setParams(objects);
                obj=request;
                break;
            case MessageType.MESSAGE_TYPE_RESPONSE:
                RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = response.getDataType();
                if(! dataType.isAssignableFrom(response.getData().getClass())){
                    response.setData(JSONObject.toJavaObject((JSONObject) response.getData(),dataType));
                }
                obj = response;
                break;
            default:
                System.out.println("暂时不支持此种消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return SerializerType.JsonSer.getCode();
    }
}
