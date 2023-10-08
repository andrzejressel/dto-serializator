package pl.andrzejressel.dto.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.Map;

class MapSerializatorTest extends AbstractSerializatorTest<Map<String, Integer>> {

    @Override
    public Serializator<Map<String, Integer>> getSerializator() {
        return new MapSerializator<>(StringSerializator.INSTANCE, IntegerSerializator.INSTANCE);
    }

    @Property
    public void test(@ForAll Map<String, Integer> value) {
        performTest(value);
    }

}