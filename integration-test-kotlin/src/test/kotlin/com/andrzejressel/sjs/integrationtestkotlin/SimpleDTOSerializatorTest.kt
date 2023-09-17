package com.andrzejressel.sjs.integrationtestkotlin

import net.jqwik.api.ForAll
import net.jqwik.api.Property
import pl.andrzejressel.sjs.serializator.Serializator

class SimpleDTOSerializatorTest : AbstractSerializatorTest<SimpleDTO>() {
    override val serializator: Serializator<SimpleDTO>
        get() = SimpleDTOSerializator.INSTANCE

    @Property
    fun test(@ForAll val1: String, @ForAll val2: List<Int>, @ForAll val3: Map<String, List<Int>>) {
        performTest(SimpleDTO(val1, val2, val3))
    }
}