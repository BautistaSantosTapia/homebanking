package com.mindhub.homebanking.modals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String nombre, apellido, mail;
    private String password;

    //accounts
    @OneToMany(mappedBy="cliente", fetch=FetchType.EAGER)
    private Set<Account> cuentas = new HashSet<>();

    //cards
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    //ClientLoans
    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans;



    public Client(){}
    public Client(String nombre, String apellido, String mail, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.password = password;
    }

    public long getId() {return id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}
    public void setApellido(String apellido) {this.apellido = apellido;}

    public String getMail() {return mail;}
    public void setMail(String mail) {this.mail = mail;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    //cuentas
    public Set<Account> getCuentas() {return cuentas;}
    public void addAccount(Account cuenta) {
        cuenta.setCliente(this);
        cuentas.add(cuenta);
    }

    //cards
    public Set<Card> getCards() {return cards;}
    public void addCard(Card card){
        card.setClient(this);
        cards.add(card);
    }

    //ClientLoan
    public Set<ClientLoan> getClientloans() {return clientLoans;}              //aca traigo el clientloan que me sirve para usarlo abajo y en el dto

    /*public void addClientLoan(ClientLoan clientLoan){
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);
    }*/

    //loan
    @JsonIgnore
    public List<Loan> getLoans() {
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toList());  //aca lo puedo usar ya que lo llame arriba
    }/*me va a traer todos los prestamos que haya pedido un cliente en especifico ( client1.getLoans() ) esto me deberia traer todos los prestamos asociados al cliente 1*/

}
