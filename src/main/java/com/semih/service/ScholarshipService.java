package com.semih.service;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.dto.response.ScholarshipExpenseResponse;
import com.semih.dto.response.ScholarshipResponse;
import com.semih.model.Scholarship;
import com.semih.repository.ScholarshipRepository;
import com.semih.utils.HelperUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScholarshipService {

    private final ScholarshipRepository scholarshipRepository;
    private final StudentService studentService;
    private final TreasuryService treasuryService;
    private final CurrencyRateService currencyRateService;
    private final HelperUtils helperUtils;

    public ScholarshipService(ScholarshipRepository scholarshipRepository, StudentService studentService, TreasuryService treasuryService, CurrencyRateService currencyRateService, HelperUtils helperUtils) {
        this.scholarshipRepository = scholarshipRepository;
        this.studentService = studentService;
        this.treasuryService = treasuryService;
        this.currencyRateService = currencyRateService;
        this.helperUtils = helperUtils;
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
                scholarship.getId(),
                scholarship.getCreatedDate(),
                scholarship.getModifiedDate(),
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

    private ScholarshipExpenseResponse mapEntityToExpenseResponse(Scholarship scholarship) {
        return new ScholarshipExpenseResponse(
                scholarship.getStudent().getName(),
                scholarship.getStudent().getSurname(),
                scholarship.getTotalDonatedAmount(),
                scholarship.getCurrency()
        );
    }


    public void saveScholarship(ScholarshipRequest scholarshipRequest) {
        // Öncelikle gelen değerin içeriklerini alıyorum.
        BigDecimal scholarshipAmount = scholarshipRequest.getScholarshipAmount();
        Integer duration = scholarshipRequest.getDuration();

        //Try'den bağımsız toplam parayı almayı sağlıyon kod.
        BigDecimal totalDonatedAmount = helperUtils.calculateTotalDonatedAmount(scholarshipAmount, duration);

        // daha sonra gelen parayı tl'ye cevırıyorum kasadaki para ile kontrol etmek ıcın.
        BigDecimal totalDonatedAmountTRY = currencyRateService.convertToTry(String.valueOf(scholarshipRequest.getCurrency()), totalDonatedAmount);

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
            String period = String.valueOf(scholarship.getPeriod());
            LocalDate endDate = helperUtils.determineEndingDate(period, duration, today);
            scholarship.setEndingDate(endDate);

            // kasadaki para miktarını güncelleme
            BigDecimal updatedCashBalance = cashBalance.subtract(totalDonatedAmountTRY);

            treasuryService.updateTreasury(updatedCashBalance);

            scholarshipRepository.save(scholarship);
        } else {
            throw new RuntimeException("Yetersiz Bakiye");
        }

    }

    public List<ScholarshipResponse> getAllScholarship() {
        return scholarshipRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    // Burs Gideri için
    public List<ScholarshipExpenseResponse> getAllScholarshipExpense() {
        return scholarshipRepository.findAll()
                .stream()
                .map(this::mapEntityToExpenseResponse)
                .collect(Collectors.toList());
    }

    public void updateScholarshipById(Long id, ScholarshipRequest scholarshipRequest) {
        // Güncellenecek olan nesneyi ve toplam bağışlanan parayı alıyorum.
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAmount = scholarship.getTotalDonatedAmount();

        //  şimdi toplam parayı Try'ye cevırmek kaldı.
        String currencyType = String.valueOf(scholarship.getCurrency());
        BigDecimal totalDonatedAmountTRY = currencyRateService.convertToTry(currencyType, totalDonatedAmount);

        BigDecimal receivedAmount = scholarshipRequest.getScholarshipAmount();
        Integer receivedDuration = scholarshipRequest.getDuration();

        // gelen toplam parayı hesaplayalım.
        BigDecimal receivedTotalDonatedAmount = helperUtils.calculateTotalDonatedAmount(receivedAmount, receivedDuration);

        // gelen toplam parayı TRY çevirelim.
        BigDecimal receivedTotalDonatedAmountTRY = currencyRateService.convertToTry(String.valueOf(scholarshipRequest.getCurrency()), receivedTotalDonatedAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // mevcut para'dan gelen para degerını cıkartıyorum.
        BigDecimal difference = totalDonatedAmountTRY.subtract(receivedTotalDonatedAmountTRY);

        BigDecimal updatedCashBalance = null;

        // mevcut bakiyeye fark eklenince 0dan büyük ya da eşit ise
        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) >= 0) {
            updatedCashBalance = cashBalance.add(difference);
        } else {
            // hata fırlatıcam burda
            throw new RuntimeException("Yetersiz Bakiye");
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
        LocalDate endDate = helperUtils.determineEndingDate(receivedPeriodType, receivedDuration, receivedDate);
        updatedScholarship.setEndingDate(endDate);

        // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum;
        treasuryService.updateTreasury(updatedCashBalance);

        scholarshipRepository.save(updatedScholarship);
    }

    public void deleteScholarshipById(Long id) {
        Scholarship scholarship = scholarshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Bulunamadi"));
        BigDecimal totalDonatedAmount = scholarship.getTotalDonatedAmount();

        // kayıtlı olan toplam miktari tl'ye çeviriyorum.
        BigDecimal totalDonatedAmountTry = currencyRateService.convertToTry(scholarship.getCurrency().toString(), totalDonatedAmount);

        // kasadaki parayı alıyorum.
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.add(totalDonatedAmountTry);

        treasuryService.updateTreasury(updatedBalance);

        scholarshipRepository.delete(scholarship);
    }

}
