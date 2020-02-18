
import entities.FuelTank;
import entities.PetrolStation;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class MainTestTest {
    @org.junit.Test
    public void test() {
        MainTest mainTest = new MainTest();
        mainTest.common();
        /*FuelTank fuelTank = mainTest.fuelTankFindByTopology_Id(1);

        long actual =fuelTank.getId();
        long expected = 1;
        assertEquals(expected, actual);*/
    }
}
