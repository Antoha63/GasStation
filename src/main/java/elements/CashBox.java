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

    private static int x;
    private static int y;
    private static int profit; //заработок за все время
    private static int balance;
    private static int criticalLevel; //between 10000 nd 100000
    private static boolean status = true;

    public CashBox (int x, int y){
        CashBox.x = x;
        CashBox.y = y;
    }

    public static int getBalance (){
        return balance;
    }

    public static void setCriticalLevel(int vol){
        criticalLevel = vol;
    }

    public static void setStatus(boolean b) {
        status = b;
    }

    public static void setBalance(int i) {
        balance = i;
    }

    public static void setPayment(int payment){
        balance += payment;
        profit += payment;
    }

    public boolean checkCashbox(){
        boolean answer = false;
        if (balance >= criticalLevel && this.status == true)
        {
            answer = true;
            this.status = false;
        }
        return answer;
    }

    public static int getX(){
        return CashBox.x;
    }

    public static int getY(){
        return CashBox.y;
    }

    public static boolean getStatus(){
        return CashBox.status;
    }
}
