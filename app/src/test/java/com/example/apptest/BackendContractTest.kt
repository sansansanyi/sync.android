package com.example.apptest

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * 本机联调测试：直接命中 127.0.0.1:8080 上的后端 (Windows 侧已运行)。
 * 验证 Retrofit 模型/序列化与后端契约一致。
 */
class BackendContractTest {

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
