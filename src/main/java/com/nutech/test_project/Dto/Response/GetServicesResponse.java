package com.nutech.test_project.Dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GetServicesResponse {

    private String service_code;

    private String service_name;

    private String service_icon;

    private BigDecimal service_tariff;

}
