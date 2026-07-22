# How to Submit Kodein-DI to Ktor Plugin Registry

This document provides step-by-step instructions for submitting the Kodein-DI plugin to the official Ktor Plugin Registry.

## Prerequisites

- A GitHub account
- Git installed on your machine
- Gradle (the ktor-plugin-registry includes a gradlew wrapper)

## Step-by-Step Instructions

### 1. Fork the Ktor Plugin Registry Repository

1. Go to https://github.com/ktorio/ktor-plugin-registry
2. Click the "Fork" button in the top right
3. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR-USERNAME/ktor-plugin-registry.git
   cd ktor-plugin-registry
   ```

### 2. Copy the Plugin Files

Copy the plugin files from this repository to your fork:

```bash
# From the Kodein repository root
cp -r ktor-plugin-registry-submission/org.kodein.di /path/to/ktor-plugin-registry/plugins/server/
```

### 3. Add Version Variable

Edit `plugins/gradle.properties` in the ktor-plugin-registry and add the Kodein-DI version:

```properties
kodein_di=7.31.+
```

Add this line in alphabetical order with the other version properties.

### 4. Validate the Plugin

Run the build to ensure everything is configured correctly:

```bash
./gradlew buildRegistry
```

This will compile and validate all plugins including your new Kodein-DI plugin.

### 5. Generate a Test Project (Optional)

You can generate a test project to verify the plugin works as expected:

```bash
./gradlew buildTestProject
```

This creates a test Ktor project in the `build/test-project` directory.

### 6. Commit and Push

```bash
git add plugins/server/org.kodein.di
git add plugins/gradle.properties
git commit -m "Add Kodein-DI plugin to registry"
git push origin main
```

### 7. Create Pull Request

1. Go to your forked repository on GitHub
2. Click "New Pull Request"
3. Ensure the base repository is `ktorio/ktor-plugin-registry` and base branch is `main`
4. Add a descriptive title: "Add Kodein-DI dependency injection framework"
5. In the description, include:
   - Brief description of Kodein-DI
   - Link to documentation: https://kosi-libs.org/kodein/
   - Any relevant information about compatibility

### 8. Wait for Review

The Ktor team will review your submission. They may:
- Request changes to the plugin configuration
- Test the plugin generation
- Merge the pull request

Once merged, the Kodein-DI plugin will appear in:
- The Ktor Project Generator at https://start.ktor.io
- The IntelliJ IDEA Ktor plugin

## Files Submitted

The following files are included in the submission:

```
org.kodein.di/
├── group.ktor.yaml           # Organization metadata
└── kodein-di/
    ├── versions.ktor.yaml    # Dependency versions for different Ktor versions
    └── 2.0/
        ├── manifest.ktor.yaml # Plugin metadata (name, description, category)
        ├── install.kt         # Installation code snippet
        └── documentation.md   # Usage documentation
```

## Additional Notes

- The plugin supports Ktor 2.0 and higher
- Maven artifact: `org.kodein.di:kodein-di-framework-ktor-server-jvm`
- Version pattern: `7.31.+` (uses latest 7.31.x release)
- Category: Frameworks
- License: MIT

## Troubleshooting

If the build fails with "Unresolved version variable: kodein_di":
- Ensure you added `kodein_di=7.31.+` to `plugins/gradle.properties`

If the plugin doesn't validate:
- Check that all YAML files are properly formatted
- Ensure the directory structure matches: `plugins/server/org.kodein.di/kodein-di/2.0/`
- Verify that all required files are present (manifest.ktor.yaml, install.kt, documentation.md)

## References

- [Ktor Plugin Registry](https://github.com/ktorio/ktor-plugin-registry)
- [Ktor Plugin Registry Blog Post](https://blog.jetbrains.com/kotlin/2024/04/the-ktor-plugin-registry/)
- [Kodein-DI Documentation](https://kosi-libs.org/kodein/)
- [Kodein-DI GitHub](https://github.com/kosi-libs/Kodein)
