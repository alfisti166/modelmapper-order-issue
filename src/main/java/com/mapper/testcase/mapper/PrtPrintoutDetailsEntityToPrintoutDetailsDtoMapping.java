package com.mapper.testcase.mapper;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.mapper.testcase.dto.PrintoutDetailsDto;
import com.mapper.testcase.dto.PrintoutFormat;
import com.mapper.testcase.entity.PrintoutDetails;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrtPrintoutDetailsEntityToPrintoutDetailsDtoMapping extends PropertyMapConfigurerSupport<PrintoutDetails, PrintoutDetailsDto> {

    @Override
    public PropertyMap<PrintoutDetails, PrintoutDetailsDto> mapping() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(new DictionaryConverter<>(PrintoutFormat.class).converter()).map(source.getPrintoutFormat(), destination.getPrintoutFormat());
            }
        };
    }
}
