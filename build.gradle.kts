buildscript {
    dependencies {
        classpath(BuildPlugins.androidGradlePlugin)
        classpath(BuildPlugins.kotlinGradlePlugin)
    }

    repositories {
        mavenCentral()
        jcenter()
        google()
    }
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        google()
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}