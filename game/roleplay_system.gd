class_name NyxRoleplaySystem
extends Node

signal notification(message: String)

var jobs := {
    "Cidadão": {"salary": 100},
    "Motorista": {"salary": 450},
    "Médico": {"salary": 650, "requires": "UNIVERSIDADE"},
    "Policial": {"salary": 700, "requires": "CONCURSO"},
    "Jornalista": {"salary": 500},
    "Militar": {"salary": 600, "requires": "RECRUTAMENTO"}
}

var marriage := {"married": false, "partner": ""}

func can_take_job(job: String, credentials: Array[String]) -> bool:
    if not jobs.has(job): return false
    if not jobs[job].has("requires"): return true
    return jobs[job].requires in credentials

func take_job(job: String, credentials: Array[String]) -> bool:
    if not can_take_job(job, credentials):
        notification.emit("Requisitos não concluídos para este emprego.")
        return false
    notification.emit("Emprego selecionado: %s" % job)
    return true

func marry(partner_name: String) -> bool:
    if partner_name.strip_edges().is_empty(): return false
    marriage.married = true
    marriage.partner = partner_name
    notification.emit("Casamento registrado com %s." % partner_name)
    return true
