class_name StaffVehiclePermissions
extends RefCounted

## Staff vehicle/tool permissions. The server must enforce these rules.
## Admin 2 and above may use staff vehicle spawning and related tools.

enum StaffRank {
    ASSISTANT,
    SUPPORT,
    SUPPORT_MASTER,
    MODERATOR,
    MODERATOR_MASTER,
    ADMIN,
    ADMIN_2,
    ADMIN_MASTER,
    MANAGER,
    DIRECTOR,
    OWNER
}

static func can_spawn_vehicle(rank: StaffRank) -> bool:
    return rank >= StaffRank.ADMIN_2

static func can_remove_vehicle(rank: StaffRank) -> bool:
    return rank >= StaffRank.ADMIN_2

static func can_repair_vehicle(rank: StaffRank) -> bool:
    return rank >= StaffRank.ADMIN_2

static func can_teleport_vehicle(rank: StaffRank) -> bool:
    return rank >= StaffRank.ADMIN_2
