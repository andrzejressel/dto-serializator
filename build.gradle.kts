import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

group = "pl.andrzejressel.dto"

plugins {
    id("com.palantir.git-version") version "3.0.0"
    kotlin("jvm") version "1.9.10" apply false
    id("com.vanniktech.maven.publish") version "0.25.3" apply false
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

version = if(details.isCleanTag) {
    details.lastTag.removePrefix("v")
} else {
    "DEV"
}
