package com.version.common.entity;

import com.version.game.Room;
/**
 * @Author 周希来
 * @Date 2019/9/2 14:50
 */
public class ThreadLocalObject {
	private Room room;
	private Controller controller;
	private long beginTime;
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public Controller getController() {
		return controller;
	}
	public void setController(Controller controller) {
		this.controller = controller;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	

}
