package com.mapper.testcase.mapper;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import com.mapper.testcase.entity.Dictionary;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;

public class DictionaryConverter<D extends Dictionary, E extends Enum<E>> extends ConverterConfigurerSupport<D, E> {

    private final Class<E> clazz;

    public DictionaryConverter(Class<E> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Converter<D, E> converter() {
        return new AbstractConverter<>() {
            @Override
            protected E convert(D source) {
                if (source == null) {
                    return null;
                }

                return E.valueOf(clazz, source.getCode());
            }
        };
    }
}
