package api.services;

import api.exceptions.BalanceLowException;
import api.models.TransactionDTO;

import java.util.Date;
import java.util.List;

public interface AccountService {

    TransactionDTO deposit(String account_id, long amount) throws BalanceLowException;

    TransactionDTO withdrawal(String account_id, long amount) throws BalanceLowException;

    List<TransactionDTO> history(String account_id, Date startDate, Date endDate);
}
