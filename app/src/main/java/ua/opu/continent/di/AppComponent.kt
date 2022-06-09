package ua.opu.continent.di

import dagger.Component
import ua.opu.continent.presentation.MainActivity
import ua.opu.continent.presentation.fragment.ChatFragment
import ua.opu.continent.presentation.fragment.MainFragment
import ua.opu.continent.presentation.fragment.OTPFragment
import ua.opu.continent.presentation.fragment.SetupProfileFragment
import javax.inject.Singleton

@Component(modules = [ViewModelFactoryModule::class, UseCaseModule::class, DataModule::class, FireBaseModule::class])
@Singleton
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(otpFragment: OTPFragment)
    fun inject(setupProfileFragment: SetupProfileFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(chatFragment: ChatFragment)
}