package com.serenity.serenity.utilities;

import java.util.HashSet;
import java.util.Set;

public class Kit {
public static void main(String[] args) {
int[] k = {1,3,4,2};
   System.err.println(Kit.lengthOfLongestSubstring("pkkewww"));
}

public static int[] doCalculation(int[] nums,int target){

     int a=0;
     int b=0;
     int ai=0;
     int bi=0;
     boolean stop =false;
     for(int i=0;i<nums.length;i++){
          a=nums[i];
          ai=i;
          System.err.println("rounnd "+ai);
 
         for(int k=0;k<nums.length;k++){
            b=nums[k];
            if(k!=i){
             if((a+b)==target){
                 ai=i;
                 bi=k;
               stop=true;
               break;
             }
            }
          }
          if(stop){
             break;
          }
 
     }
     int[] ks = {ai,bi};
     System.err.println(ai+","+bi);
     return ks;

}

 public static int lengthOfLongestSubstring(String s) {
    
    char[] list = s.toCharArray();
    Set<Character> data = new HashSet<Character>();
    for(char k : list){
        data.add(k);
    }      
    System.err.println(data.toString());
    return data.size();
    }
}
