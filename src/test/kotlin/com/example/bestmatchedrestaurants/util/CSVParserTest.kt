package com.example.bestmatchedrestaurants.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CSVParserTest {
    @Test
    fun `parse success`() {
        val output = CSVParser("test1,test2\r\n" +
                "1,2\r\n" +
                "3,4").parse()

        assertThat(output.size).isEqualTo(2)

        assertThat(output[0]["test1"]).isEqualTo("1")
        assertThat(output[0]["test2"]).isEqualTo("2")
        assertThat(output[1]["test1"]).isEqualTo("3")
        assertThat(output[1]["test2"]).isEqualTo("4")
    }

    @Test
    fun `parse success empty`() {
        val output = CSVParser("").parse()

        assertThat(output.size).isEqualTo(0)
    }

    @Test
    fun `parse fail 1`() {
        assertFailsWith<IllegalArgumentException> {
            CSVParser(
                "test1,test2\r\n" +
                        "1,2,3\r\n" +
                        "3,4"
            ).parse()
        }
    }

    @Test
    fun `parse fail 2`() {
        assertFailsWith<IllegalArgumentException> {
            CSVParser(
                "test1,test2,test3\r\n" +
                        "1,2,3\r\n" +
                        "3,4"
            ).parse()
        }
    }
}