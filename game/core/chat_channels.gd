class_name NyxChatChannels
extends RefCounted

## Server-authoritative chat channels for Nyx Roleplay.
## Visibility is determined by permissions/membership on the server.

enum Channel { GLOBAL, SUPPORT, ADMIN, ORGANIZATION }

static func can_read(channel: Channel, is_staff: bool, organization_id: String, member_organization_id: String) -> bool:
    match channel:
        Channel.GLOBAL, Channel.SUPPORT:
            return true
        Channel.ADMIN:
            return is_staff
        Channel.ORGANIZATION:
            return organization_id != "" and organization_id == member_organization_id
    return false

static func channel_name(channel: Channel) -> String:
    match channel:
        Channel.GLOBAL: return "Global"
        Channel.SUPPORT: return "Atendimento / Denúncias"
        Channel.ADMIN: return "Admin"
        Channel.ORGANIZATION: return "Organização"
    return "Desconhecido"
