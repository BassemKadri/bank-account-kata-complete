package api.factory;

import api.exceptions.BalanceLowException;
import api.models.Transaction;
import api.models.TransactionType;
import api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class WithdrawalOperation implements Operation {

    private TransactionRepository transactionRepository;

    @Autowired
    public WithdrawalOperation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction operate(String account_id, long amount) throws BalanceLowException {
        float lastBalance = 0;
        Transaction lastTransaction = transactionRepository.findTopByOrderByDateDesc(account_id);
        if (null != lastTransaction)
            lastBalance = lastTransaction.getBalance();
        if (lastBalance >= amount) {
            Transaction transaction = Transaction.builder()
                    .account_id(account_id)
                    .amount(amount)
                    .type(TransactionType.WITHDRAWL)
                    .date(new Date())
                    .balance(lastBalance - amount)
                    .build();
            return transactionRepository.save(transaction);
        } else {
            throw new BalanceLowException();
        }
    }
}
