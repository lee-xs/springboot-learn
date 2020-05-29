package cn.aleestar.arithmetic;

/**
 * 选择排序
 *
 * 遍历出最小值的下标跟当前下标进行互换
 */
public class SelectionSort {

    public static void main(String[] args) {

        int[] arr = {5, 20, 2, 78, 60, 3, 15, 11, 20, 33, 99, 1, 55, 7, 54};
        int index = 0, temp;
        for (int i = 0; i < arr.length - 1; i++) {
            index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[j] < arr[index]){
                    index = j;
                }
            }
            temp = arr[index];
            arr[index] = arr[i];
            arr[i] = temp;
        }

        for (int i = 0; i < arr.length; i++) {
            System.err.print(arr[i] + ",");
        }
    }

}
