# Nyx Roleplay — Client Architecture

## Product model
Nyx is one mobile RP client with two independent cities. The same player account may access both cities, but each city owns a separate character and progression namespace.

## Fixed cities
- city_01 — Cidade 01 — `ip.oscrias.com.br:7777`
- city_02 — Cidade 02 — `51.254.21.27:7777`

## Data isolation
Every persistent gameplay record must be scoped by `account_id + city_id + character_id`. Money, inventory, vehicles, properties, jobs, organizations and progression must never cross city boundaries.

## Client layers
1. Launcher: city selection, update state, connection entry point.
2. Account/session: authentication and session token handling.
3. Character: create/select the character belonging to the selected city.
4. HUD: health, armor, money, notifications, minimap and action controls.
5. Gameplay UI: chat, inventory, vehicle, jobs, properties and organization screens.
6. Network adapter: server-specific connection and protocol integration.
7. Persistence adapter: API/database synchronization; the client is not the authority for economy or inventory.

## Security boundary
The server remains authoritative for money, inventory, vehicles, properties, jobs, organizations and permissions. Client-side values are presentation/cache only and must not be trusted for economy changes.

## First implementation milestone
- Replace generic Android buttons with Nyx RP visual system.
- Fixed two-city selection.
- Character profiles isolated by city ID.
- Launch selected city with its host/port.
- Prepare screens for character selection and HUD without claiming server-side systems are implemented yet.

## Future server integration
The server/API layer must expose account login, character list per city, character creation, profile synchronization, inventory, vehicle, property, job and organization endpoints/events. These endpoints should be implemented against the actual Nyx server code before declaring the corresponding gameplay feature complete.
