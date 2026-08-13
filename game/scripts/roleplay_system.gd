class_name NyxRoleplaySystem
extends Node

signal job_changed(character, job)
signal relationship_changed(a, b)

const JOBS := ["Cidadão", "Médico", "Policial", "BOPE", "Exército", "Jornalista", "Bombeiro", "Professor", "Mecânico"]

func apply_job(character: NyxCharacterData, job: String) -> bool:
    if character == null or not JOBS.has(job): return false
    character.job = job
    job_changed.emit(character, job)
    return true

func marry(a: NyxCharacterData, b: NyxCharacterData) -> bool:
    if a == null or b == null: return false
    var ok:=a.marry(b)
    if ok: relationship_changed.emit(a,b)
    return ok

func enroll_university(character: NyxCharacterData) -> bool:
    if character == null: return false
    return character.age >= 17

func pass_public_exam(character: NyxCharacterData, score: int) -> bool:
    return character != null and score >= 70

func military_recruitment_due(character: NyxCharacterData) -> bool:
    return character != null and character.age == 18
