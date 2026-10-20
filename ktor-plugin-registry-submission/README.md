# Kodein-DI Ktor Plugin Registry Submission

This directory contains the files needed to add Kodein-DI to the [Ktor Plugin Registry](https://github.com/ktorio/ktor-plugin-registry).

## Structure

```
org.kodein.di/
├── group.ktor.yaml           # Organization information
└── kodein-di/
    ├── versions.ktor.yaml    # Version mappings for different Ktor versions
    └── 2.0/
        ├── manifest.ktor.yaml # Plugin metadata
        ├── install.kt         # Installation code example
        └── documentation.md   # Usage documentation
```

## Submission Process

To submit this plugin to the Ktor Plugin Registry:

1. Fork the [ktor-plugin-registry](https://github.com/ktorio/ktor-plugin-registry) repository
2. Copy the `org.kodein.di` directory to `plugins/server/` in the forked repository
3. Add `kodein_di=7.31.+` to `plugins/gradle.properties` in the ktor-plugin-registry repository
4. Run `./gradlew buildRegistry` to validate the plugin files
5. Run `./gradlew buildTestProject` to generate a test project with the plugin
6. Create a pull request to the ktor-plugin-registry repository

## Files Description

### group.ktor.yaml
Contains organization information (name, URL, email) for the plugin group.

### versions.ktor.yaml
Maps Ktor version ranges to the required Kodein-DI artifacts. Currently supports Ktor 2.0+.

### manifest.ktor.yaml
Plugin metadata including:
- Plugin name and description
- VCS link (GitHub repository)
- License (MIT)
- Category (Frameworks)
- Installation configuration

### install.kt
Example installation code that will be shown in the Ktor project generator.
Sets up the basic DI configuration in a Ktor application.

### documentation.md
Comprehensive usage documentation covering:
- Basic setup
- Dependency retrieval
- Scopes (Session and Call)
- Accessing the DI container
- Sub-DI containers

## References

- [Ktor Plugin Registry Blog Post](https://blog.jetbrains.com/kotlin/2024/04/the-ktor-plugin-registry/)
- [Ktor Plugin Registry GitHub](https://github.com/ktorio/ktor-plugin-registry)
- [Ktor Plugin Registry Contributing Guide](https://github.com/ktorio/ktor-plugin-registry/blob/main/CONTRIBUTING.md)
- [Kodein-DI Documentation](https://kosi-libs.org/kodein/)
