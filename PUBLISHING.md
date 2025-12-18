# Publishing to npm

## Pre-Publishing Checklist

✅ **Version Updated**: Changed from `0.0.6` to `0.1.0` (Capacitor 8 support)
✅ **Build Verified**: Plugin builds successfully
✅ **Dependencies Updated**: All Capacitor dependencies updated to v8.0.0
✅ **iOS Implementation**: Updated with shareImage and sharePdf methods
✅ **Android Implementation**: Compatible with Capacitor 8

## Publishing Steps

### 1. Login to npm

If you're not logged in, login to npm:

```bash
npm login
```

You'll be prompted for:
- Username
- Password
- Email
- OTP (if 2FA is enabled)

### 2. Verify You're Logged In

```bash
npm whoami
```

This should display your npm username.

### 3. Check Package Name Availability

Make sure the package name `share-to-whatsapp` is available or that you have access to publish to it:

```bash
npm view share-to-whatsapp
```

If the package exists, make sure you're the owner or have publish access.

### 4. Final Build

The `prepublishOnly` script will automatically run before publishing, but you can manually build to verify:

```bash
npm run build
```

### 5. Dry Run (Optional but Recommended)

Test the publish process without actually publishing:

```bash
npm publish --dry-run
```

This will show you what files will be included in the package.

### 6. Publish to npm

Publish the package:

```bash
npm publish
```

For a scoped package or to publish publicly (if package is private):

```bash
npm publish --access public
```

### 7. Verify Publication

After publishing, verify the package is available:

```bash
npm view share-to-whatsapp
```

Or check on npmjs.com: https://www.npmjs.com/package/share-to-whatsapp

## Version Management

### Current Version: 0.1.0

This version includes:
- ✅ Capacitor 8 support
- ✅ Updated Android build configuration
- ✅ Updated iOS implementation
- ✅ Updated dependencies

### Future Version Bumps

- **Patch** (0.1.1, 0.1.2, etc.): Bug fixes
- **Minor** (0.2.0, 0.3.0, etc.): New features, backward compatible
- **Major** (1.0.0, 2.0.0, etc.): Breaking changes

To bump version:

```bash
# Patch version
npm version patch

# Minor version
npm version minor

# Major version
npm version major
```

This will:
1. Update `package.json` version
2. Create a git tag
3. Commit the change

Then publish:

```bash
npm publish
```

## Publishing Checklist

Before each publish, ensure:

- [ ] All tests pass (if you have tests)
- [ ] Build completes successfully (`npm run build`)
- [ ] Version number is updated appropriately
- [ ] README.md is up to date
- [ ] CHANGELOG.md is updated (if you maintain one)
- [ ] All changes are committed to git
- [ ] You're logged into npm (`npm whoami`)
- [ ] Dry run looks good (`npm publish --dry-run`)

## Troubleshooting

### "You do not have permission to publish"

- Make sure you're logged in: `npm login`
- Check if the package name is taken by someone else
- If it's a scoped package, ensure you have the correct scope

### "Package name already exists"

- If you own the package, you can publish updates
- If someone else owns it, you'll need to choose a different name or contact the owner

### "Invalid package name"

- Package names must be lowercase
- Can contain hyphens and underscores
- Cannot contain spaces or special characters

### Build Errors

If `prepublishOnly` fails:
- Fix any build errors
- Ensure all dependencies are installed: `npm install`
- Try building manually: `npm run build`

## Post-Publishing

After successful publication:

1. **Tag the release in git** (if not done automatically):
   ```bash
   git tag v0.1.0
   git push origin v0.1.0
   ```

2. **Update GitHub repository** (if applicable):
   - Create a release on GitHub
   - Add release notes describing the changes

3. **Test the published package**:
   ```bash
   # In a test project
   npm install share-to-whatsapp@latest
   ```

4. **Update documentation** if needed

## Unpublishing (Emergency Only)

⚠️ **Warning**: Unpublishing should be done within 72 hours and only in emergencies.

```bash
npm unpublish share-to-whatsapp@0.1.0
```

Or to unpublish the entire package (only if no one is using it):

```bash
npm unpublish share-to-whatsapp --force
```

## Notes

- The `prepublishOnly` script ensures the plugin is built before publishing
- The `files` array in `package.json` controls what gets published
- Only files listed in `files` and not in `.npmignore` will be included
- The `dist/` folder is included and contains the built plugin code

