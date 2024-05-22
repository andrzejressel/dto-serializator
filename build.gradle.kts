import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure

group = "pl.andrzejressel.dto"

plugins {
    id("com.palantir.git-version") version "3.0.0"
    kotlin("jvm") version "2.0.0" apply false
    id("com.vanniktech.maven.publish") version "0.26.0" apply false
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

version = if(details.isCleanTag) {
    val lastTag = details.lastTag
    if (lastTag.startsWith("v")) {
        //Release
        details.lastTag.removePrefix("v")
    } else {
        //main
        "main-SNAPSHOT"
    }
} else {
    "DEV"
}
