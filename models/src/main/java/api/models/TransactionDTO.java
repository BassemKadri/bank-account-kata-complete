package api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class TransactionDTO {
    private String transaction_id;
    private Date date;
    private float amount;
    private TransactionType type;
    private float balance;
    private String account_id;
}
