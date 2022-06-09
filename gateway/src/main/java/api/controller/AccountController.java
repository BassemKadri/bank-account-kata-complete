package api.controller;

import api.models.TransactionDTO;
import clients.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    private AccountClient accountClient;

    @Autowired
    public AccountController(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    /**
     * make deposit in account
     *
     * @param account_id : the id_account
     * @param amount     : amount to deposit
     * @return transaction with new balance
     */
    @PostMapping(value = "/deposit/{account_id}/{amount}")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable("account_id") String account_id, @PathVariable("amount") float amount) {
        try {
            return accountClient.deposit(account_id, amount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * make withdrawal from account
     *
     * @param account_id : the id_account
     * @param amount     : amount to withdrawal
     * @return transaction with new balance
     */
    @PostMapping(value = "/withdrawal/{account_id}/{amount}")
    public ResponseEntity<TransactionDTO> withdrawal(@PathVariable("account_id") String account_id, @PathVariable("amount") float amount) {
        try {
            return accountClient.withdrawal(account_id, amount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * get history of transactions between two dates
     *
     * @param account_id : the id_account
     * @param startDate  : stratDate
     * @param :          endDate
     * @return list transactions
     */
    @GetMapping(value = "/history/{account_id}/{startDate}/{endDate}")
    public ResponseEntity<List<TransactionDTO>> history(
            @PathVariable("account_id") String account_id,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("startDate") Date startDate,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("endDate") Date endDate
    ) {
        try {
            return accountClient.history(account_id, startDate, endDate);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
