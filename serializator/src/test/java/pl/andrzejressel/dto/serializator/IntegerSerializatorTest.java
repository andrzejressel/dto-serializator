package pl.andrzejressel.dto.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

class IntegerSerializatorTest extends AbstractSerializatorTest<Integer> {

    @Override
    public Serializator<Integer> getSerializator() {
        return IntegerSerializator.INSTANCE;
    }

    @Property
    public void test(@ForAll int value) {
        performTest(value);
    }

}