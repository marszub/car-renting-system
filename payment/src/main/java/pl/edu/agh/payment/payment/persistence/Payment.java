package pl.edu.agh.payment.payment.persistence;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="payments")
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false, unique = true)
    private String payUId;

    @Column(nullable = false)
    private Integer rentalId;

    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    public Payment() { }

    public Payment(final String payUId, final Integer rentalId, final PaymentStatus paymentStatus) {
        this.payUId = payUId;
        this.rentalId = rentalId;
        this.paymentStatus = paymentStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getPayUId() {
        return payUId;
    }

    public void setPayUId(final String payUId) {
        this.payUId = payUId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(final Integer rentalId) {
        this.rentalId = rentalId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
