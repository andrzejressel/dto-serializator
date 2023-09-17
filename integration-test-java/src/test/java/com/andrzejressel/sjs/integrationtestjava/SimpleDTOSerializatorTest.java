package com.andrzejressel.sjs.integrationtestjava;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import pl.andrzejressel.sjs.serializator.*;

import java.util.List;

class SimpleDTOSerializatorTest extends AbstractSerializatorTest<SimpleDTO> {

    @Override
    protected Serializator<SimpleDTO> getSerializator() {
        return SimpleDTOSerializator.INSTANCE;
    }

    @Property
    public void test(@ForAll String val1, @ForAll List<Integer> val2) {
        performTest(new SimpleDTO(val1, val2));
    }

}