package pl.andrzejressel.dto.serializator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class LongSerializator implements Serializator<Long> {

    public static Serializator<Long> INSTANCE = new LongSerializator();

    private LongSerializator() {}

    @Override
    @Contract(pure = true)
    public @NotNull ByteBuffer serialize(@NotNull Long l) {
        var bb = ByteBuffer.allocate(8);
        bb.putLong(l);
        return bb;
    }

    @Override
    @NotNull
    public Long deserialize(@NotNull ByteBuffer bb) {
        return bb.getLong();
    }
}
