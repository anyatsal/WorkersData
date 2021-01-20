package app.workersdata.data.mapper

import app.workersdata.data.exceptions.BaseException
import app.workersdata.data.exceptions.NotFoundException
import retrofit2.HttpException
import java.net.HttpURLConnection

class ApiErrorMapper {
    fun map(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
                    else -> BaseException(throwable)
                }
            }
            else -> BaseException(throwable)
        }
    }
}