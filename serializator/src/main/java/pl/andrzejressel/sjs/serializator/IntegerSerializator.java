package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class IntegerSerializator implements Serializator<Integer> {

    public static Serializator<Integer> INSTANCE = new IntegerSerializator();

    private IntegerSerializator() {}

    @Override
    public @NotNull ByteBuffer serialize(@NotNull Integer l) {
        var bb = ByteBuffer.allocate(4);
        bb.putInt(l);
        return bb;
    }

    @Override
    @NotNull
    public Integer deserialize(@NotNull ByteBuffer bb) {
        return bb.getInt();
    }
}
