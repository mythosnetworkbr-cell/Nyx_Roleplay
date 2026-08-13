class_name PremiumNameRules
extends RefCounted

## Nyx Roleplay naming rules.
## The initial character name is free and remains the active RP name.
## A later name change costs 50 NXcoin (premium currency purchased outside gameplay).
const NAME_CHANGE_COST_NXCOIN: int = 50

static func can_change_name(nxcoin_balance: int, requested_name: String) -> bool:
    return nxcoin_balance >= NAME_CHANGE_COST_NXCOIN and requested_name.strip_edges().length() >= 3

static func charge_name_change(nxcoin_balance: int) -> int:
    if nxcoin_balance < NAME_CHANGE_COST_NXCOIN:
        return nxcoin_balance
    return nxcoin_balance - NAME_CHANGE_COST_NXCOIN
