package com.ahmadrd.movieexplorer.favorite.di

import android.content.Context
import com.ahmadrd.movieexplorer.di.FavoriteModuleDependencies
import com.ahmadrd.movieexplorer.favorite.ui.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }

}