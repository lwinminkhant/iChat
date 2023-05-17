package com.lmkhant.ichat

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        println(MainActivity.mediaType)
    }

    @Throws(Exception::class)
    fun test() {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        val server = MockWebServer()

        // Schedule some responses.
        server.enqueue(MockResponse().setBody("Hello"))

        // Start the server.
        server.start()

        // Ask the server for its URL. You'll need this to make HTTP requests.
        val baseUrl = server.url("/v1/chat/")

        // Exercise your application code, which should make those HTTP requests.
        // Responses are returned in the same order that they are enqueued.
        
        val chat = Message("Hello", Message.SEND_BY_OTHER)


        assertEquals(
            """
                Hello
            """.trimIndent(), chat.message
        )

        // Shut down the server. Instances cannot be reused.
        server.shutdown()
    }
}