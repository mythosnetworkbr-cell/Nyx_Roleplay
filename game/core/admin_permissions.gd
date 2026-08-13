class_name NyxAdminPermissions
extends RefCounted

## Server-authoritative staff hierarchy for Nyx Roleplay.
## Never trust the client to grant or validate staff privileges.
enum Rank {
    ASSISTENTE,
    SUPORTE,
    SUPORTE_MASTER,
    MODERADOR,
    MODERADOR_MASTER,
    ADMIN,
    ADMIN_MASTER,
    GERENTE,
    DIRETOR,
    OWNER,
}

const RANK_NAMES := {
    Rank.ASSISTENTE: "Assistente",
    Rank.SUPORTE: "Suporte",
    Rank.SUPORTE_MASTER: "Suporte Master",
    Rank.MODERADOR: "Moderador",
    Rank.MODERADOR_MASTER: "Moderador Master",
    Rank.ADMIN: "Admin",
    Rank.ADMIN_MASTER: "Admin Master",
    Rank.GERENTE: "Gerente",
    Rank.DIRETOR: "Diretor",
    Rank.OWNER: "Owner",
}

static func can_manage_staff(rank: int) -> bool:
    return rank >= Rank.ADMIN_MASTER

static func can_grant_rank(actor_rank: int, target_rank: int) -> bool:
    # Admin Master+ may promote, but cannot grant a rank equal/higher than itself.
    return can_manage_staff(actor_rank) and target_rank < actor_rank

static func can_ip_ban(rank: int) -> bool:
    return rank >= Rank.ADMIN_MASTER

static func can_ban(rank: int) -> bool:
    return rank >= Rank.ADMIN

static func can_unban(rank: int) -> bool:
    return rank >= Rank.ADMIN_MASTER

static func can_execute(rank: int, action: String) -> bool:
    match action:
        "jail", "unjail", "kick": return rank >= Rank.MODERADOR
        "ban": return can_ban(rank)
        "unban", "ip_ban", "grant_staff": return rank >= Rank.ADMIN_MASTER
        "owner_control": return rank == Rank.OWNER
        _: return false
