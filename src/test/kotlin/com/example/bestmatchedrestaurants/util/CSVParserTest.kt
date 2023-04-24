package com.example.bestmatchedrestaurants.util

import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class CSVParserTest {

    @Test
    fun `parse success`() {
        val output = CSVParser("test1,test2\r\n" +
                "1,2\r\n" +
                "3,4").parse()

        assert(output.size == 2)

        assert(output[0]["test1"] == "1")
        assert(output[0]["test2"] == "2")
        assert(output[1]["test1"] == "3")
        assert(output[1]["test2"] == "4")
    }

    @Test
    fun `parse success empty`() {
        val output = CSVParser("").parse()

        assert(output.size == 0)
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