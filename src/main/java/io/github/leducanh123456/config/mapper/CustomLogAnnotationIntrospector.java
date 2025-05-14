package io.github.leducanh123456.config.mapper;

import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import io.github.leducanh123456.anotation.json.ExcludeLogFromSerialization;

public class CustomLogAnnotationIntrospector extends JacksonAnnotationIntrospector {
    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        if (m.hasAnnotation(ExcludeLogFromSerialization.class)) {
            return true;
        }
        return super.hasIgnoreMarker(m);
    }
}
