package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.modals.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan,Long> {
}
