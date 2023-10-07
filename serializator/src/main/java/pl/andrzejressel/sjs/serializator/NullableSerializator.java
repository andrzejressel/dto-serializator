package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class NullableSerializator<T> implements AppendSerializator<T> {

    public static <T> Serializator<T> create(Serializator<T> t) {
        return new NullableSerializator<>(t);
    }

    private final Serializator<T> s;

    public NullableSerializator(Serializator<T> s) {
        this.s = s;
    }

    @Override
    public @NotNull List<ByteBuffer> serializeToList(T t) {
        if (t == null) {
            return List.of(getNullByteBuffer());
        } else {
            List<ByteBuffer> result = new ArrayList<>();
            result.add(getNonNullByteBuffer());
            result.add(s.serialize(t));
            return result;
        }
    }

    @Override
    public T deserialize(ByteBuffer bb) {
        var firstBit = bb.get();
        if (firstBit == 0) {
            return null;
        } else {
            return s.deserialize(bb);
        }
    }

    private ByteBuffer getNullByteBuffer() {
        return ByteBuffer.wrap(new byte[]{0});
    }

    private ByteBuffer getNonNullByteBuffer() {
        return ByteBuffer.wrap(new byte[]{1});
    }
}
