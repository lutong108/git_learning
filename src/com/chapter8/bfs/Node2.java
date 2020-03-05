package com.chapter8.bfs;
/**
 * 定义坐标位置
 * @author Administrator
 *
 */
public class Node2 {
	private Integer x;
	private Integer y;
	//点s到该点的最小层数
	private Integer step;
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	public Integer getStep() {
		return step;
	}
	public void setStep(Integer step) {
		this.step = step;
	}
}
