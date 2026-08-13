extends Node3D

var player: CharacterBody3D
var camera: Camera3D
var city_label: Label
var speed := 8.0
var gravity := 18.0

func _ready():
    _build_world()
    _build_player(Vector3(0, 1, 12))
    _build_ui()

func _build_world():
    var env := WorldEnvironment.new()
    var e := Environment.new()
    e.background_mode = Environment.BG_COLOR
    e.background_color = Color("07101f")
    e.ambient_light_source = Environment.AMBIENT_SOURCE_COLOR
    e.ambient_light_color = Color("9c8cff")
    e.ambient_light_energy = 0.45
    env.environment = e
    add_child(env)

    var sun := DirectionalLight3D.new()
    sun.rotation_degrees = Vector3(-52, -25, 0)
    sun.light_energy = 1.2
    add_child(sun)

    _box("Ground", Vector3(0,-0.5,0), Vector3(180,1,120), Color("202534"))
    _city(Vector3(-58,0,-8), "CURITIBA", Color("34275c"))
    _city(Vector3(58,0,-8), "FLORIANÓPOLIS", Color("173f52"))
    _bridge()
    _lookout(Vector3(0,4,-38))
    _nyx_store(Vector3(24,0,22))
    _hospital(Vector3(-25,0,24))
    _police(Vector3(-45,0,25))
    _university(Vector3(45,0,-30))

func _city(origin: Vector3, name: String, accent: Color):
    for x in range(-3,4):
        for z in range(-2,3):
            var h := 3.0 + float(abs(x*3+z*2)%5)
            _box(name+"_Building", origin+Vector3(x*8,h/2,z*8), Vector3(5,h,5), accent.lerp(Color.WHITE,0.08))
    _box(name+"_Road", origin+Vector3(0,0.02,0), Vector3(65,0.1,5), Color("11141d"))

func _bridge():
    _box("BridgeDeck", Vector3(0,2,0), Vector3(28,1.2,8), Color("555b70"))
    for x in [-12.0,-7.0,-2.0,3.0,8.0,13.0]:
        var p := MeshInstance3D.new()
        var mesh := CylinderMesh.new()
        mesh.top_radius = 0.35; mesh.bottom_radius = 0.55; mesh.height = 13
        p.mesh = mesh
        p.position = Vector3(x,8,0)
        p.rotation_degrees = Vector3(0,0,0)
        p.material_override = _mat(Color("8b5cf6"))
        add_child(p)
    for x in [-12.0,-7.0,-2.0,3.0,8.0,13.0]:
        var arch := MeshInstance3D.new()
        var torus := TorusMesh.new()
        torus.inner_radius = 3.2; torus.outer_radius = 3.5
        arch.mesh = torus
        arch.position = Vector3(x,8,0)
        arch.rotation_degrees = Vector3(90,0,0)
        arch.material_override = _mat(Color("a78bfa"))
        add_child(arch)

func _lookout(pos: Vector3):
    _box("Mirante",pos+Vector3(0,3,0),Vector3(14,6,10),Color("334155"))
    _label3d("MIRANTE NYX",pos+Vector3(0,7,0))

func _nyx_store(pos: Vector3):
    _box("NyxStore",pos+Vector3(0,3,0),Vector3(12,6,10),Color("6d28d9"))
    _label3d("NYXSTORE • ROUPAS",pos+Vector3(0,7,0))

func _hospital(pos: Vector3):
    _box("Hospital",pos+Vector3(0,3,0),Vector3(12,6,10),Color("e5e7eb"))
    _label3d("HOSPITAL",pos+Vector3(0,7,0))

func _police(pos: Vector3):
    _box("Police",pos+Vector3(0,2,0),Vector3(10,4,8),Color("1e3a8a"))
    _label3d("POLÍCIA • BOPE",pos+Vector3(0,5,0))

func _university(pos: Vector3):
    _box("University",pos+Vector3(0,3,0),Vector3(14,6,10),Color("854d0e"))
    _label3d("UNIVERSIDADE",pos+Vector3(0,7,0))

func _build_player(pos: Vector3):
    player = CharacterBody3D.new()
    player.position = pos
    add_child(player)
    var body := MeshInstance3D.new()
    var capsule := CapsuleMesh.new(); capsule.height=1.8; capsule.radius=0.42
    body.mesh=capsule; body.material_override=_mat(Color("d8b4fe")); body.position.y=1.0
    player.add_child(body)
    var shape := CollisionShape3D.new(); var capsule_shape:=CapsuleShape3D.new(); capsule_shape.height=1.8; capsule_shape.radius=0.42
    shape.shape=capsule_shape; shape.position.y=1.0; player.add_child(shape)
    camera=Camera3D.new(); camera.position=Vector3(0,5,8); camera.look_at_from_position(camera.position,Vector3(0,1,0)); player.add_child(camera)

func _physics_process(delta):
    if not player: return
    var dir=Vector3(Input.get_axis("move_left","move_right"),0,Input.get_axis("move_forward","move_back"))
    if dir.length()>0: dir=dir.normalized(); player.velocity.x=dir.x*speed; player.velocity.z=dir.z*speed
    else: player.velocity.x=move_toward(player.velocity.x,0,speed*delta); player.velocity.z=move_toward(player.velocity.z,0,speed*delta)
    if not player.is_on_floor(): player.velocity.y-=gravity*delta
    player.move_and_slide()

func _build_ui():
    var layer:=CanvasLayer.new(); add_child(layer)
    city_label=Label.new(); city_label.text="NYX ROLEPLAY  •  CURITIBA ↔ FLORIANÓPOLIS\nWASD para caminhar  •  NyxStore  •  Mirante  •  Ponte dos Arcos"
    city_label.position=Vector2(24,20); city_label.add_theme_font_size_override("font_size",20); city_label.add_theme_color_override("font_color",Color("f5f3ff")); layer.add_child(city_label)

func _box(n:String,pos:Vector3,size:Vector3,c:Color):
    var m:=MeshInstance3D.new(); m.name=n; var b:=BoxMesh.new(); b.size=size; m.mesh=b; m.position=pos; m.material_override=_mat(c); add_child(m)
    var cs:=StaticBody3D.new(); cs.position=pos; var col:=CollisionShape3D.new(); var sh:=BoxShape3D.new(); sh.size=size; col.shape=sh; cs.add_child(col); add_child(cs)

func _mat(c:Color):
    var m:=StandardMaterial3D.new(); m.albedo_color=c; m.roughness=0.72; return m

func _label3d(t:String,pos:Vector3):
    var l:=Label3D.new(); l.text=t; l.position=pos; l.font_size=48; l.modulate=Color("ffffff"); l.outline_size=8; add_child(l)
