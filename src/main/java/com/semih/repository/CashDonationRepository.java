package com.semih.repository;

import com.semih.dto.response.TopDonorResponse;
import com.semih.model.CashDonation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CashDonationRepository extends JpaRepository<CashDonation, Long> {

    @Query("Select sum(c.amount) from CashDonation c")
    BigDecimal getCashDonationAmounts();


    @Query("Select new com.semih.dto.response.TopDonorResponse(d.firstName,d.lastName,sum(cd.amount))" +
            "FROM CashDonation cd JOIN cd.donor d " +
            "GROUP BY d.id, d.firstName, d.lastName " +
            "ORDER BY SUM(cd.amount) DESC")
    List<TopDonorResponse> getTopDonors(Pageable pageable);

    @Query(
            value = """
                        WITH monthly_donations AS (
                            SELECT 
                                donor_id,
                                TO_CHAR(created_date, 'YYYY-MM') AS donation_month,
                                SUM(amount) AS total_donation
                            FROM charityadmin.cash_donation
                            GROUP BY donor_id, TO_CHAR(created_date, 'YYYY-MM')
                        ),
                        ranked_donors AS (
                            SELECT 
                                md.*,
                                RANK() OVER (PARTITION BY donation_month ORDER BY total_donation DESC) AS rank
                            FROM monthly_donations md
                        ),
                        monthly_avg AS (
                            SELECT 
                                TO_CHAR(created_date, 'YYYY-MM') AS donation_month,
                                AVG(amount) AS average_donation
                            FROM charityadmin.cash_donation
                            GROUP BY TO_CHAR(created_date, 'YYYY-MM')
                        )
                        SELECT 
                            rd.donation_month,
                            d.first_name AS donor_name,
                            d.last_name,
                            rd.total_donation,
                            ma.average_donation
                        FROM ranked_donors rd
                        JOIN charityadmin.donor d ON d.id = rd.donor_id
                        JOIN monthly_avg ma ON ma.donation_month = rd.donation_month
                        WHERE rd.rank = 1
                        ORDER BY rd.donation_month;
                    """,
            nativeQuery = true
    )
    List<Object[]> getTopMonthlyDonors();

}
