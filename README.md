# Nyx Roleplay

Projeto oficial do Nyx Roleplay, desenvolvido pela Mythøs Network.

## Cliente de jogo

A pasta `game/` contém uma base **Godot 4** para o cliente 3D nativo. O mundo é construído proceduralmente por código para permitir um primeiro protótipo executável sem depender de assets externos.

Incluído nesta camada:

- modo landscape 1280x720;
- duas cidades: Curitiba e Florianópolis;
- ponte monumental com arcos;
- mirante;
- NyxStore;
- hospital, polícia/BOPE e universidade como pontos do mundo;
- personagem 3D controlável;
- perfis de personagem separados por cidade;
- casamento sem restrição por gênero;
- empregos, universidade, concurso e recrutamento como base de gameplay.

## Estrutura

- `game/` — cliente 3D Godot e scripts de gameplay
- `app/` — aplicativo/backend auxiliar
- `launcher/` — cliente Android/protótipo de autenticação e entrada
- `server/` — servidor e configurações
- `assets/` — identidade visual e recursos
- `docs/` — documentação

## Estado

Esta é a fundação jogável/procedural. Modelos artísticos finais, mapas detalhados, animações, áudio, backend multiplayer de produção e autenticação Google com credenciais reais ainda precisam ser integrados antes de uma versão comercial final.
