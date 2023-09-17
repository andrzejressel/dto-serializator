package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class StringSerializator implements SingleSerializator<String> {

    public static Serializator<String> INSTANCE = new StringSerializator();

    private StringSerializator() {}

    @Override
    public @NotNull ByteBuffer serializeSingle(@NotNull String l) {
        var bytes = l.getBytes(StandardCharsets.UTF_8);
        var bb = ByteBuffer.allocate(4 + bytes.length);
        bb.putInt(bytes.length);
        bb.put(bytes);
        return bb;
    }

    @Override
    @NotNull
    public String deserialize(@NotNull ByteBuffer bb) {
        var stringSize = bb.getInt();
        var buffer = new byte[stringSize];
        bb.get(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }
}
