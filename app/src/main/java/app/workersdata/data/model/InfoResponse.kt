package app.workersdata.data.model

import com.squareup.moshi.Json

data class InfoResponse(
    @Json(name = "response") val employees: List<Employee>
)