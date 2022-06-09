package api.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private String transaction_id;
    private Date date;
    private float amount;
    private TransactionType type;
    private float balance;
    private String account_id;
}
