/**
 * Contains static routines for solving the problem of balancing m jobs on p processors
 * with the constraint that each processor can only perform consecutive jobs.
 */
public class LoadBalancing {

    /**
     * Checks if it is possible to assign the specified jobs to the specified number of processors such that no
     * processor's load is higher than the specified query load.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param queryLoad the maximum load allowed for any processor
     * @param p the number of processors
     * @return true iff it is possible to assign the jobs to p processors so that no processor has more than queryLoad load.
     */
    public static boolean isFeasibleLoad(int[] jobSizes, int queryLoad, int p) {
        // TODO: Implement this
        // All elements in jobSizes must not exceed queryLoad, otherwise, directly return FALSE
        // Keep assigning jobs to a particular processor. When job j causes processor p to exceed queryLoad, do not add job j to processor p
        // And move on to the next processor
        // Keep track of the increasing TotalLoad of a particular processor with a new variable
        // This totalLoad would reset once we consider next processor
        // Only if all processor can be assigned then TRUE will be returned. Otherwise if p runs out, return FALSE.
        // How to know p runs out? p-- until p == 0 and jobSizes still has unassigned element.

        int totalLoad = 0;
        if (jobSizes.length == 0) { //Empty array. Invalid inputs.
            return false;
        }

        for (int i = 0; i < jobSizes.length; i++) {
            if (jobSizes[i] > queryLoad  || jobSizes[i] <= 0 || p <= 0) {
                return false; // Invalid inputs too
            } else {
                totalLoad += jobSizes[i];
                if (totalLoad > queryLoad) {
                   p--; //Move on next processor
                   totalLoad = 0; //reset
                   i--; //The current job causes totalLoad to exceed queryLoad, so we assign it to the next processor.
                }
            }
        }

        if (p < 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the minimum achievable load given the specified jobs and number of processors.
     *
     * @param jobSizes the sizes of the jobs to be performed
     * @param p the number of processors
     * @return the maximum load for a job assignment that minimizes the maximum load
     */
    public static int findLoad(int[] jobSizes, int p) {
        // TODO: Implement this
        // Minimum workload is 1
        // Main approach: There is a queryLoad where all processor will have totalLoad < queryLoad. We will keep multiplying minLoad by 2 until isFeasibleLoad returns True
        // This TRUE tells us that the maximum load for a job assignment must be in between the final value of minLoad (Highly likely exceeded our expected output) and minLoad/2 (Highly likely below our expected output)
        // So, we perform Binary Search Algorithm to find the expected output of maxLoad that is minimized.
        // How do we know when we will reach expected output? isFeasibleLoad method on the mid value
        // Efficiency: O(M log n)

        int minLoad = 1;

        if (jobSizes.length == 0 || p <= 0) {
            return -1; //Invalid input
        }

        //From here onwards, input array is valid
        while (!isFeasibleLoad(jobSizes, minLoad, p)) {
            minLoad *= 2;
        }

        //Binary Search Algorithm
        int begin = minLoad / 2;
        int end = minLoad;

        while (begin < end) {
            int mid = begin + (end - begin) / 2;
            if (isFeasibleLoad(jobSizes, mid, p)) { //We want to go as low as possible
                end = mid;
            } else { //Minimum load is higher than current load value
                begin = mid + 1; //Search for higher values
            }
        }

        //After Binary Search, begin is the answer we are looking for (begin = mid + 1 causes begin = end)
        return begin;
    }

    // These are some arbitrary testcases.
    public static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, 100, 80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83},
            {7}
    };

    /**
     * Some simple tests for the findLoad routine.
     */
    public static void main(String[] args) {
        for (int p = 1; p < 30; p++) {
            System.out.println("Processors: " + p);
            for (int[] testCase : testCases) {
                System.out.println(findLoad(testCase, p));
            }
        }
    }
}
