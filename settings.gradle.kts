pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TextRecognition"
include(":app")
include(":nav")
include(":CaptureCamera")
include(":MyDb")
include(":mymodel")
include(":helper")
