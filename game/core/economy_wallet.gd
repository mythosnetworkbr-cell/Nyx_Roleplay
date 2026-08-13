class_name NyxEconomyWallet
extends RefCounted

## Economy model. In production, all balance mutations must be server-authoritative.

static func create() -> Dictionary:
    return {"cash": 0, "bank": 0, "nxcoin": 0}

static func deposit(wallet: Dictionary, amount: int) -> Dictionary:
    if amount <= 0 or int(wallet.get("cash", 0)) < amount:
        return wallet
    var result := wallet.duplicate(true)
    result["cash"] = int(result["cash"]) - amount
    result["bank"] = int(result.get("bank", 0)) + amount
    return result

static func withdraw(wallet: Dictionary, amount: int) -> Dictionary:
    if amount <= 0 or int(wallet.get("bank", 0)) < amount:
        return wallet
    var result := wallet.duplicate(true)
    result["bank"] = int(result["bank"]) - amount
    result["cash"] = int(result.get("cash", 0)) + amount
    return result
