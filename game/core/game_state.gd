class_name NyxGameState
extends Node

var city_profiles := {
    "CURITIBA": {"cash": 5000, "bank": 0, "job": "Cidadão", "inventory": [], "owned_vehicles": [], "properties": []},
    "FLORIANÓPOLIS": {"cash": 5000, "bank": 0, "job": "Cidadão", "inventory": [], "owned_vehicles": [], "properties": []}
}

var active_city := "CURITIBA"
var character_name := "Cidadão Nyx"
var gender := ""

func select_city(city: String) -> bool:
    if not city_profiles.has(city):
        return false
    active_city = city
    return true

func get_profile() -> Dictionary:
    return city_profiles[active_city]

func add_cash(amount: int) -> void:
    city_profiles[active_city].cash += amount

func spend_cash(amount: int) -> bool:
    if amount < 0 or city_profiles[active_city].cash < amount:
        return false
    city_profiles[active_city].cash -= amount
    return true

func set_job(job_name: String) -> void:
    city_profiles[active_city].job = job_name

func add_item(item_id: String) -> void:
    city_profiles[active_city].inventory.append(item_id)

func own_vehicle(vehicle_id: String) -> void:
    if vehicle_id not in city_profiles[active_city].owned_vehicles:
        city_profiles[active_city].owned_vehicles.append(vehicle_id)
