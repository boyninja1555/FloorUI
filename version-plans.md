# Version Plans

> This document defines how builds are versioned

## Version Categories

All builds fall into one of these categories:
- `RELEASE` - Will always be downloadable, and is well-tested
- `DEV` - Will always be downloadable, but can contain bugs patched in future `DEV` builds. Once a `DEV` build is fully tested with 0 bugs, it can be uploaded as `RELEASE`
- `EXPERIMENTAL` - Can be entirely scrapped at any moment. Once an `EXPERIMENTAL` build's core is constructed, the option to move to `DEV` is available. Downloads may be revoked at any moment to prevent common security issues or data loss

## Version Numbers

A version number consists of these digits:

`iteration.major.patch.CATEGORY`

- `iteration` exists so we can completely revamp the project if needed, then promote that digit by one
- `major` exists for `RELEASE` builds. If you're a common server admin, you should only download builds with a patch number of `0`
- `patch` exists for `DEV` builds. If you make content on flashy new features, this may not be for you. If you desperately need the new features added in a `DEV` build, it is somewhat safe to try (you just need to back up your files)
- `CATEGORY` is confirmation the build lies in a certain category

The major digit is promoted when the latest `DEV` build is guaranteed safe, which is also when the `patch` digit is reset to `0`

In the event of a complete project revamp, the `iteration` digit is promoted, and every other field is reset. The first version during a new `iteration` is most likely `iteration.0.1`

All early builds start with a `major` digit of `0`, to signify unreadiness. This also means every early version is labeled as `DEV`