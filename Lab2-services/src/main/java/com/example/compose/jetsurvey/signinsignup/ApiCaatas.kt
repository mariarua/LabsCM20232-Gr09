package com.example.compose.jetsurvey.signinsignup


import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

data class ImageCatData(
    @SerializedName("_id") val idImage: String,
    @SerializedName("mimetype") val urlImage: String,
    @SerializedName("size") val sizeImage: Int,
    @SerializedName("tags") val tagsImage: List<String>,

    )

interface ImageCatsApi {
    @GET("cats")
    suspend fun getAllCats(): List<ImageCatData>

    @GET("cat/{tag}")
    suspend fun getCatByTag(@Path("tag") tag: String): ImageCatData

    @GET("cat/{tag}/says/{text}")
    suspend fun getCatWithText(@Path("tag") tag: String, @Path("text") text: String): ImageCatData

    @GET("cat/gif/says/{text}")
    suspend fun getCatGifWithText(@Path("text") text: String): ImageCatData



}


object ImageCatApiService {
    private const val BASE_URL = "https://cataas.com/"
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val imageCatsApi: ImageCatsApi = retrofit.create(ImageCatsApi::class.java)

}
