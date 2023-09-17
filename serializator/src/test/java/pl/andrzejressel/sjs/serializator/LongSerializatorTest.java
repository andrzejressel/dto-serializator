package pl.andrzejressel.sjs.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

class LongSerializatorTest extends AbstractSerializatorTest<Long> {

    @Override
    public Serializator<Long> getSerializator() {
        return  LongSerializator.INSTANCE;
    }

    @Property
    public void test(@ForAll long value) {
        performTest(value);
    }

}