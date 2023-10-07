package com.andrzejressel.sjs.integrationtestjava;

import pl.andrzejressel.sjs.serializator.GenerateSerializator;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@GenerateSerializator
public class SimpleDTO {
    public final String a;
    public final List<Integer> b;
    private final Map<String, List<Integer>> c;

    public final List<List<List<List<String>>>> d;

    public SimpleDTO(String a, List<Integer> b, Map<String, List<Integer>> c, List<List<List<List<String>>>> d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Map<String, List<Integer>> getC() {
        return c;
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
