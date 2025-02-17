package com.example.app_android.Model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("_id")
    val id: String? = null,

    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("dni")
    val dni: String,

    @Transient // Evita que Gson serialice este campo en respuestas del backend
    val password: String? = null,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("ciudad")
    val ciudad: String,

    @SerializedName("vip")
    val vip: Boolean,

    @SerializedName("avatar")
    val avatar: String? = null
)

// Modelo para la solicitud de login
data class LoginRequest(
    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String

)

// Modelo de respuesta del backend en el login
data class LoginResponse(
    @SerializedName("user")
    val user: User?, // Contiene los datos del usuario autenticado

    @SerializedName("message")
    val message: String?, // Mensaje informativo del backend

    @SerializedName("error")
    val errorMessage: String? // En caso de error, contendrá el mensaje de error
)

data class RegisterRequest(
    @SerializedName("nombre")
    val nombre: String,

    @SerializedName("apellido")
    val apellido: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password") // Incluye la contraseña en la solicitud de registro
    val password: String,

    @SerializedName("dni")
    val dni: String,

    @SerializedName("birthday")
    val birthday: String,

    @SerializedName("sex")
    val sex: String,

    @SerializedName("ciudad")
    val ciudad: String,

    @SerializedName("vip")
    val vip: Boolean,

    @SerializedName("avatar")
    val avatar: String? = null // Avatar opcional
)

// Modelo para la respuesta del backend en el registro
data class RegisterResponse(
    @SerializedName("user")
    val user: User?, // Contiene los datos del usuario registrado

    @SerializedName("message")
    val message: String?, // Mensaje informativo del backend

    @SerializedName("error")
    val errorMessage: String? // En caso de error, contendrá el mensaje de error
)