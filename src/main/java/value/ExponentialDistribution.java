package value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExponentialDistribution implements Distribution{

    private double lambda; //лямбда between 1 and 4
    private double[] times;

    public ExponentialDistribution(double lambda){
        this.lambda = lambda;
    }

    @Override
    public void modelFunc() {
        int n = 100;
        times = new double [n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
        {
            times[i] += (-1.0 /this.lambda) * Math.log(rand.nextDouble());
        }
    }
}
