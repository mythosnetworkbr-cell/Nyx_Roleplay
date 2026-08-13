class_name NyxCityProfile
extends Resource

@export var city_id: String = "CURITIBA"
@export var display_name: String = "Curitiba"
@export var spawn := Vector3.ZERO
@export var character_slot: int = 0
@export var economy_seed: int = 100

static func curitiba() -> NyxCityProfile:
    var p:=NyxCityProfile.new(); p.city_id="CURITIBA"; p.display_name="Curitiba"; p.spawn=Vector3(-58,1,8); p.character_slot=0; return p

static func florianopolis() -> NyxCityProfile:
    var p:=NyxCityProfile.new(); p.city_id="FLORIANOPOLIS"; p.display_name="Florianópolis"; p.spawn=Vector3(58,1,8); p.character_slot=1; return p
