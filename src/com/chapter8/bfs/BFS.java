package com.chapter8.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 广度优先遍历
 * @author Administrator
 *
 */
public class BFS {
	//矩阵大小为n*m
	private static Integer n,m;
	//01矩阵
	private static Integer matrix[][];
	//记录位置x,y是否已入队
	private static Boolean inq[][];
	//增量数组
	private static int X[] = {0,0,1,-1};
	private static int Y[] = {1,-1,0,0};
	
	/**
	 * 判断(x,y)是否需要访问
	 * @param x
	 * @param y
	 * @return
	 */
	public static Boolean judge(int x,int y){
		//访问越界
		if(x >= n || x <0 || y >= m || y < 0) return false;
		//当前位置为0或已被访问过
		if(matrix[x][y]==0 || inq[x][y]==true) return false;
		
		return true;
	}

	/**
	 * BFS函数访问位置(x,y)所在的块，将该块中所有为1的inq都设置为true
	 */
	public static void BFS(int x,int y){
		//定义队列
		Queue<Node> Q = new LinkedList<Node>();
		Node node = new Node();
		node.setX(x);
		node.setY(y);
		//当前结点入队
		Q.add(node);
		//设置(x,y)已入队
		inq[x][y] = true;
		while (!Q.isEmpty()) {
			//取出队首元素
			Node top = Q.peek();
			//队首元素出对
			Q.poll();
			//循环四次得到四个相邻位置
			for (int i = 0; i < 4; i++) {
				int newX = top.getX() + X[i];
				int newY = top.getY() + Y[i];
				//判断(newX,newY)是否需要访问
				if(judge(newX,newY)){
					Node newNode = new Node();
					newNode.setX(newX);
					newNode.setY(newY);
					//新坐标加入队列
					Q.add(newNode);
					//将新结点标为已入队
					inq[newX][newY] = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		//初始化01矩阵和inq
		matrix = new Integer[][]{
				{0,1,1,1,0,0,1},
				{0,0,1,0,0,0,0},
				{0,0,0,0,1,0,0},
				{0,0,0,1,1,1,0},
				{1,1,1,0,1,0,0},
				{1,1,1,1,0,0,0}};
		n = 6;
		m = 7;
		inq = new Boolean[n][m];
		for (int x = 0; x< n; x++) {
			for (int y = 0; y< m; y++) {
				inq[x][y] = new Boolean(false);
			}
		}
		//定义块数
		int ans = 0;
		//枚举每一个位置
		for (int x = 0; x< n; x++) {
			for (int y = 0; y< m; y++) {
				//元素为1，且未入队。被访问点：(0,1)(0,6)(2,4)(4,0)
				if(matrix[x][y]==1 && inq[x][y]==false){
					ans++;
					//访问这个元素周围的块，将块内元素都表位inq为true
					BFS(x,y);
				}
			}
		}
		System.out.println("块数："+ans);
	}
}
