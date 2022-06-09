package api.factory;

import api.exceptions.BalanceLowException;
import api.models.Transaction;

public interface Operation {

    Transaction operate(String account_id, long amount) throws BalanceLowException;
}
