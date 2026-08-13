# Nyx Roleplay — Russian RP Feature Benchmark

This document converts publicly documented patterns from Russian-language CRMP/RP projects into original Nyx systems. It is a design benchmark, not a copy of proprietary code, maps, characters, brands, or assets.

## Publicly observed patterns

- CRMP Online advertises multiple servers, 100+ vehicles, 50+ buildings, professions, property, businesses and factions. It also separates server styles such as criminal, economic and racing experiences.
- Black Russia's official onboarding uses launcher -> nickname/account -> gender -> appearance -> start, and offers virtual currency/items/status purchases.
- Public CRMP forum material describes systems for ATM, vehicle management, trunk, trailers, reports, player progression, donation, GPS, houses, hotels, businesses, animations, personal phone, fuel stations and economy management, plus government, military, hospital, police, media, security and organized-crime factions.
- Arizona Online publicly advertises gangs/territories, gas stations, real estate, business economy, vehicle customization/racing, crafting, clothing and a player trading market.

## Nyx implementation targets

### World and server network
- Server browser with ping, population and maintenance status.
- Multiple Nyx servers with independent world/economy state.
- Region streaming for Android memory/performance.
- Persistent player, vehicle, property and organization data.

### Character lifecycle
- Account authentication.
- Free initial RP name.
- Gender and appearance creation.
- 50 NXcoin server-authoritative name-change fee after creation.
- Clothing and accessories.
- Progression and licenses.

### Economy
- Normal RP cash is separate from NXcoin premium currency.
- Jobs, salaries, taxes, fines, businesses, property and player trading.
- Bank accounts, ATM, transactions and transaction audit trail.
- NXcoin purchases must be verified by the platform/backend; the client cannot mint currency.

### Vehicles
- Dealerships, ownership, garages, fuel, trunk, trailers, tuning, insurance and service.
- Staff vehicle tools from Admin 2+.
- Server validation for ownership, spawning and state changes.

### Organizations
- Police/BOPE, Army, Hospital/SAMU, Fire, Government, News and civilian businesses.
- Criminal organizations/families with territories and organization progression.
- Organization ranks, applications, duties and private organization chat.

### Social/mobile systems
- Personal phone UI.
- Contacts, messages, calls and GPS.
- Global chat.
- Support/complaint channel.
- Staff-only chat.
- Organization-only chat.
- Social interactions, dances, hugs and non-explicit romantic animations.

### Staff
- Assistant -> Support -> Support Master -> Moderator -> Moderator Master -> Admin -> Admin 2 -> Admin Master -> Manager -> Director -> Owner.
- Invisible/spectator tools.
- Reports, moderation, jail, kick, temporary/permanent ban, unban, teleport and vehicle tools according to rank.
- Promotion and IP-ban privileges restricted to the appropriate senior ranks.
- Immutable server audit logs for staff actions.

## Design rule
Nyx should take inspiration from proven gameplay patterns while maintaining original Brazilian locations, original branding, original assets and original implementations. No proprietary game source code or assets are imported from reference projects.
