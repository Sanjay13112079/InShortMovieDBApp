package com.inshort.moviedb.data.remote.datasource

import retrofit2.HttpException
import java.io.IOException

object NetworkDataSource {

    suspend fun <T> execute(
        apiCall: suspend () -> T
    ): NetworkResult<T> {
        return try {
            val response = apiCall()
            NetworkResult.Success(response)
        } catch (e: HttpException) {
            NetworkResult.Error(
                message = e.message(),
                code = e.code()
            )
        } catch (e: IOException) {
            NetworkResult.Error(
                message = "Network error. Please check your internet connection."
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                message = e.localizedMessage ?: "Something went wrong"
            )
        }
    }
}
