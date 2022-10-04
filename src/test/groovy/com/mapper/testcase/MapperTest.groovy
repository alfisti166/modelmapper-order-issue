package com.mapper.testcase

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.mapper.testcase.dto.PrintoutDto
import com.mapper.testcase.entity.Printouts
import com.mapper.testcase.mapper.PrtPrintoutDetailsEntityToPrintoutDetailsDtoMapping
import com.mapper.testcase.mapper.PrtPrintoutsEntityToPrintoutDtoMapping
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.core.io.ClassPathResource
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MapperTest extends Specification {
    def objectMapper = getObjectMapper()

    def "@mapPrintout - success"() {
        given: "there is object to map"
        def source = getResource("PrintoutEntity.json", new TypeReference<Printouts>() {})

        when: "perform mapping"
        def destination = getMapperFacade(false).map(source, PrintoutDto)

        then: "operation is performed"
        objectMapper.valueToTree(destination) == objectMapper.valueToTree(getResource("PrintoutDto.json", new TypeReference<PrintoutDto>() {}))
    }

    def "@mapPrintout - failure"() {
        given: "there is object to map"
        def source = getResource("PrintoutEntity.json", new TypeReference<Printouts>() {})

        when: "perform mapping"
        def destination = getMapperFacade(true).map(source, PrintoutDto)

        then: "operation is performed"
        objectMapper.valueToTree(destination) == objectMapper.valueToTree(getResource("PrintoutDto.json", new TypeReference<PrintoutDto>() {}))
    }

    private <T> T getResource(String path, TypeReference<T> ref) {
        def responseReader = new InputStreamReader(new ClassPathResource(path).getInputStream())
        return objectMapper.readValue(responseReader, ref)
    }

    private static def getMapperFacade(boolean reverseOrder) {
        def modelMapper = new ModelMapper()
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)

        getConfigurers(reverseOrder).each { configurer ->
            configurer.configure(modelMapper)
        }

        return modelMapper
    }

    private static def getConfigurers(boolean reverseOrder) {
        return reverseOrder ? [
            new PrtPrintoutsEntityToPrintoutDtoMapping(),
            new PrtPrintoutDetailsEntityToPrintoutDetailsDtoMapping(),
        ] : [
            new PrtPrintoutDetailsEntityToPrintoutDetailsDtoMapping(),
            new PrtPrintoutsEntityToPrintoutDtoMapping(),
        ]
    }

    private static def getObjectMapper() {
        def mapper = new ObjectMapper()
        def timeModule = new JavaTimeModule()
        timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        mapper.registerModule(timeModule)
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper
    }
}
