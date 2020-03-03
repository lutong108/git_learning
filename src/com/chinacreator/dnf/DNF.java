package com.chinacreator.dnf;


/**
 * 深度优先遍历
 * @author Administrator
 *
 */
public class DNF {
	
	//物品件数n，背包容量v，最大价值maxValue
	private static Integer n=5,v=8,maxValue=0;
	//w[i]每件物品的重量，c[i]每件物品的价值
	private static Integer w[]={3,5,1,2,2},c[]={4,5,2,1,3};
	//index为当前处理物品的编号；sumW、sumC分别为当前总重量和总价值
	public static void DNF(int index,int sumW,int sumC){
		//已完成对n件商品的选择，终止递归
		if(index == n){
			if(sumW <= v && sumC > maxValue){
				maxValue = sumC;
			}
			return;
		}
		//不选第index件商品
		DNF(index+1,sumW,sumC);
		//选第index件商品
		DNF(index+1,sumW+w[index],sumC+c[index]);
	} 
	
	/**
	 * DNF优化
	 * @param index
	 * @param sumW
	 * @param sumC
	 */
	public static void DNF1(int index,int sumW,int sumC){
		//已完成对n件商品的选择，终止递归
		if(index == n){
			return;
		}
		//不选第index件商品
		DNF(index+1,sumW,sumC);
		//选第index件商品
		if(sumW+w[index] <= v){
			if(sumC+c[index] > maxValue){
				maxValue = sumC+c[index];
			}
			DNF(index+1,sumW+w[index],sumC+c[index]);
		}
	} 
	
	public static void main(String[] args) {
		//初始化
		DNF1(0,0,0);
		System.out.println("最大值:"+maxValue);
	}
}
