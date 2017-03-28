package kd.musk.upload;
import java.io.*;
import java.util.*;
import java.lang.*;
public class simplePro {

	public static void main(String[] args){
		int[] open={900,1000,1000,1000,2700,1300,800};
		int[] close={1700,1600,1600,1600,1600,1200,800};
		String day[]={"Mon","Tue","Wed","Thur","Fri","Sat","Sun"};
		for(int i=0;i<7;i++){
			int k=i;
			int j=i+1;
			if(open[i]>close[i]){
				while(j<7){
					if(open[j]>close[j]){
						i=j;
						j++;
					}else{
						break;
					}
				}
				if(j==k+1){
					System.out.println(day[i]+": "+"Closed");
				}else{
					System.out.println(day[k]+"-"+day[j-1]+": "+"Closed");
				}
			}else if(open[i]<close[i]){
				while(j<7){
					if(open[i]==open[j]&& close[i]==close[j]){
						i=j;
						j++;
					}else{
						break;
					}
				}
				if(j==k+1){
					System.out.println(day[i]+": "+open[i]+"-"+close[i]);
				}else{
					System.out.println(day[k]+"-"+day[j-1]+": "+open[i]+"-"+close[i]);
				}
			}else if(open[i]==close[i]){
				while(j<7){
					if(open[j]==close[j]){
						i=j;
						j++;
					}else{
						break;
					}
				}
				if(j==k+1){
					System.out.println(day[i]+": "+"Open 24 Hours");
				}else{
					System.out.println(day[k]+"-"+day[j-1]+": "+"Open 24 Hours");
				}
			}else{
				System.out.println(day[i]+": "+"Closed");
			}
		}
	}
}
