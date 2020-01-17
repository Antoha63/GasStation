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
    private static int balance = 0;
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

    public static int getCriticalLevel(){
        return criticalLevel;
    }

    public static void setStatus(boolean b) {
        status = b;
    }

    public static void setBalance(int i) {
        balance = i;
    }

    public static void setProfit(int i) {
        profit = i;
    }

    public static void setPayment(int payment){
        balance += payment;
        profit += payment;
    }

    public static boolean checkCashbox(){
        boolean answer = true;
        if (balance >= criticalLevel && status == true)
        {
           status = false;
        }
        else{
            answer = false;
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
