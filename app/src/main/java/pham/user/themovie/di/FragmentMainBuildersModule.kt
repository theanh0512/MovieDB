package pham.user.themovie.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pham.user.themovie.ui.DetailFragment
import pham.user.themovie.ui.MainFragment

@Module
abstract class FragmentMainBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

}