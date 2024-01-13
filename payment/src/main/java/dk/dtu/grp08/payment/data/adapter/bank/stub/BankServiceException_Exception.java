
package dk.dtu.grp08.payment.data.adapter.bank.stub;

import jakarta.xml.ws.WebFault;


/**
 * This class was generated by the XML-WS Tools.
 * XML-WS Tools 4.0.1
 * Generated source version: 3.0
 * 
 */
@WebFault(name = "BankServiceException", targetNamespace = "http://fastmoney.ws.dtu/")
public class BankServiceException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private BankServiceException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public BankServiceException_Exception(String message, BankServiceException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param cause
     * @param faultInfo
     * @param message
     */
    public BankServiceException_Exception(String message, BankServiceException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: dk.dtu.grp08.payment.data.adapter.bank.stub.BankServiceException
     */
    public BankServiceException getFaultInfo() {
        return faultInfo;
    }

}
