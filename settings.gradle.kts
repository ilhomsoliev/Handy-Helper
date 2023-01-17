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
include(":features:settings")
include(":features:pick_theme")
include(":features:goals")
include(":features:chores")
include(":features:tracker")
include(":features:notes")
include(":features:budget")
