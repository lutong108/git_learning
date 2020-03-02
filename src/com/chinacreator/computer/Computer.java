package com.chinacreator.computer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Computer {
	//输入字符
	private static String strs;
	//操作符栈
	private static Stack<Node> s = new Stack<Node>();
	//后缀表达式序列
	private static Queue<Node> q = new LinkedList<Node>();
	//操作符优先级
	private static Map<Character,Integer> op;
	/**
	 * 中缀表达式转后缀表达式
	 */
	public static void change(){
		char[] str = strs.toCharArray();
		for (int i = 0; i < str.length;) {
			if (str[i] >= '0' && str[i] <= '9') {
				//数字
				Node temp = new Node();
				temp.setFlag(true);
				temp.setNum((double)(str[i++]-'0'));			
				while (i < str.length && str[i] >= '0' && str[i] <= '9') {
					Double numTemp = temp.getNum() * 10 + (str[i] - '0');
					temp.setNum(numTemp);
					i++;
				}
				//操作数加入队列
				q.add(temp);
			}else{
				//操作符
				Node temp = new Node();
				temp.setFlag(false);
				//栈顶元素优先级大于操作符优先级，就把栈顶元素放入后缀表达式队列
				while (!s.empty() && op.get(str[i]) <= op.get(s.peek().getOp())) {
					q.add(s.peek());
					s.pop();
				}
				temp.setOp(str[i]);
				s.push(temp);
				i++;
			}
		}
		//如果操作符栈中还有操作符，就把他弹出到后缀表达式队列中
		while (!s.empty()) {
			q.add(s.peek());
			s.pop();
		}
	}
	/**
	 * 计算后缀表达式
	 * @return
	 */
	public static Double cal(){
		Double temp1,temp2;
		Node cur,temp;
		while(!q.isEmpty()){
			//队首元素
			cur = q.peek();
			q.remove();
			//是操作数，直接压入栈
			if(cur.getFlag()){
				s.add(cur);
			}else{
				//操作符
				temp2 = s.peek().getNum();
				s.pop();
				temp1 = s.peek().getNum();
				s.pop();
				temp = new Node();
				temp.setFlag(true);
				if(cur.getOp()=='+'){
					temp.setNum(temp1 + temp2);
				}else if(cur.getOp()=='-'){
					temp.setNum(temp1 - temp2);
				}else if(cur.getOp()=='*'){
					temp.setNum(temp1 * temp2);
				}else{
					temp.setNum(temp1 / temp2);
				}
				s.push(temp);
			}
			
		}
		return s.peek().getNum();
	}
	
	/**
	 * 运行
	 */
	public static void main(String[] args) {
		
		//初始化操作符优先级
		op = new HashMap<Character,Integer>();
		op.put('+', 1);
		op.put('-', 1);
		op.put('*', 2);
		op.put('/', 2);
		
		strs = "21/3*4";//2.67  28
		//初始化栈
		while (!s.empty()) {
			s.pop();
		}
		change();
		System.out.println("结果："+cal());
		
	}
}
