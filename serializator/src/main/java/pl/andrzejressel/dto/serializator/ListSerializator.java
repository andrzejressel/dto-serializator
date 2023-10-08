package pl.andrzejressel.dto.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListSerializator<T> implements AppendSerializator<List<T>> {
    private final Serializator<T> instance;

    public ListSerializator(Serializator<T> instance) {
        this.instance = instance;
    }

    @Override
    public @NotNull List<ByteBuffer> serializeToList(List<T> t) {
        var sizeBB = ByteBuffer.allocate(4);
        sizeBB.putInt(t.size());

        var elements = t.stream().map(instance::serialize).collect(Collectors.toList());
        var finalBBs = new ArrayList<ByteBuffer>();
        finalBBs.add(sizeBB);
        finalBBs.addAll(elements);
        return finalBBs;
    }

    @Override
    public List<T> deserialize(ByteBuffer bb) {
        var size = bb.getInt();
        var elements = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            elements.add(instance.deserialize(bb));
        }
        return elements;
    }
}
