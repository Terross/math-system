package com.mathsystem.domain.graph;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mathsystem.domain.graph.repository.VertexProjection;

import java.io.IOException;

public class VertexToStringSerializer extends JsonSerializer<VertexProjection> {


    @Override
    public void serialize(VertexProjection value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

    }
}
