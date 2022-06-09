package ua.opu.continent.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import ua.opu.continent.database.repository.ChatsRepository
import ua.opu.continent.database.repository.PresenceRepository
import ua.opu.continent.database.repository.UsersRepository
import ua.opu.continent.useсase.AuthenticationUseCase
import ua.opu.continent.useсase.ChatsUseCase
import ua.opu.continent.useсase.PresenceUseCase
import ua.opu.continent.useсase.UsersUseCase
import ua.opu.continent.useсase.impl.AuthenticationUseCaseFirebase
import ua.opu.continent.useсase.impl.ChatsUseCaseFirebase
import ua.opu.continent.useсase.impl.PresenceUseCaseFirebase
import ua.opu.continent.useсase.impl.UsersUseCaseFirebase
import ua.opu.continent.useсase.logging.AuthenticationUserCaseLog
import ua.opu.continent.useсase.logging.ChatsUserCaseLog
import ua.opu.continent.useсase.logging.PresenceUserCaseLog
import ua.opu.continent.useсase.logging.UsersUserCaseLog
import javax.inject.Singleton

@Module
class UseCaseModule(private val tag: String?) {

    @Provides
    @Singleton
    fun provideAuthenticationUseCase(auth: FirebaseAuth): AuthenticationUseCase {
        return AuthenticationUserCaseLog(AuthenticationUseCaseFirebase(auth), tag)
    }

    @Provides
    @Singleton
    fun provideUsersUseCase(
        usersRepository: UsersRepository,
        storage: FirebaseStorage
    ): UsersUseCase {
        return UsersUserCaseLog(UsersUseCaseFirebase(usersRepository, storage), tag)
    }

    @Provides
    @Singleton
    fun provideChatsUseCase(
        chatsRepository: ChatsRepository,
        storage: FirebaseStorage
    ): ChatsUseCase {
        return ChatsUserCaseLog(ChatsUseCaseFirebase(chatsRepository, storage), tag)
    }

    @Provides
    @Singleton
    fun providePresenceUseCase(presenceRepository: PresenceRepository): PresenceUseCase {
        return PresenceUserCaseLog(PresenceUseCaseFirebase(presenceRepository), tag)
    }


}