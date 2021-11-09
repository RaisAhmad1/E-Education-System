package com.example.EducationDepartment.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


// @FeignClient(name = "e-police-system")
@FeignClient(name = "e-bank")
public interface PoliceServiceClient {
//
//    @GetMapping("/criminal/check-criminal-record")
//    public Boolean checkCriminalRecord(@RequestHeader String cnic);
//    
    @GetMapping("/currency/bank")
    public String bankFiegn();
    
    @GetMapping("/currency/all")
    public ResponseEntity<Object> getAllCurrencies();
    
}
