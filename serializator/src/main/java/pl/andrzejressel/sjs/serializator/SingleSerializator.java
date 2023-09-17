package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;

public interface SingleSerializator<T> extends Serializator<T> {
    @Contract(pure = true)
    @Override
    default @NotNull List<ByteBuffer> serialize(T t) {
        return List.of(serializeSingle(t));
    }

    @Contract(pure = true)
    @NotNull
    ByteBuffer serializeSingle(T t);
    T deserialize(ByteBuffer bb);
}
