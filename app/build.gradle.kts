plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // جديد: مطلوب لـ Dagger Hilt ومعالج التعليقات التوضيحية
    id("com.google.dagger.hilt.android") // جديد: مكون Hilt الإضافي
}

android {
    namespace = "com.example.daawahtv"
    compileSdk = 36 // من نسختك
    buildToolsVersion = "29.0.3" // من نسختك (ملاحظة: هذا الإصدار قديم، قد تحتاج لتحديثه لاحقًا)

    signingConfigs {
        create("release") {
            storeFile = file("dummy.jks")
            storePassword = project.findProperty("KEYSTORE_PASSWORD") as? String ?: "password"
            keyAlias = "daawahtv"
            keyPassword = project.findProperty("KEY_PASSWORD") as? String ?: "password"
        }
    }

    defaultConfig {
        applicationId = "com.example.daawahtv"
        minSdk = 23 // من نسختك
        targetSdk = 36 // من نسختك
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false // عادة ما تكون false في Debug
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11 // من نسختك
        targetCompatibility = JavaVersion.VERSION_11 // من نسختك
    }
    kotlinOptions {
        jvmTarget = "11" // من نسختك
    }
    buildFeatures {
        viewBinding = true // ميزة جيدة، تم تضمينها
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // تبعيات AndroidX الأساسية
    implementation(libs.androidx.core.ktx)
    implementation("androidx.appcompat:appcompat:1.7.0") // إضافة لضمان التوافق
    implementation("com.google.android.material:material:1.12.0") // من نسختك (إصدار صريح)

    // Leanback UI (لـ Android TV)
    implementation(libs.androidx.leanback)
    implementation("androidx.leanback:leanback-preference:1.2.0-alpha01") // إضافة لميزات التفضيلات إذا لزم الأمر

    // ExoPlayer (لتشغيل الفيديو)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.datasource) // من نسختك

    // Glide (لتحميل الصور)
    implementation(libs.glide)
    kapt("com.github.bumptech.glide:compiler:4.16.0") // جديد: معالج التعليقات التوضيحية لـ Glide

    // Retrofit و OkHttp (لشبكات API) - استخدام الإصدارات الصريحة من نسختك
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3") // من نسختك
    implementation("com.google.code.gson:gson:2.11.0") // جديد: تبعية Gson نفسها

    // YouTube Android Player API (لتشغيل فيديوهات يوتيوب)
    implementation("com.github.HaarigerHarald:android-youtubeExtractor:master-SNAPSHOT") // من نسختك
    // ملاحظة: إذا كان لديك ملف JAR محلي لـ YouTube Android Player API، فاستخدم:
    // implementation(files("libs/YouTubeAndroidPlayerApi.jar"))

    // Kotlin Coroutines (ضرورية للعمليات غير المتزامنة مع Hilt و LifecycleScope)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")

    // Lifecycle (ViewModel, LiveData) - ضرورية لـ LifecycleScope و ViewModels
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")

    // Dagger Hilt (لحقن التبعية) - جديد: لحل مشكلات @Inject و @Singleton
    implementation("com.google.dagger:hilt-android:2.51.1") // أحدث إصدار مستقر
    kapt("com.google.dagger:hilt-compiler:2.51.1")

    // ConstraintLayout (من نسختك)
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    // اختبارات
    testImplementation(libs.junit4)
    androidTestImplementation("androidx.test.ext:junit:1.2.1") // إضافة لاختبارات الأجهزة
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1") // إضافة لاختبارات واجهة المستخدم
}
