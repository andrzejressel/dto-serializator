package pl.andrzejressel.dto.integrationtestjava;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import pl.andrzejressel.dto.serializator.*;

import java.util.List;
import java.util.Map;

class SimpleDTOSerializatorTest extends AbstractSerializatorTest<SimpleDTO> {

    @Override
    protected Serializator<SimpleDTO> getSerializator() {
        return SimpleDTOSerializator.INSTANCE;
    }

    @Property
    public void test(@ForAll String val1, @ForAll List<Integer> val2, @ForAll Map<String, List<Integer>> val3) {
        performTest(new SimpleDTO(
                val1,
                val2,
                val3,
                List.of(List.of(List.of(List.of("abc"), List.of("123")), List.of(List.of("ABC"), List.of("!@#"))))
        ));
    }

}