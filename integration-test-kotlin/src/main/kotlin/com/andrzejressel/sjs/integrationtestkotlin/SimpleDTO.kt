package com.andrzejressel.sjs.integrationtestkotlin

import pl.andrzejressel.sjs.serializator.GenerateSerializator

@GenerateSerializator
data class SimpleDTO(val a: String, val b: List<Int>, val c: Map<String, List<Int>>)
