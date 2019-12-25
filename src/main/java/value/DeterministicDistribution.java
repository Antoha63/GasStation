package elements;

import java.util.Random;

public class DeterministicDistribution {

    private double time; //between 1 and 10
    private double[] times;

    public DeterministicDistribution(double time){
        this.time = time;
    }

    public void createDistribution()
    {
        int n = 100;

        times = new double [n];
        for (int i = 0; i < n; i++)
        {
            times[i] = this.time;
        }

    }
}
