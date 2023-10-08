package pl.andrzejressel.dto.serializator;

import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

abstract class AbstractSerializatorTest<T> {
    protected abstract Serializator<T> getSerializator();

    protected void performTest(T value) {
        var serializator = getSerializator();
        ByteBuffer result = serializator.serialize(value);
        assertThat(result.hasRemaining()).isFalse();
        assertThat(value).isEqualTo(serializator.deserialize(result.rewind()));
    }

}
