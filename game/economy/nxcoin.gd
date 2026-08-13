class_name NXCoinEconomy
extends Node

## Nyx Roleplay's official in-game currency.
## NXcoin is an internal virtual currency; never use real-money values here.
const CURRENCY_CODE := "NX"
const CURRENCY_NAME := "NXcoin"

var balances: Dictionary = {}

func get_balance(player_id: String) -> int:
    return int(balances.get(player_id, 0))

func set_balance(player_id: String, amount: int) -> void:
    balances[player_id] = max(0, amount)

func deposit(player_id: String, amount: int) -> bool:
    if amount < 0:
        return false
    set_balance(player_id, get_balance(player_id) + amount)
    return true

func withdraw(player_id: String, amount: int) -> bool:
    if amount < 0 or get_balance(player_id) < amount:
        return false
    set_balance(player_id, get_balance(player_id) - amount)
    return true

func format_amount(amount: int) -> String:
    return "%d NXcoin" % amount
