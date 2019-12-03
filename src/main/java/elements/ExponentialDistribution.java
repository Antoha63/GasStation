package elements;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import repositories.CarRepository;

import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class ExponentialDistribution {

    private double a; //лямбда between 1 and 4
    private double[] times;

    public ExponentialDistribution(double a){
        this.a = a;
    }

    public void createDistribution()
    {
        int n = 100;

        times = new double [n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
        {
            times[i] += (-1.0 /this.a) * Math.log(rand.nextDouble());
        }

    }
}
