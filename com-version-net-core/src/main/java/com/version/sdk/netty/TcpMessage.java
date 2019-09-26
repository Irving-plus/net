package com.version.sdk.netty;

import com.alibaba.fastjson.JSON;
import com.version.common.entity.message.IMessage;
import lombok.Data;

@Data
public class TcpMessage implements IMessage {
	private static final long serialVersionUID = -9043895947534699359L;
	private int code;
	private int length;
	private byte[] data;


    @Override
    public Object getData(Class clazz) {
        return JSON.parseObject(data,clazz);
    }
}
