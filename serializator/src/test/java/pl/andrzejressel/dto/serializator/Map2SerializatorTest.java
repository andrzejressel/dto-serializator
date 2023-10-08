package pl.andrzejressel.dto.serializator;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.Objects;

class Map2SerializatorTest extends AbstractSerializatorTest<Map2SerializatorTest.Pair> {

    @Override
    protected Serializator<Map2SerializatorTest.Pair> getSerializator() {
        return new Map2Serializator<>(
                new Tuple2<>(IntegerSerializator.INSTANCE, StringSerializator.INSTANCE),
                pair -> new Tuple2<>(pair.a, pair.b),
                tuple -> new Pair(tuple.a, tuple.b)
        );
    }

    @Property
    public void test(@ForAll Integer val1, @ForAll String val2) {
        performTest(new Pair(val1, val2));
    }

    public static class Pair {
        private final int a;
        private final String b;

        public Pair(int a, String b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return a == pair.a && Objects.equals(b, pair.b);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b);
        }
    }

}