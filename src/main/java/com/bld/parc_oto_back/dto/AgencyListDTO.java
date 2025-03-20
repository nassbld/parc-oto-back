package com.bld.parc_oto_back.dto;

import lombok.Data;
import java.util.List;

@Data
public class AgencyListDTO {
    private List<Long> agencyIds;
}
