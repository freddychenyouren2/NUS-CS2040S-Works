/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        int begin = 0;
        int end = dataArray.length - 1;
        //Case 1: Empty array
        if (dataArray.length == 0) {
            return 0; //Empty Array
        } else if (dataArray.length == 1) { //Case 2: One-element array
            return dataArray[0];
        }

        //Case 3: Array element decreases to minimum then increases
        //Check if the array decreases first
        if (dataArray[begin] > dataArray[begin + 1]) { //Array decreases first
            //Then check if array is strictly decreasing
            if (dataArray[end] < dataArray[end - 1]) {
                return dataArray[begin]; //Max element
            } else { // Array increases somewhere, now just need to check if the first element or the last element is bigger
                return (dataArray[begin] > dataArray[end]) ? dataArray[begin] : dataArray[end];
            }
        } else { // Case 4: Array Increases first then decreases: Implement Binary search algorithm
            //Check if array is strictly increasing
            if (dataArray[end] > dataArray[end - 1]) {
                return dataArray[end]; //Maximum element
            } else { //Array decreases somewhere, need to find the peak
                int mid = begin + (end - begin) / 2;

                while (dataArray[mid] < dataArray[mid - 1] || dataArray[mid] < dataArray[mid + 1]) {
                    if (dataArray[mid] < dataArray[mid + 1]) {
                        //Peak is somewhere to the right
                        begin = mid;
                    } else {
                        //Peak is somewhere to the left
                        end = mid;
                    }
                    mid = begin + (end - begin) / 2;
                }
                return dataArray[mid]; //Max should be found
            }
        }
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
