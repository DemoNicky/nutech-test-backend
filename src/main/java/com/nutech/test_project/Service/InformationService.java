package com.nutech.test_project.Service;

import com.nutech.test_project.Dto.Response.GetBannerResponse;
import com.nutech.test_project.Dto.Response.GetServicesResponse;
import com.nutech.test_project.Dto.Response.ResponseHandling;

import java.util.List;

public interface InformationService {
    ResponseHandling<List<GetBannerResponse>> getBanner();

    ResponseHandling<List<GetServicesResponse>> getService();
}
