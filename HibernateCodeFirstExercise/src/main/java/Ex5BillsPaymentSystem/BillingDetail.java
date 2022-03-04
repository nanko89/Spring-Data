package Ex5BillsPaymentSystem;

import entity.BasicEntity;

import javax.persistence.*;

@Entity
@Table(name = " billing_detail")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetail extends BasicEntity {

    @Column(name = "number", nullable = false)
    private String number;

    @ManyToOne
    private BankUser owner;

    public BillingDetail() {
    }

    public BillingDetail(String number, BankUser owner) {
        this.number = number;
        this.owner = owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BankUser getOwner() {
        return owner;
    }

    public void setOwner(BankUser owner) {
        this.owner = owner;
    }
}
