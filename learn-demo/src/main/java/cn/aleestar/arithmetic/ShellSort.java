package cn.aleestar.arithmetic;

import java.util.Arrays;

public class ShellSort {

    public static void main(String[] args) {
        int[] arr = {5, 20, 2, 78, 60, 3, 15, 11, 20, 33, 99, 1, 55, 7, 54};
        //arr = sort(arr);

        //for (int i = 0; i < arr.length; i++) {
        //    System.err.print(arr[i] + ",");
        //}

        sort1(arr);
    }

    public static int[] sort(int[] sourceArray) {
        // 对 arr 进行拷贝，不改变参数内容
        int[] arr = Arrays.copyOf(sourceArray, sourceArray.length);

        int gap = 1;
        while (gap < arr.length) {
            gap = gap * 3 + 1;
        }

        while (gap > 0) {
            for (int i = gap; i < arr.length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 3);
        }

        return arr;
    }


    public static void sort1(int[] arr){


        int gap = arr.length / 5;

        while (gap > 0){
            for(int i = gap; i < arr.length; i++){
                int temp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > temp){
                    arr[j + gap] = arr[j];
                    j-=gap;
                }
                arr[j + gap] = temp;
            }
            gap--;
        }


        Arrays.stream(arr).forEach(System.out::println);
    }

}
