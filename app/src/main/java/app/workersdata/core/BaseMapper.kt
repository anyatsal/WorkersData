package app.workersdata.core

interface BaseMapper<in A : Any, out B : Any> {
    fun map(from: A): B
}