import java.util.HashMap;

public class Ex11_20220808064 {
    public static void main(String[] args) {

        //  String a ="welcome";
        //  String b="c";
        //    System.out.println(isSubString(a,b));

        //    int[] test={124,235,24,6435,745,73,25,1234,1,1,1,1,153,153,24,24};
        // findRepeats(test,2);
        int[] triplets = {-2, 0, 1, 3};
        System.out.println(numOfTriplets(triplets, 2));

        //    System.out.println(subSequence("Welcome"));
    }


    public static int numOfTriplets(int[] arr, int sum) {
        int count = 0;
        int n = arr.length;


        for (int i = 0; i < n - 2; i++) {
            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int tripletSum = arr[i] + arr[left] + arr[right];
                if (tripletSum < sum) {
                    count += right - left;
                    left++;
                } else {
                    right--;
                }
            }
        }

        return count;
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
            if (map.containsKey(i1))
                map.put(i1, map.get(i1) + 1);
            else {
                map.put(i1, 1);
            }
        }
        for (Integer i : map.keySet()) {
            if (map.get(i) > n) {
                System.out.print(i + "\t");
            }
        }
        System.out.println();
    }

    /* public static void merge(int arr[], int l, int m, int r) {

         int n1 = m - l + 1;
         int n2 = r - m;

         int L[] = new int[n1];
         int R[] = new int[n2];

         for (int i = 0; i < n1; ++i)
             L[i] = arr[l + i];
         for (int j = 0; j < n2; ++j)
             R[j] = arr[m + 1 + j];

         int i = 0, j = 0;

         int k = l;
         while (i < n1 && j < n2) {
             if (L[i] <= R[j]) {
                 arr[k] = L[i];
                 i++;
             }
             else {
                 arr[k] = R[j];
                 j++;
             }
             k++;
         }


         while (i < n1) {
             arr[k] = L[i];
             i++;
             k++;
         }


         while (j < n2) {
             arr[k] = R[j];
             j++;
             k++;
         }
     }

     public static void mergeSort(int arr[], int l, int r)
     {
         if (l < r) {


             int m = l + (r - l) / 2;


             mergeSort(arr, l, m);
             mergeSort(arr, m + 1, r);


             merge(arr, l, m, r);
         }
     }*/
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

