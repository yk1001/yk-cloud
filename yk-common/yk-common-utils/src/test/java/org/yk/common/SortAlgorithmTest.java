package org.yk.common;

import java.util.Arrays;

import org.junit.Test;

public class SortAlgorithmTest {

    @Test
    public void algorithmTest(){
        int[] arr = new int[]{9,8,7,6,5,4,3,2,1};
        insertSort(arr);
        System.out.println("insertSort-"+Arrays.toString(arr));
        
        int[] arr1 = new int[]{9,8,7,6,5,4,3,2,1};
        selectSort(arr1);
        System.out.println("selectSort-"+Arrays.toString(arr1));
        
        int[] arr2 = new int[]{9,8,7,6,5,4,3,2,1};
        bubbleSort(arr2);
        System.out.println("bubbleSort-"+Arrays.toString(arr2));
        
        int[] arr3 = new int[]{9,8,7,6,5,4,3,2,1};
        quickSort(arr3,0,arr3.length-1);
        System.out.println("quickSort-"+Arrays.toString(arr3));
        
        int[] arr4 = new int[]{9,8,7,6,5,4,3,2,1};
        int[] temp = new int[arr4.length];
        mergeSort(arr4,0,arr4.length-1,temp);
        System.out.println("mergeSort-"+Arrays.toString(arr4));
        
    }
    
    /**
     * 插入排序
     * @param arr 待排序的数组
     * */
    private void insertSort(int[] arr){
        for(int i = 1;i<arr.length;i++){
            int temp = arr[i];
            int j = i - 1;
            for (;j >= 0;j--) {
                if(temp < arr[j]){
                    arr[j+1] = arr[j];
                }else{
                   break;
                }
            }
            arr[j+1] = temp;
        }
    }
    
    /**
     * 选择排序
     * @param arr 待排序的数组
     * */
    private void selectSort(int[] arr){
        for(int i = 0;i<arr.length;i++){
            int minPos = i;
            // 找到最小值
            for(int j = minPos;j<arr.length;j++){
                if(arr[j] < arr[minPos]){
                    minPos = j;
                }
            }
            if(i != minPos){
                int temp = arr[i];
                arr[i] = arr[minPos];
                arr[minPos] = temp;
            }
        }
    }
    
    /**
     * 选择排序
     * @param arr 待排序的数组
     * */
    private void bubbleSort(int[] arr){
        for(int i = 0;i < arr.length - 1;i++){
           for(int j = 1;j < arr.length - i;j++){
               if(arr[j-1] > arr[j]){
                   int temp = arr[j-1];
                   arr[j-1] = arr[j];
                   arr[j] = temp;
               }
           }
        }
    }
    
    /**
     * 快速排序
     * @param arr 待排序的数组
     * */
    private void quickSort(int[] arr,int l,int r){
        if(l < r){
            int q = mpartition(arr,l,r);
            quickSort(arr,l,q-1);
            quickSort(arr,q+1,r);
        }
    }
    
    private int mpartition(int[] arr,int l,int r){
        int pivotIndex = l;
        int pivot = arr[pivotIndex];
        while(l < r){
            while (l<r && pivot <= arr[r]){
                r--;
            }
            if (l<r){
                arr[pivotIndex] = arr[r];
                pivotIndex = r;
            }
            while (l<r && pivot > arr[l]){
                l++;
            }
            if (l<r){
                arr[pivotIndex] = arr[l];
                pivotIndex = l;
            }
        }
        arr[l] = pivot;
        return l;
    }
    
    /**
     * 快速排序
     * @param arr 待排序的数组
     * */
    private void mergeSort(int[] arr,int l,int r,int[] temp){
        if(l < r){
            int mid = (l+r)/2;
            mergeSort(arr,l,mid,temp);
            mergeSort(arr,mid+1,r,temp);
            mergearray(arr,l,mid,r,temp);
        }
    }

    private void mergearray(int[] arr, int l, int mid, int r, int[] temp) {
        int lstart = l;
        int rstart = mid + 1;
        int i = 0;
        while(lstart <= mid && rstart <= r){
            if(arr[lstart] <= arr[rstart]){
                temp[i] = arr[lstart];
                lstart++;
            }else{
                temp[i] = arr[rstart];
                rstart++;
            }
            i++;
        }
        while(lstart <= mid){
            temp[i] = arr[lstart];
            lstart++;
            i++;
        }
        while(rstart <= r){
            temp[i] = arr[rstart];
            rstart++;
            i++;
        }
        for(int k=0;k<i;k++){
            arr[l+k] = temp[k];
        }
    }
    
    
}
