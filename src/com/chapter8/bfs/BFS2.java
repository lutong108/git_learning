package com.chapter8.bfs;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 广度优先遍历(二)
 * @author Administrator
 *
 */
public class BFS2 {
	//矩阵大小为n*m
	private static Integer n,m;
	//迷宫信息
	private static char maze[][];
	//记录位置x,y是否已入队
	private static Boolean inq[][];
	//记录起点和终点
	private static Node2 S,T;
	//增量数组
	private static int X[] = {0,0,1,-1};
	private static int Y[] = {1,-1,0,0};
	
	/**
	 * 判断(x,y)是否需要访问
	 * @param x
	 * @param y
	 * @return
	 */
	public static Boolean test(int x,int y){
		//访问越界
		if(x >= n || x <0 || y >= m || y < 0) return false;
		//当前位置为墙或已入过队
		if(maze[x][y]=='*' || inq[x][y]==true) return false;
		
		return true;
	}

	/**
	 * BFS函数访问位置(x,y)所在的位置，将访问过的不是墙的点inq都设置为true，直到找到目标位置
	 */
	public static Integer BFS(Node2 start){
		//定义队列
		Queue<Node2> Q = new LinkedList<Node2>();
		//起点入队
		Q.add(start);
		//设置起点已入队
		inq[start.getX()][start.getY()] = true;
		while (!Q.isEmpty()) {
			//取出队首元素
			Node2 top = Q.peek();
			//队首元素出对
			Q.poll();
			//队首元素和目标元素位置一致，返回s到当前点的层数
			if(top.getX()==T.getX() && top.getY()==T.getY()){
				return top.getStep();
			}
			//循环四次得到四个相邻位置
			for (int i = 0; i < 4; i++) {
				int newX = top.getX() + X[i];
				int newY = top.getY() + Y[i];
				//判断(newX,newY)是否需要访问
				if(test(newX,newY)){
					Node2 newNode2 = new Node2();
					newNode2.setX(newX);
					newNode2.setY(newY);
					newNode2.setStep(top.getStep()+1);
					//新坐标加入队列
					Q.add(newNode2);
					//将新结点标为已入队
					inq[newX][newY] = true;
				}
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		//初始化地图矩阵和inq
		maze = new char[][]{
				{'.','.','.','.','.'},
				{'.','*','.','*','.'},
				{'.','*','S','*','.'},
				{'.','*','*','*','.'},
				{'.','.','.','T','*'}
			};
		n = 5;
		m = 5;
		inq = new Boolean[n][m];
		for (int x = 0; x< n; x++) {
			for (int y = 0; y< m; y++) {
				inq[x][y] = new Boolean(false);
			}
		}
		//定义起点和终点
		S = new Node2();
		S.setX(2);
		S.setY(2);
		S.setStep(0);
		T = new Node2();
		T.setX(4);
		T.setY(3);
		T.setStep(0);

		System.out.println("层数："+BFS(S));
	}
}
