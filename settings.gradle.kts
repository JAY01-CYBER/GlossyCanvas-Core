/*
 * ╭────────────────────────────────────────────╮
 * │             Glossy Canvas Core             │
 * │--------------------------------------------│
 * │  Licensed under the GNU GPL v3.0           │
 * │  Crafted for expressive music experience   │
 * ╰────────────────────────────────────────────╯
 */

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

rootProject.name = "GlossyCanvas-Core"
// अगर तुम लाइब्रेरी का कोड 'core' नाम के फोल्डर में रख रहे हो, तो नीचे वाली लाइन यूज़ करना:
// include(":core") 
