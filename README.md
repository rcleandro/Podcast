# :headphones: Podcast Player

Este é um projeto Android desenvolvido em Kotlin para um reprodutor de podcast que utiliza fontes RSS de podcasts públicos.

## Funcionalidades

- Listagem de episódios de podcasts a partir de fontes RSS.
- Reprodução de episódios de podcast.
- Controle de reprodução (play, pause, stop).
- Interface amigável e intuitiva.
- Cache de dados RSS usando Room.
- Cache de imagens usando Picasso.

## Tecnologias Utilizadas

- **Kotlin**: Linguagem principal do projeto.
- **Android Jetpack**: Conjunto de bibliotecas para ajudar no desenvolvimento de aplicativos robustos.
    - **ViewModel**: Para gerenciar dados relacionados à UI de forma lifecycle-aware.
    - **LiveData**: Para observar mudanças nos dados.
    - **Navigation**: Para gerenciar a navegação entre fragmentos.
    - **Room**: Para cache de dados RSS.
- **Retrofit**: Para fazer requisições HTTP e parsear o XML das fontes RSS.
- **ExoPlayer**: Para reprodução de áudio.
- **Picasso**: Para carregamento e cache de imagens.

## Estrutura do Projeto

- `MainActivity.kt`: Atividade principal que hospeda os fragmentos.
- `MainViewModel.kt`: ViewModel que gerencia os dados dos RSS e podcasts.
- `HomeFragment.kt`: Fragmento que exibe a lista de episódios de podcast.
- `HomeViewModel.kt`: ViewModel que gerencia o status da lista de RSS.
- `PlayerFragment.kt`: Fragmento que controla a reprodução dos episódios.
- `RSSRepository.kt`: Repositório que faz as requisições às fontes RSS e gerencia o cache.

## Configuração do Ambiente

1. **Clone o repositório:**
    ```sh
    git clone https://github.com/rcleandro/Podcast.git
    cd Podcast
    ```

2. **Abra o projeto no Android Studio:**
    - Selecione `File > Open` e escolha o diretório do projeto clonado.

3. **Compile e execute o projeto:**
    - Clique no botão `Run` ou utilize o atalho `Shift + F10`.