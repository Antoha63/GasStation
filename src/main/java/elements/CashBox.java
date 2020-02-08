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

    private static int x = 666;
    private static int y = 666;
    private static int profit; //заработок за все время
    private static int balance = 0;
    private static int criticalLevel; //between 10000 nd 100000
    private static boolean status = true;
    private static boolean setted = false;

    public CashBox (int x, int y){
        CashBox.x = x;
        CashBox.y = y;
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

    public static int getBalance (){
        return balance;
    }
    public static void setBalance(int i) {
        balance = i;
    }
    public static int getCriticalLevel(){
        return criticalLevel;
    }
    public static void setCriticalLevel(int vol){
        criticalLevel = vol;
    }
    public static boolean getStatus(){
        return CashBox.status;
    }
    public static void setStatus(boolean b) {
        status = b;
    }
    public static int getProfit(){
        return profit;
    }
    public static void setProfit(int i) {
        profit = i;
    }

    public static void setPayment(int payment){
        balance += payment;
        profit += payment;
    }

    public static int getX(){
        return CashBox.x;
    }
    public static int getY(){
        return CashBox.y;
    }
    public static void setX(int x) {
        CashBox.x = x;
    }
    public static void setY(int y) {
        CashBox.y = y;
    }

    public static boolean getSetted() {
        return setted;
    }
    public static void  setSetted(boolean s){
        setted = s;
    }
}
