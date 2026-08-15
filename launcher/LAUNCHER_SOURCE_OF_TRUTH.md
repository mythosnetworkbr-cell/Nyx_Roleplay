# MYTHØS Launcher — Source of Truth

This directory is the official Launcher project for the Mythøs Network.

## Current baseline

- Release baseline: **MYTHØS Launcher 2.2 Polished**
- Android package: `br.com.mythos.rp`
- Version: `2.2.0`
- Android versionCode: `12`
- Orientation: landscape
- Target server: `51.68.107.75:10961`
- Direct protocol: `samp://51.68.107.75:10961`

## Responsibilities

The Launcher owns:

- Launcher UI and branding
- Play/enter-server flow
- Automatic content/cache preparation
- Client/update delivery flow
- Launcher media and assets
- Android packaging and release configuration
- Server endpoint configuration used by the Launcher

## Project separation

- `RP` = Mobile client
- `RPGRAMBR` = RPGRAM social network
- `Game_base` = SAMP GameMode/base
- `Mythos_Network_SAMP-OFICIAL` and the other unrelated repositories are not modified as part of Launcher work.

## Baseline assets

The 2.2 Polished package supplied for this project contains the current launcher visual baseline, including the MYTHØS branding, hero video and supplied client APK asset.

## Important Android constraint

A normal Android application cannot silently install another APK. The Launcher must use the Android-supported package installation flow when an external client APK needs to be installed or updated.
