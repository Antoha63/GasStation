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
public class DeterministicDistribution {

    private double time; //between 1 and 10
    private double[] times;

    public DeterministicDistribution(double time) {
        this.time = time;
        int n = 100;
        times = new double[n];
        for (int i = 0; i < n; i++)
        {
            times[i] += this.time;
        }
    }
}
