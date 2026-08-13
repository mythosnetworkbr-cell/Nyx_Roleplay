extends Node

const PORT := 7777
const MAX_CLIENTS := 128
var peer := ENetMultiplayerPeer.new()
var players := {}

func _ready() -> void:
    if not OS.has_feature("dedicated_server") and not OS.has_feature("headless"):
        return
    peer.create_server(PORT, MAX_CLIENTS)
    multiplayer.multiplayer_peer = peer
    multiplayer.peer_connected.connect(_on_peer_connected)
    multiplayer.peer_disconnected.connect(_on_peer_disconnected)
    print("NYX SERVER listening on UDP %d" % PORT)

func _on_peer_connected(id: int) -> void:
    players[id] = {"city": "CURITIBA", "character": "Cidadão Nyx", "cash": 5000}
    print("player connected: ", id)

func _on_peer_disconnected(id: int) -> void:
    players.erase(id)
    print("player disconnected: ", id)

@rpc("any_peer", "reliable")
func request_spawn(city: String, character_name: String) -> void:
    if not multiplayer.is_server(): return
    var id := multiplayer.get_remote_sender_id()
    if not players.has(id): return
    if city != "CURITIBA" and city != "FLORIANÓPOLIS": return
    players[id].city = city
    players[id].character = character_name.left(32)
    rpc_id(id, "spawn_accepted", players[id])

@rpc("authority", "reliable")
func spawn_accepted(profile: Dictionary) -> void:
    print("spawn accepted: ", profile)
