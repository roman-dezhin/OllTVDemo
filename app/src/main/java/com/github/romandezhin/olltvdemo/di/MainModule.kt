package com.github.romandezhin.olltvdemo.di

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import com.github.romandezhin.olltvdemo.data.model.mapper.ProgramMapper
import com.github.romandezhin.olltvdemo.data.net.ProgramService
import com.github.romandezhin.olltvdemo.data.repository.ProgramRepositoryImpl
import com.github.romandezhin.olltvdemo.data.repository.datasource.ProgramCloudDataStore
import com.github.romandezhin.olltvdemo.domain.interactor.ProgramInteractor
import com.github.romandezhin.olltvdemo.domain.interactor.ProgramInteractorImpl
import com.github.romandezhin.olltvdemo.domain.repository.ProgramRepository

object MainModule {

    private var programRepository: ProgramRepository? = null
    private var programInteractor: ProgramInteractor? = null
    private var serialNumber: String? = null

    fun initialize(app: Application) {
        serialNumber = getSerialNumber(app)
    }

    fun getProgramInteractorImpl(): ProgramInteractor {
        if (programInteractor == null) {
            programInteractor = makeProgramInteractor(getProgramRepository())
        }
        return programInteractor!!
    }

    private fun makeProgramInteractor(repository: ProgramRepository) =
        ProgramInteractorImpl(repository)

    private fun getProgramRepository(): ProgramRepository {
        if (programRepository == null) {
            programRepository = ProgramRepositoryImpl(getProgramCloudDataStore(), ProgramMapper())
        }
        return programRepository!!
    }

    private fun getProgramCloudDataStore() =
        ProgramCloudDataStore(
            NetworkModule.connectionManager,
            NetworkModule.getService(ProgramService::class.java),
            serialNumber.orEmpty()
        )

    @SuppressLint("HardwareIds")
    private fun getSerialNumber(app: Application): String =
        Settings.Secure.getString(app.contentResolver, Settings.Secure.ANDROID_ID)
}
