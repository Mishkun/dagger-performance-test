package com.themishkun.poet

import java.io.File

/**
 * @author themishkun on 15/09/2018.
 */
private val PACKAGE_NAME = "kotlincomponent"

fun generateComponentFeature(name: String) {
    File("${getRelativePath(PACKAGE_NAME)}/di/components/Component$name.kt").writeText(sampleComponentSpec(name))
    File("${getRelativePath(PACKAGE_NAME)}/di/modules/Module$name.kt").writeText(sampleModuleSpec(name))
    File("${getRelativePath(PACKAGE_NAME)}/features/Feature$name.kt").writeText(sampleUsageSpec(name))
}

private fun sampleUsageSpec(name: String) = """
            package di

            import javax.inject.Inject

            @PerFeature
            class Feature$name {
                @Inject
                lateinit var presenter: Presenter
            }
""".trimIndent()


private fun sampleComponentSpec(name: String) = """
            package di

            import dagger.Component

            @PerFeature
            @Component(dependencies = [MainComponent::class], modules = [Module$name::class])
            interface Component$name {
                fun inject(usage: Feature$name)
            }
""".trimIndent()

private fun sampleModuleSpec(name: String) = """
            package di

            import dagger.Module
            import dagger.Provides

            @Module
            class Module$name {
                @Provides
                @PerFeature
                fun provideRepo$name(api: Api) = Repository(api)

                @Provides
                @PerFeature
                fun provideInteractor$name(repo: Repository) = Interactor(repo)

                @Provides
                @PerFeature
                fun providePresenter$name(interactor: Interactor) = Presenter(interactor)
            }
""".trimIndent()