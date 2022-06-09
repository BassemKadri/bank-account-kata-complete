package api.repository;

import api.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {
    Transaction findTopByOrderByDateDesc(String account_id);

    List<Transaction> findByDateBetween(String account_id, Date start, Date end);
}
