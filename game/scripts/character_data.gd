class_name NyxCharacterData
extends Resource

@export var name: String = "Cidadão Nyx"
@export var gender: String = ""
@export var city: String = "CURITIBA"
@export var age: int = 18
@export var job: String = "Desempregado"
@export var money: int = 5000
@export var married_to: String = ""

func can_marry() -> bool:
    return name.strip_edges() != "" and married_to == ""

func marry(other: NyxCharacterData) -> bool:
    if not can_marry() or other == null or not other.can_marry(): return false
    married_to = other.name
    other.married_to = name
    return true
