package ua.opu.continent

import android.app.Application
import ua.opu.continent.di.AppComponent
import ua.opu.continent.di.DaggerAppComponent
import ua.opu.continent.di.UseCaseModule
import ua.opu.continent.presentation.MainViewModel

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .useCaseModule(UseCaseModule(MainViewModel.TAG_FOR_LOGS))
            .build()
    }
}