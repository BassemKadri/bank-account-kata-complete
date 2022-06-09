package services;

import Utils.DateUtilsTest;
import api.exceptions.BalanceLowException;
import api.factory.DepositeOperation;
import api.factory.OperationFactory;
import api.factory.WithdrawalOperation;
import api.models.Transaction;
import api.models.TransactionDTO;
import api.models.TransactionType;
import api.repository.TransactionRepository;
import api.services.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

//we test only services
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private OperationFactory operationFactory;
    @Mock
    private DepositeOperation depositeOperation;
    @Mock
    private WithdrawalOperation withdrawalOperation;
    @InjectMocks
    private AccountServiceImpl accountService;


    @Test
    public void test_deposit() throws BalanceLowException {
        Transaction transactionBeforeDeposit = Transaction.builder().account_id("123")
                .amount(12)
                .balance(1000)
                .build();
        when(transactionRepository.findTopByOrderByDateDesc("123")).thenReturn(transactionBeforeDeposit);

        Transaction transactionAfterDeposit = Transaction.builder()
                .amount(12)
                .balance(1200)
                .build();

        when(operationFactory.getOperation(TransactionType.DEPOSIT)).thenReturn(depositeOperation);
        when(depositeOperation.operate("123", 200)).thenReturn(transactionAfterDeposit);
        TransactionDTO dto = accountService.deposit("123", 200);

        assertEquals(1200.0, dto.getBalance(), 0.00);
    }

    @Test
    public void test_withdrawal() throws BalanceLowException {
        Transaction transactionBeforeDeposit = Transaction.builder().account_id("123")
                .amount(12)
                .balance(1000)
                .build();
        when(transactionRepository.findTopByOrderByDateDesc("123")).thenReturn(transactionBeforeDeposit);

        Transaction transactionAfterDeposit = Transaction.builder()
                .amount(12)
                .balance(800)
                .build();
        when(operationFactory.getOperation(TransactionType.WITHDRAWL)).thenReturn(withdrawalOperation);
        when(withdrawalOperation.operate("123", 200)).thenReturn(transactionAfterDeposit);
        TransactionDTO dto = accountService.withdrawal("123", 200);

        assertEquals(800.0, dto.getBalance(), 0.00);
    }

    @Test
    public void test_history() {
        Date dateTransaction1 = DateUtilsTest.getDate("5-10-2019");
        Date dateTransaction2 = DateUtilsTest.getDate("20-10-2019");
        Date startDate = DateUtilsTest.getDate("02-10-2019");
        Date endDate = DateUtilsTest.getDate("10-10-2019");
        //Suppose we have this two transactions in database
        Transaction t1 = new Transaction("123", dateTransaction1, 100,
                TransactionType.DEPOSIT, 555, "111");
        Transaction t2 = new Transaction("444", dateTransaction2, 200,
                TransactionType.DEPOSIT, 800, "111");
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t1);
        transactions.add(t2);
        when(transactionRepository.findByDateBetween("111", startDate, endDate)).thenReturn(Collections.singletonList(transactions.get(0)));
        List<TransactionDTO> dtos = accountService.history("111", startDate, endDate);

        assertEquals(dtos.size(), 1);
        boolean isBetween = DateUtilsTest.checkDateBetweenTwoDate(dtos.get(0).getDate(), DateUtilsTest.getDate("02-10-2019"), DateUtilsTest.getDate("10-10-2019"));
        assertEquals(isBetween, true);

    }
}
