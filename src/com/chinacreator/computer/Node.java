package com.chinacreator.computer;

public class Node {

	//操作数
	private Double num;
	//操作符
	private Character op;
	//true表示操作数，false表示操作符
	private Boolean flag;
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public Character getOp() {
		return op;
	}
	public void setOp(Character op) {
		this.op = op;
	}
}
