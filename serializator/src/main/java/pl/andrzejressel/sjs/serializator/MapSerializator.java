package pl.andrzejressel.sjs.serializator;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapSerializator<K, V> implements AppendSerializator<Map<K, V>> {
    private final Serializator<K> keySerializator;
    private final Serializator<V> valueSerializator;

    public MapSerializator(Serializator<K> keySerializator, Serializator<V> valueSerializator) {
        this.keySerializator = keySerializator;
        this.valueSerializator = valueSerializator;
    }

    @Override
    public @NotNull List<ByteBuffer> serializeToList(Map<K, V> m) {
        var sizeBB = ByteBuffer.allocate(4);
        sizeBB.putInt(m.size());
        var finalBBs = new ArrayList<ByteBuffer>(m.size() * 2);
        finalBBs.add(sizeBB);

        m.forEach((k, v) -> {
            finalBBs.add(keySerializator.serialize(k));
            finalBBs.add(valueSerializator.serialize(v));
        });

        return finalBBs;
    }

    @Override
    public Map<K, V> deserialize(ByteBuffer bb) {
        var size = bb.getInt();
        var map = new HashMap<K, V>();
        for (int i = 0; i < size; i++) {
            map.put(keySerializator.deserialize(bb), valueSerializator.deserialize(bb));
        }
        return map;
    }
}
