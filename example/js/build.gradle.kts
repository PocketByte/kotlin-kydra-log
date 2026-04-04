plugins {
    id("kotlin-multiplatform")
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":example:common"))
            }
        }
        jsMain {
            dependencies {
                implementation(libs.kotlin.stdlib)
            }
        }
    }
}