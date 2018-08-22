plugins {
    id("kodein-common")
}

kodeinPublication {
    upload {
        name = "Kodein-DI-Core-Common"
        description = "KODEIN Dependency Injection Core Commons"
        repo = "Kodein-DI"
    }
}

setProperty("archivesBaseName", "Kodein_DI_Core_Common")
