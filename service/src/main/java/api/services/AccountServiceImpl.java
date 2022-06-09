package api.services;


import api.exceptions.BalanceLowException;
import api.factory.Operation;
import api.factory.OperationFactory;
import api.mapper.TransactionMapper;
import api.models.Transaction;
import api.models.TransactionDTO;
import api.models.TransactionType;
import api.repository.TransactionRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private TransactionRepository transactionRepository;
    private OperationFactory operationFactory;
    private TransactionMapper mapper = Mappers.getMapper(TransactionMapper.class);

    @Autowired
    public AccountServiceImpl(TransactionRepository transactionRepository, OperationFactory operationFactory) {
        this.transactionRepository = transactionRepository;
        this.operationFactory = operationFactory;
    }

    @Override
    public TransactionDTO deposit(String account_id, long amount) throws BalanceLowException {
        Operation op = operationFactory.getOperation(TransactionType.DEPOSIT);
        Transaction transaction = op.operate(account_id, amount);
        return mapper.toDto(transaction);
    }

    @Override
    public TransactionDTO withdrawal(String account_id, long amount) throws BalanceLowException {
        Operation op = operationFactory.getOperation(TransactionType.WITHDRAWL);
        Transaction transaction = op.operate(account_id, amount);
        return mapper.toDto(transaction);
    }

    @Override
    public List<TransactionDTO> history(String account_id, Date startDate, Date endDate) {
        List<Transaction> transactions = transactionRepository.findByDateBetween(account_id, startDate, endDate);
        List<TransactionDTO> transactionsDTO = mapper.toListDto(transactions);
        return transactionsDTO;
    }
}
