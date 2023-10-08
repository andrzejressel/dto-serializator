package pl.andrzejressel.dto.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

class StringSerializatorTest extends AbstractSerializatorTest<String> {

    @Override
    public Serializator<String> getSerializator() {
        return  StringSerializator.INSTANCE;
    }

    @Property
    public void test(@ForAll String value) {
        performTest(value);
    }

}