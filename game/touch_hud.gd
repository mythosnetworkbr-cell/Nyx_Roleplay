extends CanvasLayer

signal move_vector(value: Vector2)
signal action_pressed(action: String)

func _ready() -> void:
    layer = 20
    _build_touch_ui()

var layer := 20

func _build_touch_ui() -> void:
    var root := Control.new()
    root.set_anchors_and_offsets_preset(Control.PRESET_FULL_RECT)
    add_child(root)

    var joystick := TouchScreenButton.new()
    joystick.name = "MoveJoystick"
    joystick.position = Vector2(55, 540)
    joystick.size = Vector2(150, 150)
    root.add_child(joystick)

    for data in [["CORRER", Vector2(1040, 500)], ["INTERAGIR", Vector2(1050, 600)], ["CELULAR", Vector2(900, 600)]]:
        var b := Button.new()
        b.text = data[0]
        b.position = data[1]
        b.size = Vector2(150, 64)
        b.pressed.connect(func(): action_pressed.emit(data[0]))
        root.add_child(b)
