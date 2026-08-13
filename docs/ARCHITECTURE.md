# Nyx Roleplay — arquitetura

## Objetivo
Cliente Android em landscape, inspirado na experiência de servidores RP de mundo aberto, mas com identidade, código e assets próprios.

## Camadas
- `game/`: cliente 3D Godot 4.
- `server/`: servidor autoritativo Godot para sessões RP.
- `launcher/`: entrada Android, autenticação e distribuição.
- `assets/`: modelos, materiais, áudio e identidade visual.

## Cliente
O cliente mantém somente apresentação, input e predição local. Dinheiro, inventário, empregos, propriedade, estado de veículos e regras críticas devem ser validados pelo servidor.

## Multiplayer
ENet é a primeira camada de transporte. RPCs públicas devem validar origem, permissões, estado do personagem e limites de dados. O servidor deve ser executável em modo dedicado/headless.

## Cidades
Cada cidade tem um perfil de personagem/economia separado. A ponte conecta regiões do mundo, mas a persistência permanece separada por cidade.

## Sistemas
Personagem, veículos, inventário, economia, empregos, universidade, concurso, recrutamento militar, organizações, polícia, hospital, comércio, propriedades e casamento são módulos independentes.

## Android
O alvo inicial é 1280x720 lógico em landscape, com HUD touch adaptativo. O projeto deve oferecer presets de qualidade para aparelhos de baixo, médio e alto desempenho.

## Conteúdo
Referências de design de outros RP podem orientar UX e sistemas. Código, mapas, personagens, marcas, músicas e modelos proprietários de terceiros não devem ser copiados.
