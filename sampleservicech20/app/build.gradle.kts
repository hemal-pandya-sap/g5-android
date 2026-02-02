plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
        alias(libs.plugins.sap.odata.android)
    alias(libs.plugins.kotlin.compose)
}

configure<com.sap.odata.android.gradle.ODataPluginExtension> {
    verbose = true
    services {
        //connection id: com.sap.edm.sampleservice.v4
    create("espmcontainer") {
        schemaFile = file("src/main/res/raw/com_sap_edm_sampleservice_v4.xml")
        packageName = "com.sap.cloud.android.odata.espmcontainer"
        serviceClass = "ESPMContainer"
        additionalParameters = listOf("-kotlin",  "-offline" )
    }
   }
}

android {
    compileSdk = 36
    defaultConfig {
        multiDexEnabled = true
        applicationId = "com.company.sampleservicech20"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["clearPackageData"] = "true"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    namespace = "com.company.sampleservicech20"

    flavorDimensions += "appStore"
    productFlavors {
        create("googlePlayStoreforGlobalMarket") {
            dimension = "appStore"
            isDefault = true
        }
        create("tencentAppStoreforChinaMarket") {
            dimension = "appStore"
        }
    }
}

configurations.configureEach {
    exclude(group = "org.apache.httpcomponents", module = "httpclient")
    exclude(group = "commons-logging", module = "commons-logging")
    exclude(group = "opengl-api")
    exclude(group = "xmlParserAPIs")
    exclude(group = "xpp3")
    exclude(group = "android")
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsizeclass)

    // Android framework dependencies
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.runtime.livedata)
    implementation(libs.navigation.compose)
    implementation(libs.activity.compose)

    // Android Architecture Components
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.livedata.ktx)

    implementation(libs.datastore.preferences)
    implementation(libs.security.crypto)
    implementation(libs.paging.common.ktx)
    implementation(libs.paging.compose)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.work.runtime.ktx)
    implementation(libs.guava.android)

    // utils
    // https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j
    implementation(libs.zip4j)

    // JUnit dependency
    testImplementation(libs.junit)

    // SAP Cloud Android SDK dependencies
    implementation(libs.sap.cloud.foundation)
    implementation(libs.sap.cloud.foundation.app.security)
    implementation(libs.sap.cloud.onboarding.compose)
    implementation(libs.sap.cloud.flows.compose)
    implementation(libs.sap.cloud.fiori.composable.theme)
    implementation(libs.sap.cloud.fiori.compose.ui)
    implementation(libs.sap.cloud.permission.request.tracker)
    implementation(libs.sap.cloud.odata){
        artifact {
            classifier = "kotlin"
            type = "aar"
        }
    }
    implementation(libs.sap.cloud.offline.odata){
        artifact {
            classifier = "kotlin"
            type = "aar"
        }
    }

    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)

    // For UI testing
    androidTestImplementation(libs.espressodoppio)
    androidTestImplementation(libs.uiautomator)
    androidTestUtil(libs.androidx.test.orchestrator)

    //Java code also need this library to convert java class to kotlin class
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.reflect)
}
