package pl.andrzejressel.dto.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.WithNull;

class NullableSerializatorTest extends AbstractSerializatorTest<Integer> {

    @Override
    public Serializator<Integer> getSerializator() {
        return NullableSerializator.create(IntegerSerializator.INSTANCE);
    }

    @Property
    public void test(@ForAll @WithNull Integer value) {
        performTest(value);
    }

}