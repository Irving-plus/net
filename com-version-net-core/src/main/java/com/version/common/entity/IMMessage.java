package com.version.common.entity;

import java.io.Serializable;

import com.version.common.util.StringHelper;

public class IMMessage implements Serializable{
	private static final long serialVersionUID = -1018719997646984034L;
	private final String msgId = StringHelper.randUUID();
	private String fromUniqueId;
	private long sendTime;
	private String toUniqueId;
	private int chanelType;
	private int msgType;
	private String msg;
	public IMMessage(String uniqueId, long time, String toUniqueId,int chanelType,
			int msgType, String msg) {
		this.fromUniqueId = uniqueId;
		this.sendTime = time;
		this.toUniqueId = toUniqueId;
		this.chanelType = chanelType;
		this.msgType = msgType;
		this.msg = msg;
	}
	public String getFromUniqueId() {
		return fromUniqueId;
	}
	public void setFromUniqueId(String fromUniqueId) {
		this.fromUniqueId = fromUniqueId;
	}
	public long getSendTime() {
		return sendTime;
	}
	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}
	public String getToUniqueId() {
		return toUniqueId;
	}
	public void setToUniqueId(String toUniqueId) {
		this.toUniqueId = toUniqueId;
	}
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgId() {
		return msgId;
	}
	public int getChanelType() {
		return chanelType;
	}
	public void setChanelType(int chanelType) {
		this.chanelType = chanelType;
	}

}
