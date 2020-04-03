package cn.aleestar.arithmetic;

/**
 * 快速排序，简称：快排
 */
public class QuickSort {

    private static int[] arr = new int[10000];

    public static void main(String[] args) {

        for (int i = 0; i < arr.length - 1; i++) {
            arr[i] = (int) (Math.random() * 100000);
        }

        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }

        System.err.println("\n-----------------------------");

        long start = System.currentTimeMillis();
        quick(arr, 0 , arr.length - 1);
        long end = System.currentTimeMillis();

        for (int i = 0; i < arr.length - 1; i++) {
            System.out.print(arr[i] + ",");
        }

        System.err.println("\n排序耗时：" + (end - start));

    }


    public static void quick(int[] a, int low, int high){
        if(low < high){
            int i = low;
            int j = high;
            int temp = a[i];
            while (i < j){
                while (i < j && a[j] >= temp){
                    j--;
                }

                a[i] = a[j];

                while (i < j && a[i] <= temp){
                    i++;
                }

                a[j] = a[i];
            }
            a[i] = temp;

            quick(a, low, i - 1);
            quick(a, i + 1, high);
        }
    }
}
