package app.workersdata.data.datasource.api

import app.workersdata.data.datasource.schedule.SchedulerProvider
import app.workersdata.data.model.InfoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET

class CompanyService(
    private val retrofit: Retrofit,
    private val schedulerProvider: SchedulerProvider
) {
    private val api: Api by lazy<Api> {
        retrofit.create(Api::class.java)
    }

    fun getInfo(): Single<InfoResponse> = api.getInfo()
        .subscribeOn(schedulerProvider.io())

    private interface Api {
        @GET("/65gb/static/raw/master/testTask.json")
        fun getInfo(): Single<InfoResponse>
    }
}