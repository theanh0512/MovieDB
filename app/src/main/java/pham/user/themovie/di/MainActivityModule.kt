package pham.user.themovie.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pham.user.themovie.ui.MainActivity

/**
 * Define MainActivity-specific dependencies here.
 */
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = arrayOf(FragmentMainBuildersModule::class))
    abstract fun contributeMainActivity(): MainActivity
}