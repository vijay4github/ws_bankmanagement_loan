package com.bankmanagement.loan.repository;

import com.bankmanagement.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByCustomerId(Integer customerId);

    Optional<Loan> findByLoanId(Integer loanId);

}
