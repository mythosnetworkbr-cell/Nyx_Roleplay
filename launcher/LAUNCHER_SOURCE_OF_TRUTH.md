# MYTHØS Launcher — Source of Truth

This directory is the official Launcher project for the Mythøs Network.

## Current baseline

- Release baseline: **MYTHØS Launcher 2.2 Polished**
- Android package: `br.com.mythos.rp`
- Version: `2.2.0`
- Android versionCode: `12`
- Orientation: landscape
- Target server: `ip.oscrias.com.br:7777`
- Direct protocol: `samp://ip.oscrias.com.br:7777`

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

## Realidade RP reference

The supplied `Realidade RP.apk` was analyzed as a UX/architecture reference. Useful patterns were incorporated into the Launcher without copying its proprietary runtime binaries or third-party game data:

- landscape-first full-screen presentation
- persistent server/status information
- explicit refresh/update action
- compact status and progress treatment
- card-based server selector/status surfaces
- loading-state feedback
- settings entry point
- direct server-entry flow
- detection of whether a `samp://` handler is available

The reference APK contains a full client/runtime stack, including native game libraries and open.mp components. Those binaries belong to the Mobile/client side and are **not** copied into this Launcher repository. `RP` remains the Mobile client repository.

## Important Android constraint

A normal Android application cannot silently install another APK. The Launcher must use the Android-supported package installation flow when an external client APK needs to be installed or updated.
