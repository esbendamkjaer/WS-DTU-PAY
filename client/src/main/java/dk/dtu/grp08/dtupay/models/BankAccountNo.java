package dk.dtu.grp08.dtupay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Fuad Hassan Jama (s233468)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountNo {
    String bankAccountNo;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BankAccountNo) {
            BankAccountNo other = (BankAccountNo) obj;
            return bankAccountNo.equals(other.bankAccountNo);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return bankAccountNo.hashCode();
    }
}
