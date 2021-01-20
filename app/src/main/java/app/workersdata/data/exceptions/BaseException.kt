package app.workersdata.data.exceptions

class BaseException(throwable: Throwable) : Throwable(throwable.cause)