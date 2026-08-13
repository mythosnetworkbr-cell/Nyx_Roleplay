class_name NyxVehicle
extends CharacterBody3D

@export var max_speed := 22.0
@export var acceleration := 18.0
@export var brake := 28.0
@export var turn_speed := 1.8
var occupied := false
var driver_id := 0

func _physics_process(delta: float) -> void:
    if not occupied:
        velocity.x = move_toward(velocity.x, 0.0, brake * delta)
        velocity.z = move_toward(velocity.z, 0.0, brake * delta)
        return
    var throttle := Input.get_axis("move_back", "move_forward")
    var steering := Input.get_axis("move_left", "move_right")
    velocity.z = move_toward(velocity.z, -throttle * max_speed, acceleration * delta)
    rotation.y -= steering * turn_speed * delta * clamp(abs(velocity.z) / max_speed, 0.15, 1.0)
    velocity = global_transform.basis * Vector3(0, 0, -velocity.z)
    move_and_slide()
