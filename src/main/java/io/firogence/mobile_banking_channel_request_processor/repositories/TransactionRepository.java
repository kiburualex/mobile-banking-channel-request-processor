package io.firogence.mobile_banking_channel_request_processor.repositories;

import io.firogence.mobile_banking_channel_request_processor.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Alex Kiburu
 */
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity> {
    @Query(value = """
        SELECT
            SUM(t.amount)
        FROM
            TransactionEntity t
        WHERE
            DATE(t.createdDate) = DATE(:today) AND
            t.serviceCode = :serviceCode AND
            t.customerNationalId = :nationalId
        """)
    Optional<BigDecimal> findTotalAmountByServiceCodeAndNationalIdToday(
            @Param("serviceCode") String serviceCode,
            @Param("nationalId") String customerNationalId,
            @Param("today") LocalDateTime today
    );
}
