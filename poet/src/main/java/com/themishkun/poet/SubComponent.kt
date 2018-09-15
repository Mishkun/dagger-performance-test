package com.themishkun.poet

import java.io.File

/**
 * @author themishkun on 15/09/2018.
 */

private val PACKAGE_NAME = "kotlinsubcomponent"

fun generateSubcomponentFeature(name: String) {
    File("${getRelativePath(PACKAGE_NAME)}/di/components/Component$name.kt").writeText(sampleSubcomponentSpec(name))
    File("${getRelativePath(PACKAGE_NAME)}/di/modules/Module$name.kt").writeText(sampleModuleSpec(name))
    File("${getRelativePath(PACKAGE_NAME)}/features/Feature$name.kt").writeText(sampleUsageSpec(name))
}

fun generateMainSubcomponent(names: List<String>) =
        File("${getRelativePath(PACKAGE_NAME)}/di/main/MainComponent.kt").writeText(mainComponentSpec(names))

private fun mainComponentSpec(names: List<String>) = """
        package di

        import dagger.Component
        import javax.inject.Singleton

        @Component(modules = [MainModule::class])
        @Singleton
        interface MainComponent {
        """ + names.joinToString(separator = "\n") { name ->
        """
        fun subcomponent$name(module: Module$name): Subcomponent$name
        """.trimIndent()
        } + """
        }
""".trimIndent()

private fun sampleUsageSpec(name: String) = """
            package di

            import javax.inject.Inject

            @PerFeature
            class Feature$name {
                @Inject
                lateinit var presenter: Presenter
            }
""".trimIndent()


private fun sampleSubcomponentSpec(name: String) = """
            package di

            import dagger.Subcomponent

            @PerFeature
            @Subcomponent(modules = [Module$name::class])
            interface Subcomponent$name {
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