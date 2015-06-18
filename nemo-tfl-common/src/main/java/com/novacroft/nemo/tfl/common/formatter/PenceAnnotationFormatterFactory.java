package com.novacroft.nemo.tfl.common.formatter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

public class PenceAnnotationFormatterFactory implements AnnotationFormatterFactory<PenceFormat> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(Integer.class);
        return set;
    }

    @Override
    public Printer<?> getPrinter(PenceFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(PenceFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation, fieldType);
    }

    protected Formatter<Integer> configureFormatterFrom(PenceFormat annotation, Class<?> fieldType) {
        return new PenceFormatter();
    }

}
