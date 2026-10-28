package com.example.apptest

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/**
 * 真机联调测试：依赖电脑上的后端 (127.0.0.1:8080，通过 adb reverse 映射)。
 * 运行前先执行: adb reverse tcp:8080 tcp:8080
 */
@RunWith(AndroidJUnit4::class)
class BackendApiTest {

    @Test
    fun registerThenSendNotify() = runBlocking {
        val api = RetrofitClient.api

        val register = api.register(RegisterRequest(teacherId = 1001, teacherName = "联调测试"))
        assertEquals("Teacher registered successfully", register.message)

        val resp = api.sendNotify(
            NotifyRequest(
                teacherId = 1001,
                strength = 2,
                displayMode = 1,
                classId = "12025001",
                targetType = 0,
                msgText = MsgText(title = "联调标题", content = "来自安卓端")
            )
        )
        assertEquals(200, resp.code)
        assertTrue(resp.data.msgId.isNotEmpty())
        assertEquals("12025001", resp.data.classId)
        assertEquals("联调标题", resp.data.msgText.title)
    }
}
