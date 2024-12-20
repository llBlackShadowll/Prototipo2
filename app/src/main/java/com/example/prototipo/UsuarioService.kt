package com.example.prototipo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioService {
    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuario): Call<Usuario>

    @POST("usuarios/login")
    fun loginUsuario(@Body usuario: Usuario): Call<Usuario>
}