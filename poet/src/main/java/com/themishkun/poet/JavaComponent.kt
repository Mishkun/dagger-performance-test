package com.themishkun.poet

import java.io.File

/**
 * @author themishkun on 15/09/2018.
 */
private val PACKAGE_NAME = "javasubcomponent"

fun generateJavaComponentFeature(name: String) {
    File("${getRelativePath(PACKAGE_NAME)}/di/components/Component$name.java").writeText(sampleComponentSpec(name))
    File("${getRelativePath(PACKAGE_NAME)}/di/modules/Module$name.java").writeText(sampleModuleSpec(name))
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
            package com.themishkun.$PACKAGE_NAME.di.components;

            import com.themishkun.$PACKAGE_NAME.di.modules.Module$name;
            import di.Feature$name;

            import org.jetbrains.annotations.NotNull;

            import dagger.Subcomponent;
            import di.PerFeature;

            @PerFeature
            @Subcomponent(modules = {Module$name.class})
            public interface Component$name {
                void inject(@NotNull Feature$name feature$name);
            }
""".trimIndent()

private fun sampleModuleSpec(name: String) = """
        package com.themishkun.$PACKAGE_NAME.di.modules;
        import org.jetbrains.annotations.NotNull;

        import dagger.Provides;
        import dagger.Module;
        import di.Api;
        import di.Interactor;
        import di.PerFeature;
        import di.Presenter;
        import di.Repository;

        @Module
        public class Module$name {
            @Provides
            @PerFeature
            @NotNull
            public final Repository provideRepo$name(@NotNull Api api) {
                return new Repository(api);
            }

            @Provides
            @PerFeature
            @NotNull
            public final Interactor provideInteractor$name(@NotNull Repository repo) {
                return new Interactor(repo);
            }

            @Provides
            @PerFeature
            @NotNull
            public final Presenter providePresenter$name(@NotNull Interactor interactor) {
                return new Presenter(interactor);
            }
        }
""".trimIndent()