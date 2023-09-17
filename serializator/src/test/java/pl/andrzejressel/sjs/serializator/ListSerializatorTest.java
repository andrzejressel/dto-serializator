package pl.andrzejressel.sjs.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.List;

class ListSerializatorTest extends AbstractSerializatorTest<List<Integer>> {

    @Override
    public Serializator<List<Integer>> getSerializator() {
        return new ListSerializator<>(IntegerSerializator.INSTANCE);
    }

    @Property
    public void test(@ForAll List<Integer> value) {
        performTest(value);
    }

}