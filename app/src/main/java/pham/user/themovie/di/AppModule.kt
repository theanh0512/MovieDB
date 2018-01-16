package pham.user.themovie.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pham.user.themovie.api.TheMovieService
import pham.user.themovie.util.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = arrayOf(ViewModelModule::class))
class AppModule {
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context, httpInterceptor: HttpLoggingInterceptor): OkHttpClient { // The Interceptor is then added to the client
        return OkHttpClient.Builder()
                .addInterceptor(httpInterceptor)
                .build()
    }

    @Singleton
    @Provides
    fun provideNCardService(context: Context, okHttpClient: OkHttpClient): TheMovieService {
        return Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TheMovieService::class.java)
    }
}
