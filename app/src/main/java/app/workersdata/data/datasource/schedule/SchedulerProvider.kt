package app.workersdata.data.datasource.schedule

import io.reactivex.rxjava3.core.Scheduler

/**
 * Rx Scheduler Provider
 */
interface SchedulerProvider {
    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}