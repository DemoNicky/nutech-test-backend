package com.nutech.test_project.Controller;

import com.nutech.test_project.Dto.Response.GetBannerResponse;
import com.nutech.test_project.Dto.Response.GetServicesResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Service.InformationService;
import com.nutech.test_project.Utils.TokenUtils.ExtractTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InformationController {

    private final InformationService informationService;

    @GetMapping(value = "/banner")
    public ResponseEntity<ResponseHandling<List<GetBannerResponse>>> getBanner(){
        ResponseHandling<List<GetBannerResponse>> responseHandling = informationService.getBanner();
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

    @GetMapping(value = "/services")
    public ResponseEntity<ResponseHandling<List<GetServicesResponse>>> getService(){
        ResponseHandling<List<GetServicesResponse>> responseHandling = informationService.getService();
        return ResponseEntity.status(HttpStatus.OK).body(responseHandling);
    }

}
