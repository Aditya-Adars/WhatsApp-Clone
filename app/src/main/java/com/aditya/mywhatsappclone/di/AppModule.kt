package com.aditya.mywhatsappclone.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()


    @Provides
    @Singleton
    fun provideFirebaseAuthentication(): FirebaseAuth = FirebaseAuth.getInstance()
}
