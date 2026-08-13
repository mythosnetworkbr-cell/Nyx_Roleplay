package com.nyx.roleplay

/**
 * Nyx Roleplay prototype flow.
 * This is a native client prototype: no GTA dependency.
 *
 * Flow: Login -> City -> Character -> Enter City.
 */

data class NyxCity(
    val id: String,
    val name: String,
    val state: String,
    val description: String
)

data class NyxCharacter(
    val name: String,
    val gender: Gender,
    val cityId: String
)

enum class Gender { MASCULINO, FEMININO, OUTRO }

enum class Screen { LOGIN, CITY, CHARACTER, WORLD }

object NyxPrototype {
    val cities = listOf(
        NyxCity("curitiba", "Curitiba", "PR", "Metrópole urbana, centro, bairros, indústria e áreas rurais."),
        NyxCity("florianopolis", "Florianópolis", "SC", "Cidade litorânea, praias, centro, morros e áreas turísticas.")
    )

    fun createCharacter(name: String, gender: Gender, city: NyxCity): NyxCharacter =
        NyxCharacter(name.trim(), gender, city.id)
}
