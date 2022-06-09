package clients;

import api.models.TransactionDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

// for many services we can use .properties to set urls
@FeignClient(decode404 = true, name = "bank-account", url = "http://localhost:8080")
public interface AccountClient {

    @PostMapping(value = "/deposit/{account_id}/{amount}")
    ResponseEntity<TransactionDTO> deposit(@PathVariable("account_id") String account_id, @PathVariable("amount") float amount);

    @PostMapping(value = "/withdrawal/{account_id}/{amount}")
    ResponseEntity<TransactionDTO> withdrawal(@PathVariable("account_id") String account_id, @PathVariable("amount") float amount);

    @GetMapping(value = "/withdrawal/{account_id}/{startDate}/{endDate}")
    ResponseEntity<List<TransactionDTO>> history(
            @PathVariable("account_id") String account_id,
            @PathVariable("startDate") Date startDate,
            @PathVariable("endDate") Date endDate
    );

}