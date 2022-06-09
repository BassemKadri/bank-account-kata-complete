package api;

import Utils.DateUtilsTest;
import api.controller.AccountController;
import api.factory.DepositeOperation;
import api.factory.WithdrawalOperation;
import api.models.TransactionDTO;
import api.repository.AccountRepository;
import api.repository.TransactionRepository;
import api.services.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//we test only controllers and we mock all services
@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private DepositeOperation depositeOperation;
    @MockBean
    private WithdrawalOperation withdrawalOperation;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private AccountRepository accountRepository;


    @Test
    public void deposit_controller() throws Exception {
        TransactionDTO transactionAfterDeposit = TransactionDTO.builder()
                .amount(12)
                .balance(800)
                .build();
        Mockito.when(accountService.deposit("15", 100)).thenReturn(transactionAfterDeposit);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/deposit/{account_id}/{amount}", "15", 100))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(800))
                .andReturn();
    }

    @Test
    public void withDrawal_controller() throws Exception {
        TransactionDTO transactionAfterDeposit = TransactionDTO.builder()
                .amount(12)
                .balance(500)
                .build();
        Mockito.when(accountService.withdrawal("15", 100)).thenReturn(transactionAfterDeposit);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/withdrawal/{account_id}/{amount}", "15", 100))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(500))
                .andReturn();
    }

    @Test
    public void history_controller() throws Exception {
        List<TransactionDTO> dtos = new ArrayList<>();
        TransactionDTO transactionAfterDeposit = TransactionDTO.builder()
                .amount(12)
                .date(DateUtilsTest.getDate("05-09-2019"))
                .balance(500)
                .build();
        dtos.add(transactionAfterDeposit);
        Mockito.when(accountService.history("15", DateUtilsTest.getDate("04-09-2019"), DateUtilsTest.getDate("10-09-2019"))).thenReturn(dtos);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/account/history/{account_id}/{startDate}/{endDate}", "15", "04-09-2019", "10-09-2019"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].balance").value(500))
                .andReturn();
    }


}
