package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class NullableSerializator<T> implements Serializator<T> {

    public static <T> Serializator<T> create(Serializator<T> t) {
        return new NullableSerializator<>(t);
    }

    private static final ByteBuffer NULL_BB = ByteBuffer.allocate(1);
    private static final ByteBuffer NON_NULL_BB = ByteBuffer.allocate(1);

    static {
        NULL_BB.put((byte) 0);
        NON_NULL_BB.put((byte) 1);
    }

    private final Serializator<T> s;

    public NullableSerializator(Serializator<T> s) {
        this.s = s;
    }

    @Override
    public @NotNull List<ByteBuffer> serialize(T t) {
        if (t == null) {
            return List.of(NULL_BB);
        } else {
            List<ByteBuffer> result = new ArrayList<>();
            result.add(NON_NULL_BB);
            result.addAll(s.serialize(t));
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
}
