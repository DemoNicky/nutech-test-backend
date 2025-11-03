package com.nutech.test_project.Service.Impl;

import com.nutech.test_project.Dto.Response.GetBannerResponse;
import com.nutech.test_project.Dto.Response.GetServicesResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;
import com.nutech.test_project.Entity.Banner;
import com.nutech.test_project.Entity.Services;
import com.nutech.test_project.Repository.BannerRepository;
import com.nutech.test_project.Repository.ServicesRepository;
import com.nutech.test_project.Service.InformationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InformationServiceImplements implements InformationService {

    private final BannerRepository bannerRepository;

    private final ServicesRepository servicesRepository;

    @Override
    public ResponseHandling<List<GetBannerResponse>> getBanner() {
        ResponseHandling<List<GetBannerResponse>> responseHandling = new ResponseHandling<>();

        List<Banner> banner = bannerRepository.findAll();

        List<GetBannerResponse> getBannerResponseList = banner.stream().map(p -> {
            GetBannerResponse getBannerResponse = new GetBannerResponse();
            getBannerResponse.setBanner_name(p.getBannerName());
            getBannerResponse.setBanner_image(p.getUrlImage());
            getBannerResponse.setDescription(p.getDescription());

            return getBannerResponse;
        }).collect(Collectors.toList());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Sukses");
        responseHandling.setData(getBannerResponseList);

        return responseHandling;
    }

    @Override
    public ResponseHandling<List<GetServicesResponse>> getService() {
        ResponseHandling<List<GetServicesResponse>> responseHandling = new ResponseHandling<>();

        List<Services> serviceList = servicesRepository.findAll();

        List<GetServicesResponse> getServicesResponsesList = serviceList.stream().map(p -> {
            GetServicesResponse getServicesResponse = new GetServicesResponse();
            getServicesResponse.setService_code(p.getServiceCode());
            getServicesResponse.setService_name(p.getServiceName());
            getServicesResponse.setService_icon(p.getUrlImage());
            getServicesResponse.setService_tariff(p.getPrice());
            return getServicesResponse;
        }).collect(Collectors.toList());

        responseHandling.setStatus("0");
        responseHandling.setMessage("Sukses");
        responseHandling.setData(getServicesResponsesList);

        return responseHandling;
    }


}
