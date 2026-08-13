# Nyx Roleplay — Mundo e mapa inicial

## Conceito
O mundo do Nyx usa Curitiba e Florianópolis como referências geográficas. A implementação final deve transformar os dados geográficos em um mapa 3D próprio e otimizado para Android, sem copiar prédios/ativos proprietários sem autorização.

## Base cartográfica
OpenStreetMap pode ser usado como referência de ruas, vias e geografia. O projeto deve manter a atribuição exigida pela ODbL quando dados OSM forem incorporados. Fonte: https://www.openstreetmap.org/copyright

## Ligação entre cidades
- Uma grande ponte monumental de arcos será o marco de ligação entre as duas regiões do mapa.
- A ponte terá pista para veículos, passagem de pedestres e iluminação noturna.
- Haverá um mirante elevado próximo à ponte, com vista panorâmica e ponto turístico.
- A ponte será um asset original do Nyx, não uma cópia de uma ponte real.

## Curitiba
- Centro urbano
- Bairros residenciais
- Região industrial
- Comunidades fictícias
- Rodovias
- Polícia, hospital, bombeiros, jornal, universidades e comércio
- Área turística com mirante

## Florianópolis
- Centro
- Região insular e continental representadas de forma otimizada
- Praias
- Morros e comunidades fictícias
- Marina/porto
- Rodovias
- Polícia, hospital, bombeiros, jornal, universidades e comércio

## NyxStore
A entrada de uma grande loja de departamentos será um ponto turístico do mapa.

- Nome no jogo: **NyxStore**
- Grande fachada própria
- Grande estátua inspirada no conceito visual de uma estátua da liberdade como marco arquitetônico
- Loja de roupas e personalização de personagens
- Roupas comuns, sociais, esportivas, profissionais e coleções especiais
- Provadores e preview 3D antes da compra
- Eventos e lançamentos de coleções

A NyxStore deve usar identidade visual e modelagem próprias do Nyx. O uso de marca, fachada ou ativos oficiais da Havan exigiria autorização dos titulares. A referência à Estátua da Havan foi usada apenas como inspiração de ponto turístico; a Havan informa que suas estátuas são um símbolo da rede e que existem dezenas delas no Brasil.

## Otimização mobile
- Streaming por regiões/células
- LOD para prédios e objetos distantes
- Occlusion culling
- Texturas comprimidas
- Limite de NPCs por região
- Carregamento assíncrono de interiores
- Distância de renderização adaptativa
- Presets gráfico baixo/médio/alto

## Primeira área jogável
1. Login
2. Seleção Curitiba/Florianópolis
3. Criação do personagem
4. Spawn inicial
5. Centro urbano
6. Ponte monumental
7. Mirante
8. NyxStore
9. Primeiros serviços e empregos
10. HUD mobile
