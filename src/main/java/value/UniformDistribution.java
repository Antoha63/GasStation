package value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Random;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UniformDistribution {

    private double a; //мат.ожидание between 1 and 10
    private double b; //дисперсия between 0 and 4,5
    private double[] times;

    public UniformDistribution(double a, double b){
        this.a = a;
        this.b = b;
        int n = 100;
        times = new double [n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
        {
            times[i] += a + (b - a) * rand.nextDouble();
        }
    }
}
