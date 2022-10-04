package com.mapper.testcase.mapper;

import com.github.jmnarloch.spring.boot.modelmapper.PropertyMapConfigurerSupport;
import com.mapper.testcase.dto.PrintoutDto;
import com.mapper.testcase.dto.PrintoutStatus;
import com.mapper.testcase.entity.Printouts;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrtPrintoutsEntityToPrintoutDtoMapping extends PropertyMapConfigurerSupport<Printouts, PrintoutDto> {

    @Override
    public PropertyMap<Printouts, PrintoutDto> mapping() {
        return new PropertyMap<>() {
            @Override
            protected void configure() {
                using(new DictionaryConverter<>(PrintoutStatus.class).converter()).map(source.getPrintoutStatus(), destination.getPrintoutStatus());
            }
        };
    }
}
