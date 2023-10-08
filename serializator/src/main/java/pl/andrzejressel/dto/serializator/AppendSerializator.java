package pl.andrzejressel.dto.serializator;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.List;

public interface AppendSerializator<T> extends Serializator<T> {
    @Contract(pure = true)
    @NotNull
    List<ByteBuffer> serializeToList(T t);

    @Contract(pure = true)
    @NotNull
    @Override
    default ByteBuffer serialize(T t) {
        var bbs = serializeToList(t);
        var sum = bbs.stream().mapToInt(Buffer::capacity).sum();
        var finalBB = ByteBuffer.allocate(sum);
        bbs.forEach(byteBuffer -> finalBB.put(byteBuffer.rewind()));
        return finalBB;
    }
}
