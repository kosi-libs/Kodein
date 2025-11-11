# Ktor Plugin Registry Submission Instructions

This folder contains all required files for submitting Kodein-DI to the Ktor Plugin Registry.

## Issue Reference

This submission addresses: https://github.com/kosi-libs/Kodein/issues/453

## Files Included

1. **manifest.ktor.yaml** - Plugin metadata and configuration
2. **group.ktor.yaml** - Organization/vendor information
3. **documentation.md** - Comprehensive plugin documentation with examples
4. **install.kt** - Code snippet for generated Ktor projects
5. **versions.ktor.yaml** - Version compatibility mapping (Ktor 3.x)

## Submission Steps

### 1. Fork the Ktor Plugin Registry

```bash
# Navigate to: https://github.com/ktorio/ktor-plugin-registry
# Click "Fork" button
# Clone your fork
git clone https://github.com/YOUR_USERNAME/ktor-plugin-registry.git
cd ktor-plugin-registry
```

### 2. Create Plugin Folder

```bash
# Create the plugin folder structure
mkdir -p plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm
```

### 3. Copy Files

```bash
# Copy all files from this submission folder to the plugin folder
cp manifest.ktor.yaml plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm/
cp group.ktor.yaml plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm/
cp documentation.md plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm/
cp install.kt plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm/
cp versions.ktor.yaml plugins/server/org.kodein.di/kodein-di-framework-ktor-server-controller-jvm/
```

### 4. Commit and Push

```bash
git add plugins/server/org.kodein.di/
git commit -m "Add Kodein-DI plugin for Ktor

Kodein-DI is a straightforward dependency injection framework for Kotlin Multiplatform.
This submission includes:
- DIPlugin for global DI container management
- closestDI() pattern for dependency retrieval
- Session and call-scoped dependencies
- Sub-DI for route-specific bindings
- MVC-style controllers

Fixes: kosi-libs/Kodein#453"

git push origin main
```

### 5. Create Pull Request

1. Go to your forked repository on GitHub
2. Click "Pull Request" button
3. Title: `Add Kodein-DI plugin`
4. Description:
```markdown
## Plugin Submission: Kodein-DI

This PR adds Kodein-DI to the Ktor Plugin Registry.

### Plugin Details
- **Name**: Kodein-DI
- **Category**: Dependency Injection
- **Version**: 7.30.0
- **Ktor Version**: 3.3.2+
- **Repository**: https://github.com/kosi-libs/Kodein
- **Documentation**: https://kosi-libs.org/kodein/

### Features
- DIPlugin for global DI container management
- closestDI() pattern for retrieving dependencies anywhere
- Session-scoped and call-scoped dependencies
- Sub-DI support for route-specific bindings
- MVC-style controllers with built-in DI support

### Licensing
All code is released under the MIT License, compatible with Apache 2.0 requirements.

### Checklist
- [x] All 5 required files included (manifest, group, documentation, install, versions)
- [x] Files follow Ktor plugin registry guidelines
- [x] Documentation includes comprehensive examples
- [x] install.kt provides working code snippet
- [x] Version compatibility mapping provided
- [x] Apache 2.0 / MIT license compliance

Closes: https://github.com/kosi-libs/Kodein/issues/453
```

5. Submit the pull request
6. Wait for Ktor team review

### 6. Update Issue

Once PR is submitted, update issue #453 with:
```markdown
✅ Submission completed!

Pull request: [link to PR]

The Ktor Plugin Registry submission includes:
- manifest.ktor.yaml - Plugin metadata
- group.ktor.yaml - Organization info
- documentation.md - Comprehensive docs with examples
- install.kt - Code snippet for project generator
- versions.ktor.yaml - Version compatibility (Ktor 3.x)

Awaiting Ktor team review.
```

## Expected Timeline

- **Initial review**: 1-2 weeks
- **Feedback incorporation**: As needed
- **Final approval**: 2-4 weeks total

## Post-Approval

Once approved, Kodein-DI will appear in:
- Ktor project generator (start.ktor.io)
- IntelliJ IDEA Ktor project wizard
- Searchable in the plugin registry

## Notes

- The submission targets the **controller module** (`kodein-di-framework-ktor-server-controller-jvm`) which includes the base server module
- Category chosen: **Dependency Injection**
- Version compatibility includes **Ktor 3.x only** as specified

## Questions?

Contact: contact@kodein.net or open a discussion in the PR.
