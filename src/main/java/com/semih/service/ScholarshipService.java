package com.semih.service;

import com.semih.dto.request.ScholarshipRequest;
import com.semih.dto.response.BaseResponse;
import com.semih.dto.response.ScholarshipExpenseResponse;
import com.semih.dto.response.ScholarshipResponse;
import com.semih.exception.InsufficientBalanceException;
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

    private Scholarship mapToEntity(ScholarshipRequest scholarshipRequest) {
        Scholarship scholarship = new Scholarship();

        // gelen ogrenci isim ve soyismini alıyorum.
        scholarship.setStudent(studentService.getStudentByNameAndSurname(scholarshipRequest.studentName(), scholarshipRequest.studentSurname()));

        scholarship.setScholarshipAmount(scholarshipRequest.scholarshipAmount());
        scholarship.setCurrency(scholarshipRequest.currency());
        scholarship.setPeriod(scholarshipRequest.period());
        scholarship.setDuration(scholarshipRequest.duration());

        return scholarship;
    }

    private ScholarshipResponse mapToResponse(Scholarship scholarship) {
        return new ScholarshipResponse(
                new BaseResponse(scholarship.getId(),
                        scholarship.getCreatedDate(),
                        scholarship.getModifiedDate()),
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

    private ScholarshipExpenseResponse mapToExpenseResponse(Scholarship scholarship) {
        return new ScholarshipExpenseResponse(
                new BaseResponse(scholarship.getId(),
                        scholarship.getCreatedDate(),
                        scholarship.getModifiedDate()),
                scholarship.getStudent().getName(),
                scholarship.getStudent().getSurname(),
                scholarship.getTotalDonatedAmount(),
                scholarship.getCurrency()
        );
    }

    public ScholarshipResponse saveScholarship(ScholarshipRequest scholarshipRequest) {
        // Öncelikle gelen değerin içeriklerini alıyorum.
        BigDecimal scholarshipAmount = scholarshipRequest.scholarshipAmount();
        Integer duration = scholarshipRequest.duration();

        //Try'den bağımsız toplam parayı almayı sağlıyon kod.
        BigDecimal totalDonatedAmount = helperUtils.calculateTotalDonatedAmount(scholarshipAmount, duration);

        // daha sonra gelen parayı tl'ye cevırıyorum kasadaki para ile kontrol etmek ıcın.
        BigDecimal totalDonatedAmountTRY = currencyRateService
                .convertToTry(String.valueOf(scholarshipRequest.currency()), totalDonatedAmount);

        // kasadaki o anlık parayı alıyorum
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();


        if (cashBalance.compareTo(totalDonatedAmountTRY) < 0) {
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil.");
        }

        // eğer kasadaki para miktari daha büyük ise toplam bağışlancak mıktardan,
        // çevirme ve kaydetme işlemlerini yapıyorum.
        Scholarship savedScholarship = mapToEntity(scholarshipRequest);
        savedScholarship.setTotalDonatedAmount(totalDonatedAmount);

        // o anki tarihi alma
        LocalDate today = LocalDate.now();
        savedScholarship.setStartingDate(today);

        // bitiş tarihini alma
        String period = String.valueOf(savedScholarship.getPeriod());
        LocalDate endDate = helperUtils.determineEndingDate(period, duration, today);
        savedScholarship.setEndingDate(endDate);

        // kasadaki para miktarını güncelleme
        BigDecimal updatedCashBalance = cashBalance.subtract(totalDonatedAmountTRY);

        treasuryService.updateTreasury(updatedCashBalance);

        savedScholarship = scholarshipRepository.save(savedScholarship);
        return mapToResponse(savedScholarship);
    }

    public List<ScholarshipResponse> getScholarshipList() {
        return scholarshipRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Burs Gideri için
    public List<ScholarshipExpenseResponse> getScholarshipExpenseList() {
        return scholarshipRepository.findAll()
                .stream()
                .map(this::mapToExpenseResponse)
                .collect(Collectors.toList());
    }

    public ScholarshipResponse updateScholarshipById(Long id, ScholarshipRequest scholarshipRequest) {
        // Güncellenecek olan nesneyi ve toplam bağışlanan parayı alıyorum.
        Scholarship scholarship = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Burs bulunamadı!!!" + id));
        BigDecimal totalDonatedAmount = scholarship.getTotalDonatedAmount();

        //  şimdi toplam parayı Try'ye cevırmek kaldı.
        String currencyType = String.valueOf(scholarship.getCurrency());
        BigDecimal totalDonatedAmountTRY = currencyRateService.convertToTry(currencyType, totalDonatedAmount);

        BigDecimal receivedAmount = scholarshipRequest.scholarshipAmount();
        Integer receivedDuration = scholarshipRequest.duration();

        // gelen toplam parayı hesaplayalım.
        BigDecimal receivedTotalDonatedAmount = helperUtils
                .calculateTotalDonatedAmount(receivedAmount, receivedDuration);

        // gelen toplam parayı TRY çevirelim.
        BigDecimal receivedTotalDonatedAmountTRY = currencyRateService
                .convertToTry(String.valueOf(scholarshipRequest.currency()), receivedTotalDonatedAmount);

        BigDecimal cashBalance = treasuryService.getTreasuryBalance();

        // mevcut para'dan gelen para degerını cıkartıyorum.
        BigDecimal difference = totalDonatedAmountTRY.subtract(receivedTotalDonatedAmountTRY);

        BigDecimal updatedCashBalance = null;

        // mevcut bakiyeye fark eklenince 0dan büyük ya da eşit ise
        if (cashBalance.add(difference).compareTo(BigDecimal.ZERO) < 0) {
            // hata fırlatıcam burda
            throw new InsufficientBalanceException("İşlem gerçekleştirilemedi. Kasadaki mevcut miktar yeterli değil!!!");
        }

        updatedCashBalance = cashBalance.add(difference);
        // gelen yeni nesneyı entity'e çeviriyorum
        Scholarship updatedScholarship = mapToEntity(scholarshipRequest);
        updatedScholarship.setId(id);
        updatedScholarship.setTotalDonatedAmount(receivedTotalDonatedAmount);
        updatedScholarship.setCreatedDate(scholarship.getCreatedDate());

        // başlangıç değerine göre ve gelen değere göre son tarihi guncellıyorum
        LocalDate receivedDate = scholarship.getStartingDate();
        updatedScholarship.setStartingDate(receivedDate);
        String receivedPeriodType = String.valueOf(scholarshipRequest.period().getValue());
        LocalDate endDate = helperUtils.determineEndingDate(receivedPeriodType, receivedDuration, receivedDate);
        updatedScholarship.setEndingDate(endDate);

        // kasadaki para mıktarını azaltıp verıtabanında guncellıyorum;
        treasuryService.updateTreasury(updatedCashBalance);

        updatedScholarship = scholarshipRepository.save(updatedScholarship);
        return mapToResponse(updatedScholarship);
    }

    public ScholarshipResponse deleteScholarshipById(Long id) {
        Scholarship deletedScholarship = scholarshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Burs bulunamadı!!!" + id));
        BigDecimal totalDonatedAmount = deletedScholarship.getTotalDonatedAmount();

        // kayıtlı olan toplam miktari tl'ye çeviriyorum.
        BigDecimal totalDonatedAmountTry = currencyRateService
                .convertToTry(deletedScholarship.getCurrency().toString(), totalDonatedAmount);

        // kasadaki parayı alıyorum.
        BigDecimal cashBalance = treasuryService.getTreasuryBalance();
        BigDecimal updatedBalance = cashBalance.add(totalDonatedAmountTry);

        treasuryService.updateTreasury(updatedBalance);

        scholarshipRepository.delete(deletedScholarship);
        return mapToResponse(deletedScholarship);
    }

}
