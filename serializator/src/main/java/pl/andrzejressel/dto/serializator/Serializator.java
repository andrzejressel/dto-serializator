package pl.andrzejressel.dto.serializator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public interface Serializator<T> {
    @Contract(pure = true)
    T deserialize(ByteBuffer bb);

    @Contract(pure = true)
    @NotNull
    ByteBuffer serialize(T t);
}
