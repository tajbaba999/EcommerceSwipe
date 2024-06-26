plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.e_commerce_swipe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.e_commerce_swipe"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
//        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    implementation("androidx.activity:activity:1.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.2.1")
    implementation("org.imaginativeworld.oopsnointernet:oopsnointernet:2.0.0")

    implementation("com.google.android.material:material:1.12.0")

    implementation("com.google.firebase:firebase-bom:33.0.0")

    //Splash API
    implementation("androidx.core:core-splashscreen:1.0.0")

    //Circlur image
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //material ui
    implementation("com.google.android.material:material:1.5.0")
    //Image pciker lin

    implementation("com.github.dhaval2404:imagepicker:2.1")


            //firebase auth
    implementation("com.google.android.gms:play-services-auth:20.2.0")

    //firebase analytics
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
    implementation("com.google.firebase:firebase-analytics")

    //shrimmer
    implementation("com.facebook.shimmer:shimmer:0.5.0")

}
