package Ex5BillsPaymentSystem;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BillingDetail{

    @Column(name = "bank_name", nullable = false)
    private String bankName;

    @Column(name = "swift", nullable = false)
    private String SWIFT;

    public BankAccount() {
    }

    public BankAccount(String number, User owner, String bankName, String SWIFT) {
        super(number, owner);
        this.bankName = bankName;
        this.SWIFT = SWIFT;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public void setSWIFT(String SWIFT) {
        this.SWIFT = SWIFT;
    }
}
