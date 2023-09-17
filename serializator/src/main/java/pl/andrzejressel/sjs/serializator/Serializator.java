package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

public interface Serializator<T> {
    @Contract(pure = true)
    @NotNull
    List<ByteBuffer> serialize(T t);
    T deserialize(ByteBuffer bb);

    @NotNull
    default ByteBuffer serializeFlatten(T t) {
        var bbs = serialize(t);
        var sum = bbs.stream().mapToInt(Buffer::capacity).sum();
        var finalBB = ByteBuffer.allocate(sum);
        bbs.forEach(byteBuffer -> finalBB.put(byteBuffer.flip()));
        return finalBB;
    }
}
