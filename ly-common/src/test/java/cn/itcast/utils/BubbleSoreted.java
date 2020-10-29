package cn.itcast.utils;

import java.util.Arrays;

public class BubbleSoreted {
    public static void main(String[] args) {
        BubbleSoreted bubbleSoreted = new BubbleSoreted();
        int[] arrays = new int[]{3, 2, 1, 8, 5, 7, 6, 4,11,111,114};
        bubbleSoreted.bubbleSoretedArith(arrays);
        for (int array : arrays) {
            System.out.print(array+"-");
        }
    }

    public  void  bubbleSoretedArith(int[] arrays){
        for (int i = 0; i <arrays.length; i++) {
            for (int j = 0; j < arrays.length-1-i; j++) {
                if( arrays[j] >arrays[j+1]){

                    int temp = arrays[j+1];
                    arrays[j+1] =arrays[j];
                    arrays[j] =temp;
                }
            }

        }
    }
}
