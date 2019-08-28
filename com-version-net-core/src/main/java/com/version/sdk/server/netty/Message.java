package com.version.sdk.server.netty;

import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = -9043895947534699359L;
	private int code;
	private int length;
	private byte[] data;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
