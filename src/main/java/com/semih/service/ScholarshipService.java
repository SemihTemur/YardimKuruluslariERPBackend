package com.semih.service;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.dto.response.ScholarshipResponse;
import com.semih.model.Scholarship;
import com.semih.repository.ScholarshipRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final StudentService studentService;
    private final TreasuryService treasuryService;

    public ScholarshipService(ScholarshipRepository scholarshipRepository, StudentService studentService, TreasuryService treasuryService) {
        this.scholarshipRepository = scholarshipRepository;
        this.studentService = studentService;
        this.treasuryService = treasuryService;
    }

    private Scholarship mapDtoToEntity(ScholarshipRequest scholarshipRequest) {
        Scholarship scholarship = new Scholarship();

        // gelen ogrenci isim ve soyismini alıyorum.
        scholarship.setStudent(studentService.getStudentByNameAndSurname(scholarshipRequest.getStudentName(), scholarshipRequest.getStudentSurname()));

        scholarship.setScholarshipAmount(scholarshipRequest.getScholarshipAmount());
        scholarship.setCurrency(scholarshipRequest.getCurrency());
        scholarship.setPeriod(scholarshipRequest.getPeriod());
        scholarship.setDuration(scholarshipRequest.getDuration());

        return scholarship;
    }

    private ScholarshipResponse mapEntityToResponse(Scholarship scholarship) {
        return new ScholarshipResponse(
          scholarship.getStudent().getName(),
          scholarship.getStudent().getSurname(),
          scholarship.getScholarshipAmount(),
          scholarship.getCurrency(),
          scholarship.getPeriod(),
          scholarship.getDuration(),
          scholarship.getStartingDate(),
          scholarship.getEndingDate(),
          scholarship.getTotalDonatedAmount()
        );
    }


    public void saveScholarship(ScholarshipRequest scholarshipRequest) {
        // Öncelikle gelen değerin içeriklerini alıyorum.
        BigDecimal scholarshipAmount = scholarshipRequest.getScholarshipAmount();
        Integer duration = scholarshipRequest.getDuration();

        //Try'den bağımsız toplam parayı almayı sağlıyon kod.
        BigDecimal totalDonatedAmount = calculateTotalDonatedAmount(scholarshipAmount, duration);

        // daha sonra gelen parayı tl'ye cevırıyorum kasadaki para ile kontrol etmek ıcın.
        BigDecimal totalDonatedAmountTRY = convertToTry(String.valueOf(scholarshipRequest.getCurrency()), totalDonatedAmount);

        // kasadaki o anlık parayı alıyorum
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // eğer kasadaki para miktari daha büyük ise toplam bağışlancak mıktardan,
        // çevirme ve kaydetme işlemlerini yapıyorum.
        if (cashBalance.compareTo(totalDonatedAmountTRY) >= 0) {
            Scholarship scholarship = mapDtoToEntity(scholarshipRequest);
            scholarship.setTotalDonatedAmount(totalDonatedAmount);

            // o anki tarihi alma
            LocalDate today = LocalDate.now();
            scholarship.setStartingDate(today);

            // bitiş tarihini alma
            String periodType = String.valueOf(scholarshipRequest.getPeriod().getValue());
            LocalDate endDate = determineEndingDate(periodType, duration, today);
            scholarship.setEndingDate(endDate);

            // kasadaki para miktarını güncelleme
            BigDecimal updatedCashBalance = cashBalance.subtract(totalDonatedAmountTRY);

            treasuryService.updateTreasury(updatedCashBalance);

            scholarshipRepository.save(scholarship);
        } else {
            System.out.println("Kasada bu kadar para yoktur");
        }

    }

    public List<ScholarshipResponse> getAllScholarships() {
        List<Scholarship> scholarships = scholarshipRepository.findAll();

        return scholarships.stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public void updateScholarshipById(Long id, ScholarshipRequest scholarshipRequest) {
        // Güncellenecek olan nesneyi ve toplam bağışlanan parayı alıyorum.
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAmount = scholarship.getTotalDonatedAmount();

        //  şimdi toplam parayı Try'ye cevırmek kaldı.
        String currencyType = String.valueOf(scholarship.getCurrency());
        BigDecimal totalDonatedAmountTRY = convertToTry(currencyType, totalDonatedAmount);

        BigDecimal receivedAmount = scholarshipRequest.getScholarshipAmount();
        Integer receivedDuration = scholarshipRequest.getDuration();

        // gelen toplam parayı hesaplayalım.
        BigDecimal receivedTotalDonatedAmount = calculateTotalDonatedAmount(receivedAmount, receivedDuration);

        // gelen toplam parayı TRY çevirelim.
        BigDecimal receivedTotalDonatedAmountTRY = convertToTry(String.valueOf(scholarshipRequest.getCurrency()), receivedTotalDonatedAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // mevcut para'dan gelen para degerını cıkartıyorum.
        BigDecimal difference = totalDonatedAmountTRY.subtract(receivedTotalDonatedAmountTRY);

        BigDecimal updatedCashBalance = null;

        // gelen toplam para o anki paradan büyükse kasadan para azaltıcam
        if (receivedTotalDonatedAmountTRY.compareTo(totalDonatedAmountTRY) >= 0) {
            if (cashBalance.compareTo(receivedTotalDonatedAmountTRY) >= 0) {
                // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum
                updatedCashBalance = cashBalance.add(difference);
            } else {
                // hata fırlatıcam burda
                System.out.println("Kasada bu kadar para yoktur");
            }
        } else {
            // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum
            updatedCashBalance = cashBalance.add(difference);
        }

        // gelen yeni nesneyı entity'e çeviriyorum
        Scholarship updatedScholarship = mapDtoToEntity(scholarshipRequest);
        updatedScholarship.setId(id);
        updatedScholarship.setTotalDonatedAmount(receivedTotalDonatedAmount);
        updatedScholarship.setCreatedDate(scholarship.getCreatedDate());

        // başlangıç değerine göre ve gelen değere göre son tarihi guncellıyorum
        LocalDate receivedDate = scholarship.getStartingDate();
        updatedScholarship.setStartingDate(receivedDate);
        String receivedPeriodType = String.valueOf(scholarshipRequest.getPeriod().getValue());
        LocalDate endDate = determineEndingDate(receivedPeriodType, receivedDuration, receivedDate);
        updatedScholarship.setEndingDate(endDate);

        // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum;
        treasuryService.updateTreasury(updatedCashBalance);

        scholarshipRepository.save(updatedScholarship);
    }

    public void deleteScholarshipById(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAmount = scholarship.getTotalDonatedAmount();

        // kayıtlı olan toplam miktari tl'ye çeviriyorum.
        BigDecimal totalDonatedAmountTry = convertToTry(scholarship.getCurrency().toString(), totalDonatedAmount);

        // kasadaki parayı alıyorum.
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.add(totalDonatedAmountTry);

        treasuryService.updateTreasury(updatedBalance);

        scholarshipRepository.delete(scholarship);
    }


    // Bu metot toplam bagıslanan parayı hesaplar
    private BigDecimal calculateTotalDonatedAmount(BigDecimal scholarshipAmount, Integer duration) {
        return scholarshipAmount.multiply(BigDecimal.valueOf(duration));
    }

    private LocalDate determineEndingDate(String periodType, Integer duration, LocalDate startingDate) {
        return switch (periodType) {
            case "Haftalik" -> startingDate.plusWeeks(duration);
            case "Aylık" -> startingDate.plusMonths(duration);
            case "Yıllık" -> startingDate.plusYears(duration);
            default -> startingDate;
        };
    }

    private BigDecimal convertToTry(String currencyType, BigDecimal scholarshipAmount) {
        return switch (currencyType) {
            case "USD", "EUR" -> scholarshipAmount;
            default -> scholarshipAmount;
        };
    }


}
