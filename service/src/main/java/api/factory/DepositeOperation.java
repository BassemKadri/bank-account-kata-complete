package api.factory;

import api.models.Transaction;
import api.models.TransactionType;
import api.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DepositeOperation implements Operation {

    private TransactionRepository transactionRepository;

    @Autowired
    public DepositeOperation(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction operate(String account_id, long amount) {
        // lastBalance added to check the first time to add amount transaction
        float lastBalance = 0;
        Transaction lastTransaction = transactionRepository.findTopByOrderByDateDesc(account_id);
        if (null != lastTransaction)
            lastBalance = lastTransaction.getBalance();
        Transaction transaction = Transaction.builder()
                .account_id(account_id)
                .amount(amount)
                .type(TransactionType.DEPOSIT)
                .date(new Date())
                .balance(lastBalance + amount)
                .build();
        return transactionRepository.save(transaction);
    }
}
