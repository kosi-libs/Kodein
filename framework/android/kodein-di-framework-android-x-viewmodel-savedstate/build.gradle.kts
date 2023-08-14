plugins {
    kodein.library.android
}

dependencies {
    api(projects.framework.android.kodeinDiFrameworkAndroidX)

    implementation(libs.android.x.appcompat)
    implementation(libs.android.x.fragment.ktx)
    implementation(libs.android.x.lifecycle.viewmodel.ktx)
    implementation(libs.android.x.lifecycle.viewmodel.savedstate)
}

android {
    namespace = "org.kodein.di.android.x.viewmodel.savedstate"
}

kodeinUpload {
    name = "Kodein-Framework-AndroidX-ViewModel-SavedState"
    description = "Kodein extensions for AndroidX ViewModel with SavedStateHandle"
}
