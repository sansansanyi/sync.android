package com.example.apptest

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

// ==================== 请求体 (对应后端 ClientSendReq) ====================

data class MsgText(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)

data class NotifyRequest(
    @SerializedName("teacher_id") val teacherId: Long,
    @SerializedName("strength") val strength: Int,            // 0~4
    @SerializedName("display_mode") val displayMode: Int,     // 0 或 1
    @SerializedName("class_id") val classId: String,          // 8 位
    @SerializedName("target_type") val targetType: Int,       // 0~3
    @SerializedName("msg_text") val msgText: MsgText,
    @SerializedName("token") val token: String = "",
    @SerializedName("display_time") val displayTime: Long = 0 // 0 由后端补当前时间
)

data class RegisterRequest(
    @SerializedName("teacher_id") val teacherId: Long,
    @SerializedName("teacher_name") val teacherName: String
)

// ==================== 响应体 ====================

data class BroadcastEvent(
    @SerializedName("msg_id") val msgId: String,              // 后端按字符串下发，防精度丢失
    @SerializedName("teacher_id") val teacherId: Long,
    @SerializedName("teacher_name") val teacherName: String,
    @SerializedName("created_time") val createdTime: Long,
    @SerializedName("display_time") val displayTime: Long,
    @SerializedName("strength") val strength: Int,
    @SerializedName("display_mode") val displayMode: Int,
    @SerializedName("class_id") val classId: String,
    @SerializedName("target_type") val targetType: Int,
    @SerializedName("msg_text") val msgText: MsgText
)

data class NotifyResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: BroadcastEvent
)

data class RegisterResponse(
    @SerializedName("message") val message: String
)

interface ApiService {
    @POST("api/notify")
    suspend fun sendNotify(@Body req: NotifyRequest): NotifyResponse

    @POST("register")
    suspend fun register(@Body req: RegisterRequest): RegisterResponse
}
