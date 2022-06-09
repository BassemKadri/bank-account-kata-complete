package api.mapper;

import api.models.Transaction;
import api.models.TransactionDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * mapstruct class for mapping entities we can use dozer
 */
@Mapper()
public interface TransactionMapper {
    @Mappings({
            @Mapping(target = "transaction_id", ignore = true),
            @Mapping(target = "account_id", ignore = true)
    })
    TransactionDTO toDto(Transaction transaction);

    @IterableMapping(qualifiedByName = "toDto")
    List<TransactionDTO> toListDto(List<Transaction> transaction);
}
