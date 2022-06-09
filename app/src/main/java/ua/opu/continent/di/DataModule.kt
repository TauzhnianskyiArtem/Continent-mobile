package ua.opu.continent.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import ua.opu.continent.database.dao.ChatsDao
import ua.opu.continent.database.dao.PresenceDao
import ua.opu.continent.database.dao.UsersDao
import ua.opu.continent.database.dao.impl.ChatsDaoFirebase
import ua.opu.continent.database.dao.impl.PresenceDaoFirebase
import ua.opu.continent.database.dao.impl.UsersDaoFirebase
import ua.opu.continent.database.repository.ChatsRepository
import ua.opu.continent.database.repository.PresenceRepository
import ua.opu.continent.database.repository.UsersRepository
import javax.inject.Singleton

@Module
class DataModule {


    @Provides
    @Singleton
    fun provideUsersDao(database: FirebaseDatabase): UsersDao {
        return UsersDaoFirebase(database)
    }

    @Provides
    @Singleton
    fun provideChatsDao(database: FirebaseDatabase): ChatsDao {
        return ChatsDaoFirebase(database)
    }

    @Provides
    @Singleton
    fun providePresenceDao(database: FirebaseDatabase): PresenceDao {
        return PresenceDaoFirebase(database)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(usersDao: UsersDao): UsersRepository {
        return UsersRepository(usersDao)
    }

    @Provides
    @Singleton
    fun provideChatsRepository(chatsDao: ChatsDao): ChatsRepository {
        return ChatsRepository(chatsDao)
    }

    @Provides
    @Singleton
    fun providePresenceRepository(presenceDao: PresenceDao): PresenceRepository {
        return PresenceRepository(presenceDao)
    }

}