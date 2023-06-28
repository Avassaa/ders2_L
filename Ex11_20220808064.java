import java.util.Arrays;
import java.util.HashMap;

public class Ex11_20220808064 {
    public static void main(String [] yamdux){
        int [] test={1,12,1,1,1,1,1,346,347,34,634,26324,76,347,3457,3216,3457,45689,3};
        findRepeats(test,0);
    }
    public static int numOfTriplets(int[] arr, int sum) {

        sort(arr,new int[arr.length],0,arr.length-1);
        int numOfTriplets = 0;
        int len = arr.length;


        for (int i = 0; i < len - 2; i++) {
            int low = i + 1;
            int high = len - 1;

            while (low < high) {
                int tripletSum = arr[i] + arr[low] + arr[high];
                if (tripletSum < sum) {
                    numOfTriplets += high - low;
                    low++;
                } else {
                    high--;
                }
            }
        }

        return numOfTriplets;
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[i]) {
                    int temp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = temp;
                }
            }
        }
    }

    public static int kthSmallest(int[] arr, int k) {
        sort(arr,new int[arr.length],0,arr.length-1);
        return arr[k];
    }

    public static String subSequence(String str) {

        StringBuilder st = new StringBuilder();
        st.append(str.charAt(0));
        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) < str.charAt(i + 1)) {
                st.append(str.charAt(i + 1));
            }
        }
        System.out.println("O(n)");
        return st.toString();
    }

    public static int isSubString(String str1, String str2) {
        int str2_index = 0;
        int firstIntersection = 0;
        boolean isSubstring = false;
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(str2_index)) {
                if (str2_index == 0) {
                    firstIntersection = i;
                }
                if (str2_index < str2.length() - 1)
                    str2_index++;
                isSubstring = true;
            }
            if (str2_index > 0 && !isSubstring)
                return -1;

        }
        if (str1.length() - firstIntersection < str2.length())
            return -1;
        return firstIntersection;
    }

    public static void findRepeats(int[] arr, int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            Integer i1 = arr[i];
            map.putIfAbsent(i1,1);
            map.put(i1,map.get(i1)+1);
        }
        for (Integer i : map.keySet()) {
            if (map.get(i) > n) {
                System.out.print(i + "\t");
            }
        }
        System.out.println();
    }


    public static void sort(int[] arr, int[] temporary, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;  // 1 2 3 4 5 6 7 8 9
            sort(arr, temporary, low, mid);
            sort(arr, temporary, mid + 1, high);
            merge(arr, temporary, low, mid, high);
        }
    }

    private static void merge(int[] arr, int[] temporary, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            temporary[i] = arr[i];

        }
        int i = low; //for left sorted array
        int j = mid + 1; // right sorted array
        int k = low;// to fill the arr (to merge)
        while (i <= mid && j <= high) {
            if (temporary[i] < temporary[j]) {
                arr[k] = temporary[i];
                i++;
            } else {
                arr[k] = temporary[j];
                j++;
            }
            k++;

        }
        while(i<=mid){
            arr[k]=temporary[i];
            k++;
            i++;
        }

    }
}

