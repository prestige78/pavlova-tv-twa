// =============================================================
// Project: Pavlova T.V.
// Файл: build.gradle.kts (Module :app)
// Назначение: конфигурация сборки приложения.
// =============================================================
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

android {
    // ---------------------------------------------------------
    // namespace — базовый пакет для генерации R и BuildConfig.
    // ---------------------------------------------------------
    namespace = "ru.juristspb78.app"

    // ---------------------------------------------------------
    // compileSdk — против какого Android API компилируем.
    // 37 обязателен для androidx.core:1.19.0.
    // ---------------------------------------------------------
    compileSdk = 37

    // ---------------------------------------------------------
    // signingConfigs — настройка подписи для release-сборки.
    // Без этого assembleRelease не создаёт подписанный APK.
    // ---------------------------------------------------------
    signingConfigs {
        create("release") {
            // ✅ Читаем из local.properties (не попадает в Git)
            val localProps = Properties().apply {
                val f = rootProject.file("local.properties")
                if (f.exists()) f.inputStream().use { load(it) }
            }

            storeFile = file(localProps.getProperty("RELEASE_STORE_FILE") ?: "../../keys/jurist78.jks")
            storePassword = localProps.getProperty("RELEASE_STORE_PASSWORD") ?: ""
            keyAlias = localProps.getProperty("RELEASE_KEY_ALIAS") ?: "jurist78"
            keyPassword = localProps.getProperty("RELEASE_KEY_PASSWORD") ?: ""
        }
    }

    defaultConfig {
        // -----------------------------------------------------
        // applicationId — УНИКАЛЬНЫЙ ID приложения на устройстве
        // и в RuStore. После публикации НЕ меняется.
        // -----------------------------------------------------
        applicationId = "ru.juristspb78.app"

        // -----------------------------------------------------
        // minSdk — минимальная версия Android.
        // API 24 = Android 7.0. Покрывает ~98% устройств.
        // -----------------------------------------------------
        minSdk = 24

        // -----------------------------------------------------
        // targetSdk — на какое поведение Android рассчитано.
        // -----------------------------------------------------
        targetSdk = 36

        // versionCode — внутренний номер версии.
        // versionName — версия, которую видит пользователь.
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // ---------------------------------------------------------
            // Подключаем signingConfig к release-сборке.
            // Теперь assembleRelease создаст подписанный APK.
            // ---------------------------------------------------------
            signingConfig = signingConfigs.getByName("release")
        }
    }

    // ---------------------------------------------------------
    // compileOptions — версия Java для компиляции.
    // 11 совместим с AGP 9.1.1.
    // ---------------------------------------------------------
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ---------- AndroidX ----------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ---------- TWA ----------
    // android-browser-helper — содержит LauncherActivity для TWA.
    // Хостится на Google Maven, который уже подключён в settings.gradle.kts.
    implementation(libs.androidbrowserhelper)

    // ---------- Тесты ----------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}