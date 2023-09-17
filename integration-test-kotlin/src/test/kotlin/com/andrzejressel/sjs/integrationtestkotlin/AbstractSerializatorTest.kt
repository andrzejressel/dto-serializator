package com.andrzejressel.sjs.integrationtestkotlin

import org.assertj.core.api.Assertions
import pl.andrzejressel.sjs.serializator.Serializator

abstract class AbstractSerializatorTest<T> {
    protected abstract val serializator: Serializator<T>
    protected fun performTest(value: T) {
        val serializator = serializator
        val result = serializator.serializeFlatten(value)
        Assertions.assertThat(result.hasRemaining()).isFalse()
        Assertions.assertThat(value).isEqualTo(serializator.deserialize(result.flip()))
    }
}
