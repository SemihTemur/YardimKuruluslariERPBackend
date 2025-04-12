package com.semih.repository;

import com.semih.model.OtherExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherExpenseRepository extends JpaRepository<OtherExpense, Long> {
}
