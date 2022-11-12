package pl.edu.agh.payment.payment.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    @Query("SELECT p FROM Payment p WHERE p.payUId = :payUId")
    Optional<Payment> getPaymentByPayUId(@Param("payUId") String payUId);

    @Query("SELECT p FROM Payment p")
    List<Payment> getAllPayments();

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Payment p SET p.paymentStatus = :newPaymentStatus WHERE p.payUId = :payUId")
    void updatePaymentStatus(@Param("payUId") final String payUId,
                             @Param("newPaymentStatus") final PaymentStatus paymentStatus);
}
