package api.controller;

import api.models.TransactionDTO;
import api.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {


    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/deposit/{account_id}/{amount}")
    public ResponseEntity<TransactionDTO> deposit(@PathVariable("account_id") String account_id, @PathVariable("amount") long amount) {
        try {
            return ResponseEntity.ok(accountService.deposit(account_id, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping(value = "/withdrawal/{account_id}/{amount}")
    public ResponseEntity<TransactionDTO> withdrawal(@PathVariable("account_id") String account_id, @PathVariable("amount") long amount) {
        try {
            return ResponseEntity.ok(accountService.withdrawal(account_id, amount));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/history/{account_id}/{startDate}/{endDate}")
    public ResponseEntity<List<TransactionDTO>> history(
            @PathVariable("account_id") String account_id,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("startDate") Date startDate,
            @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("endDate") Date endDate
    ) {
        try {
            List<TransactionDTO> transactionsDto = accountService.history(account_id, startDate, endDate);
            return ResponseEntity.ok(transactionsDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
