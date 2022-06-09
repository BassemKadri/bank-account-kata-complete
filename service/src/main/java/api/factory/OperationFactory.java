package api.factory;

import api.models.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationFactory {

    public DepositeOperation depositeOperation;
    public EmptyOperation emptyOperation;
    public WithdrawalOperation withdrawalOperation;

    @Autowired
    public OperationFactory(DepositeOperation depositeOperation, EmptyOperation emptyOperation, WithdrawalOperation withdrawalOperation) {
        this.depositeOperation = depositeOperation;
        this.emptyOperation = emptyOperation;
        this.withdrawalOperation = withdrawalOperation;
    }

    public Operation getOperation(TransactionType transactionType){
        switch(transactionType) {
            case DEPOSIT:
                return depositeOperation;
            case WITHDRAWL:
                return withdrawalOperation;
            default:
                return emptyOperation;
        }
    }
}
