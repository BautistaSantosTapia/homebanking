package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.modals.ClientLoan;
import com.mindhub.homebanking.modals.Loan;

public class ClientLoanDTO {

    private long id;                            //id (del ClientLoan)
    private int amount;     //double                //monto solicitado (del ClientLoan)
    private Integer payments;                   //cuotas a pagar (del ClientLoan)

    private String name;                        //nombre (del loan)
    private long loanId;                          //el id del prestamo (del loan)
//no esta cliente porque cuando llame a este dto en el cliente dto no me interesa que me traiga denuevo el cliente del que es la info (ademas creo que se crearia recursibidad)

    public ClientLoanDTO() {}
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }

    public long getId() {return id;}

    public long getLoanId() {return loanId;}

    public String getName() {return name;}

    public int getAmount() {return amount;}

    public Integer getPayments() {return payments;}
}
