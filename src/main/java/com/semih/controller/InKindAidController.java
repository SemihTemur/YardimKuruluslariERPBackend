package com.semih.controller;

import com.semih.dto.request.InKindAidRequest;
import com.semih.dto.response.InKindAidResponse;
import com.semih.service.InKindAidService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("rest/api")
public class InKindAidController {

    private final InKindAidService inKindAidService;

    public InKindAidController(InKindAidService inKindAidService) {
        this.inKindAidService = inKindAidService;
    }

    @PostMapping(path="/saveInKindAid")
    public void saveInKindAid(@RequestBody InKindAidRequest inKindAidRequest) {
        inKindAidService.saveInKindAid(inKindAidRequest);
    }

    @GetMapping(path="/getInKindAidList")
    public List<InKindAidResponse> getInKindAidList() {
        return inKindAidService.getAllInKindAid();
    }

    @PutMapping(path="/updateInKindAidById/{id}")
    public void updateInKindAidById(@PathVariable Long id, @RequestBody InKindAidRequest inKindAidRequest) {
        inKindAidService.updateInKindAidById(id, inKindAidRequest);
    }

    @DeleteMapping(path="/deleteInKindAidById/{id}")
    public void deleteInKindAidById(@PathVariable Long id) {
        inKindAidService.deleteInKindAidById(id);
    }

}
