package com.mapper.testcase.dto;

import lombok.Data;

@Data
public class PrintoutDto {
    private PrintoutStatus printoutStatus;

    private PrintoutDetailsDto printoutDetails;
}
