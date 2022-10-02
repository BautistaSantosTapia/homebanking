package com.mindhub.homebanking.modals;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String name;
    private int maxAmount;
    @ElementCollection//se usa para relaciones de uno a muchos de datos simples (numeros, strings) en este caso entre loans y payments
    @Column(name="payment")//genera una nueva columna separada de la de loan en la que se ve el id del loan y el valor de los payments que tiene esa loan
    private List<Integer> payments = new ArrayList<>();

    //ClientLoans
    @OneToMany(mappedBy="loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans;


    public Loan(){}
    public Loan(String name,int maxAmount,List<Integer> payments){
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public long getId() {return id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public int getMaxAmount() {return maxAmount;}

    public void setMaxAmount(int maxAmount) {this.maxAmount = maxAmount;}

    public List<Integer> getPayments() {return payments;}

    public void setPayments(List<Integer> payments) {this.payments = payments;}

    //ClientLoan
    public Set<ClientLoan> getClientloans() {return clientLoans;}//aca no se usa porque no lo necesitamos cuando generamos los dto
    //el metodo set no lo tiene porque si lo queremos modificar lo hacemos desde el clientloan directamente supongo

    //client
    public List<Client> getClients() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getClient()).collect(Collectors.toList());
    }/*este metodo lo que hace es me trae todos los clientes que hayan sacado un prestamo en especifico. ( loan1.getClients() ) esto me deberia traer todos los clientes qe pidieron el loan1*/


}
