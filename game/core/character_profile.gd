class_name NyxCharacterProfile
extends RefCounted

const NAME_CHANGE_COST := 50

static func create(name: String, gender: String, city: String) -> Dictionary:
    return {
        "name": name.strip_edges(),
        "gender": gender,
        "city": city,
        "cash": 0,
        "nxcoin": 0,
        "job": "unemployed",
        "organization_id": "",
        "university_completed": false,
        "public_exam_passed": false,
        "military_recruited": false,
        "vehicles": [],
        "properties": []
    }

static func can_change_name(profile: Dictionary) -> bool:
    return int(profile.get("nxcoin", 0)) >= NAME_CHANGE_COST

static func change_name(profile: Dictionary, new_name: String) -> Dictionary:
    if not can_change_name(profile):
        return profile
    var clean_name := new_name.strip_edges()
    if clean_name.is_empty():
        return profile
    var result := profile.duplicate(true)
    result["name"] = clean_name
    result["nxcoin"] = int(result["nxcoin"]) - NAME_CHANGE_COST
    return result
