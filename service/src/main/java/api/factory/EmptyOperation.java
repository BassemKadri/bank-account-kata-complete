package api.factory;

import api.models.Transaction;
import org.springframework.stereotype.Component;

@Component
public class EmptyOperation implements Operation {
    @Override
    public Transaction operate(String account_id, long amount) {
        return null;
    }
}
