package app.workersdata.core

import org.koin.core.module.Module

interface ModuleProvider {
    fun modules(): List<Module>
}