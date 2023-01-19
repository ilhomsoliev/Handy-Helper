pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "Handy Helper"
include (":app")
include(":data:local")
include(":data:model")
include(":data:repository")
include(":domain")
include(":features:notes")
include(":core")
