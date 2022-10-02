package com.mindhub.homebanking;

import com.mindhub.homebanking.modals.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository repository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of customers
			// /*repository.save(new Client("Jack", "Bauer","j@b.com"));repository.save(new Client("Chloe", "O'Brian", "c@o.com"));repository.save(new Client("Kim", "Bauer", "k@b.com"));repository.save(new Client("Michelle", "Dessler", "m@d.com"));*/
//clientes
			Client cliente1 = new Client("Melba","Lorenzo","melba@lorenzo.com", passwordEncoder.encode("1234"));
			repository.save(cliente1);

			Client cliente2 = new Client("David", "Bustamante", "david@bustamante.com", passwordEncoder.encode("2345"));
			repository.save(cliente2);

			Client cliente3 = new Client("Bautista","Santos Tapia","bst@hotmal.com", passwordEncoder.encode("3456"));
			repository.save(cliente3);


//cuentas
			/*LocalDateTime diaCreacion = LocalDateTime.now(); LocalDateTime tomorrow = diaCreacion.plusDays(1);*/
			Account account1 = new Account("VIN001",LocalDateTime.now(),5000.0,cliente1,AccountType.corriente,true);
			//cliente1.addAccount(account1);//al cliente 1 le agregas la cuenta 1
			accountRepository.save(account1);

			Account account2 = new Account("VIN002",LocalDateTime.now().plusDays(1),7500.0,cliente1,AccountType.ahorro,true);
			//cliente1.addAccount(account2);
			accountRepository.save(account2);

			Account account3 = new Account("VIN003",LocalDateTime.now().plusMonths(2),1500.0,cliente2,AccountType.corriente,true);
			//cliente2.addAccount(account3);
			accountRepository.save(account3);

			Account account4 = new Account("VIN004",LocalDateTime.now().plusDays(4),1234.0,cliente3,AccountType.corriente,true);
			//cliente3.addAccount(account4);
			accountRepository.save(account4);

			Account account5 = new Account("VIN005",LocalDateTime.now().plusDays(24),9876.0,cliente3,AccountType.ahorro,true);
			//cliente3.addAccount(account5);
			accountRepository.save(account5);


//transacciones
			Transaction transaction1 = new Transaction(4444,"hola",LocalDateTime.now(),TransactionType.credito);
			account1.addTransaction(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(6666,"chau",LocalDateTime.now().plusHours(6),TransactionType.credito);
			account1.addTransaction(transaction2);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(-350,"alfajor",LocalDateTime.now().plusYears(8),TransactionType.debito);
			account5.addTransaction(transaction3);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(350,"coca-cola",LocalDateTime.now().plusYears(4),TransactionType.credito);
			account5.addTransaction(transaction4);
			transactionRepository.save(transaction4);

			Transaction transaction5 = new Transaction(10,"prueba-cliente3-cuenta4",LocalDateTime.now(),TransactionType.credito);
			account4.addTransaction(transaction5);
			transactionRepository.save(transaction5);

			Transaction transaction6 = new Transaction(10,"prueba-cliente1-cuenta2",LocalDateTime.now(),TransactionType.credito);
			account2.addTransaction(transaction6);
			transactionRepository.save(transaction6);

			Transaction transaction7 = new Transaction(10,"prueba-cliente2-cuenta3",LocalDateTime.now(),TransactionType.credito);
			account3.addTransaction(transaction7);
			transactionRepository.save(transaction7);

			Transaction transaction8 = new Transaction(-10,"prueba-cliente1-cuenta2",LocalDateTime.now(),TransactionType.debito);
			account2.addTransaction(transaction8);
			transactionRepository.save(transaction8);


//Prestamos(loan)
			/*Hipotecario, monto máximo 500.000, cuotas 12,24,36,48,60
			  Personal, monto máximo 100.000, cuotas 6,12,24
			  Automotriz, monto máximo 300.000, cuotas 6,12,24,36*/

			Loan loan1 = new Loan("Hipotecario",500000, Arrays.asList(12,24,36,48,60));
			//cliente1.addClientLoan();//se la tengo que agragar al DTO???
			loanRepository.save(loan1);

			Loan loan2 = new Loan("Personal",100000, Arrays.asList(6,12,24));
			loanRepository.save(loan2);

			Loan loan3 = new Loan("Automotriz",300000,Arrays.asList(6,12,24,36));
			loanRepository.save(loan3);

//clientLoan
			ClientLoan clientloan1 = new ClientLoan(400000, 60, cliente1, loan1);
			clientLoanRepository.save(clientloan1);

			ClientLoan clientloan2 = new ClientLoan(50000, 12, cliente1, loan2);
			clientLoanRepository.save(clientloan2);

			ClientLoan clientloan3 = new ClientLoan(100000, 24, cliente3, loan2);
			clientLoanRepository.save(clientloan3);

			ClientLoan clientloan4 = new ClientLoan(200000, 36, cliente3, loan3);
			clientLoanRepository.save(clientloan4);

//card
			Card card1 = new Card(CardType.debito,CardColor.GOLD,"1111-1111-1111-1111","Melba Lorenzo",LocalDateTime.now(), LocalDateTime.now().plusYears(5),111,cliente1,true);
			//cliente1.addCard(card1);
			cardRepository.save(card1);

			Card card2 = new Card(CardType.credito,CardColor.TITANIUM,"2222-2222-2222-2222","Melba Lorenzo",LocalDateTime.now(),LocalDateTime.now().plusYears(5),222,cliente1, true);
			//cliente1.addCard(card2);
			cardRepository.save(card2);

			Card card3 = new Card(CardType.credito,CardColor.SILVER,"3333-3333-3333-3333","Bautista Santos Tapia",LocalDateTime.now(),LocalDateTime.now().plusYears(3),333,cliente3,true);
			//cliente3.addCard(card3);
			cardRepository.save(card3);

			/*Card card4 = new Card(CardType.debito,CardColor.TITANIUM,"4444-4444-4444-4444","Bautista Santos Tapia",LocalDateTime.now().minusYears(4),LocalDateTime.now().minusYears(1),444,cliente3,true);
			cardRepository.save(card4);*/

		};
	}




}
