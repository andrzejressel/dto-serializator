package com.andrzejressel.sjs.integrationtestjava;

import pl.andrzejressel.sjs.serializator.GenerateSerializator;

import java.util.List;
import java.util.Objects;

@GenerateSerializator
public class SimpleDTO {
    public final String a;
    public final List<Integer> b;

    public SimpleDTO(String a, List<Integer> b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleDTO simpleDTO = (SimpleDTO) o;
        return Objects.equals(a, simpleDTO.a) && Objects.equals(b, simpleDTO.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
