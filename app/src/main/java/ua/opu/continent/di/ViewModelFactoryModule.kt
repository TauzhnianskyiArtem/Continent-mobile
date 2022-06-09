package ua.opu.continent.di

import dagger.Module
import dagger.Provides
import ua.opu.continent.presentation.MainViewModelFactory
import ua.opu.continent.useсase.AuthenticationUseCase
import ua.opu.continent.useсase.ChatsUseCase
import ua.opu.continent.useсase.PresenceUseCase
import ua.opu.continent.useсase.UsersUseCase
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        authUseCase: AuthenticationUseCase,
        userUseCase: UsersUseCase,
        chatsUseCase: ChatsUseCase,
        presenceUseCase: PresenceUseCase
    ): MainViewModelFactory {
        return MainViewModelFactory(authUseCase, userUseCase, chatsUseCase, presenceUseCase)
    }
}