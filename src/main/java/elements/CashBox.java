package elements;

import elements.mainElement.MainStaticElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CashBox extends MainStaticElement {

    private int x;
    private int y;
    private static int profit; //заработок за все время
    private static int balance;
    private static int criticalLevel; //between 10000 nd 100000
    private static boolean status = true;

    public CashBox (int x, int y){
        this.x = x;
        this.y = y;
    }

    public static void setStatus(boolean b) {
        status = b;
    }

    public static void setBalance(int i) {
        balance = i;
    }

    public void setPayment(int payment){
        balance += payment;
        profit += payment;
    }

    public boolean checkCashbox(){
        boolean answer = false;
        if (balance >= criticalLevel && this.status == true)
        {
            answer = true;
        }
        return answer;
    }
}
