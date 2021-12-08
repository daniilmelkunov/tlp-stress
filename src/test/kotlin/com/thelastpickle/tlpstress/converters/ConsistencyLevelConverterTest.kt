package com.thelastpickle.tlpstress.converters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import shaded.com.scylladb.cdc.driver3.driver.core.ConsistencyLevel

import kotlin.test.assertFailsWith

internal class ConsistencyLevelConverterTest {

    lateinit var converter: ConsistencyLevelConverter

    @BeforeEach
    fun setUp() {
        converter = ConsistencyLevelConverter()
    }

    @Test
    fun convert() {
        assertThat(converter.convert("LOCAL_ONE")).isEqualTo(ConsistencyLevel.LOCAL_ONE)
        assertThat(converter.convert("LOCAL_QUORUM")).isEqualTo(ConsistencyLevel.LOCAL_QUORUM)
    }

    @Test
    fun convertAndFail() {
        assertFailsWith<java.lang.IllegalArgumentException> {val cl = converter.convert("LOCAL")}
    }
}