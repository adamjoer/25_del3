package test;

import java.lang.*;

import game.Beaker;

public class test1 {

    public static void main(String[] args) {

        Beaker beaker = new Beaker(2,6);

        /*
         * Simulate 1008 dice rolls.
         * Creates an array with counted rolls.
         * Measures an upper bound on average time for 1008 rolls.
         */
        // Make an array for counting 11 outcomes of getSum(), and 2 outcomes of isIdentical.
        int[] obs = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int a;
        // Get time in milliseconds before the for loop.
        long timeStart  = System.currentTimeMillis();
        System.out.println("Time before rolling dice (System.currentTimeMillis()) = " + timeStart);
        // Roll the dice 1008 times and store data in the array.
        for (int i = 0; i < 1008; i++) {
            beaker.roll();
            a = beaker.getSum();
            // Adds 1 to the specific outcome in the array.
            obs[a - 2]++;
        }
        // Calculate how long it took to run the for loop.
        long timeEnd = System.currentTimeMillis();
        System.out.println("Time after rolling dice (System.currentTimeMillis()) = " + timeEnd);
        long runTime = timeEnd-timeStart;
        System.out.println("Time elapsed (milliseconds) = " + runTime);
        // Divide it by the number of rolls, to have an estimate on how long each roll took.
        double perRoll = runTime / 1008.;

        System.out.println("Upper bound on average time per roll: " + perRoll);
        System.out.println("Is this less than 333ms? " + (perRoll < 333.));


        /*
         *  Calculates a chiSquare sample size to try against the chiSquare distribution with 11 degrees of freedom.
         *  The chiSquare test concludes if the observed experiment falls within 95% of the possible outcomes.
         *  For more specifics of this, refer to the project report.
         *  In other words, the test can be used to measure if the random number generation for the dice is credible.
         *  Take note, that far most of the calculations are using int.
         */

        // Define an array of evenly distributed outcomes.
        int[] exp = new int[]{28, 56, 84, 112, 140, 168, 140, 112, 84, 56, 28};
        // Variable for calculating the chiSquare sample.
        double chSq = 0.;
        // Calculate $\sum_{i=0}^{10} \frac{(exp_i - obs_i)^2}{(exp_i)}$ - see report.
        for (int i = 0; i < 11; i++) {
            a = (exp[i] - obs[i]);
            a = a * a;
            chSq += (double) (a) / (double) (exp[i]);
        }
        System.out.println("ChiSquared sample size = " + chSq);
        // threshold is the solution x to the equation P_dice(X>x)=0.95, with 10 DoF. See report.
        double threshold = 18.307;
        // Print a conclusion of the statistical test.
        if (threshold > chSq) {
            System.out.println("The test confirms the dice as credible with a significance level of 5%.");
        } else {
            System.out.println("The test rejects the dice as credible with a significance level of 5%.");
        }
    }
}