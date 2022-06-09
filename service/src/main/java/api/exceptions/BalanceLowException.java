package api.exceptions;

public class BalanceLowException extends Exception {

    public BalanceLowException(){
        super("your balance is too low");
    }

}
