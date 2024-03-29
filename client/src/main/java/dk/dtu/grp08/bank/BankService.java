
package dk.dtu.grp08.bank;

import java.math.BigDecimal;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import jakarta.xml.ws.RequestWrapper;
import jakarta.xml.ws.ResponseWrapper;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebService(name = "BankService", targetNamespace = "http://fastmoney.ws.dtu/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankService {


    /**
     * 
     * @param accountId
     * @return
     *     returns dk.dtu.grp08.bank.Account
     * @throws BankServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccount", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.GetAccount")
    @ResponseWrapper(localName = "getAccountResponse", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.GetAccountResponse")
    public Account getAccount(
        @WebParam(name = "account_id", targetNamespace = "")
        String accountId)
        throws BankServiceException_Exception
    ;

    /**
     * 
     * @param balance
     * @param user
     * @return
     *     returns java.lang.String
     * @throws BankServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "createAccountWithBalance", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.CreateAccountWithBalance")
    @ResponseWrapper(localName = "createAccountWithBalanceResponse", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.CreateAccountWithBalanceResponse")
    public String createAccountWithBalance(
        @WebParam(name = "user", targetNamespace = "")
        User user,
        @WebParam(name = "balance", targetNamespace = "")
        BigDecimal balance)
        throws BankServiceException_Exception
    ;

    /**
     * 
     * @param cpr
     * @return
     *     returns dk.dtu.grp08.bank.Account
     * @throws BankServiceException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getAccountByCprNumber", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.GetAccountByCprNumber")
    @ResponseWrapper(localName = "getAccountByCprNumberResponse", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.GetAccountByCprNumberResponse")
    public Account getAccountByCprNumber(
        @WebParam(name = "cpr", targetNamespace = "")
        String cpr)
        throws BankServiceException_Exception
    ;

    /**
     * 
     * @param accountId
     * @throws BankServiceException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "retireAccount", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.RetireAccount")
    @ResponseWrapper(localName = "retireAccountResponse", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.RetireAccountResponse")
    public void retireAccount(
        @WebParam(name = "account_id", targetNamespace = "")
        String accountId)
        throws BankServiceException_Exception
    ;

    /**
     * 
     * @param amount
     * @param creditor
     * @param debtor
     * @param description
     * @throws BankServiceException_Exception
     */
    @WebMethod
    @RequestWrapper(localName = "transferMoneyFromTo", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.TransferMoneyFromTo")
    @ResponseWrapper(localName = "transferMoneyFromToResponse", targetNamespace = "http://fastmoney.ws.dtu/", className = "dk.dtu.grp08.bank.TransferMoneyFromToResponse")
    public void transferMoneyFromTo(
        @WebParam(name = "debtor", targetNamespace = "")
        String debtor,
        @WebParam(name = "creditor", targetNamespace = "")
        String creditor,
        @WebParam(name = "amount", targetNamespace = "")
        BigDecimal amount,
        @WebParam(name = "description", targetNamespace = "")
        String description)
        throws BankServiceException_Exception
    ;

}
