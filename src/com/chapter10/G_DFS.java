package com.chapter10;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class G_DFS {
	private static Integer maxn=2010;//总人数
	private static Integer INF=1000000000;//无穷大
	private static Map<Integer, String> intToString=new HashMap<Integer, String>();//编号-->姓名
	private static Map<String, Integer> stringToInt=new HashMap<String, Integer>();//姓名-->编号
	private static Map<Integer, String> Gang=new HashMap<Integer, String>();//head-->人数
	
	private static int G[][]=new int[1000][1000],weight[]=new int[100];//邻接矩阵G、点权weight
	private static int n,k,numPerson=0;//边数n,下限k,总人数numPerson
	private static Boolean vis[]={false};//标记是否被访问
	
	public static void main(String[] args) {
		int w;
		String str1,str2;
		n=8;
		k=59;
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i < n; i++) {
			System.out.println("请端点1：");
			str1 = sc.nextLine();
	        System.out.println("请端点2：");
	        str2 = sc.nextLine();
	        System.out.println("请点权：");
	        w = sc.nextInt();
	        int id1 = change(str1);//将str1转换为编号id1
	        int id2 = change(str2);//将str2转换为编号id2
	        weight[id1] += w;//id1的点权增加w
	        weight[id2] += w;//id2的点权增加w
	        G[id1][id2] += w;//边id1-->id2的点权增加w
	        G[id2][id1] += w;//边id2-->id1的点权增加w
		}
		//DFSTrave();//遍历整个图的所有连通块
	}
	/**
	 * 返回姓名str对应的编号
	 */
	public static Integer change(String str) {
		if(stringToInt.get(str) != null){
			return stringToInt.get(str);
		}else{
			stringToInt.put(str, numPerson);
			intToString.put(numPerson, str);
			return numPerson++;
		}
	}
}
