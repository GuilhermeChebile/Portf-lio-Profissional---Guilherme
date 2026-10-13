# Portfólio Profissional | Guilherme Chebile

Website de portfólio profissional com seções **Sobre mim**, **Projetos**, **Experiências** e **Contato**, em **português e inglês** (troca no cabeçalho), design responsivo e formulário de contato com envio por e-mail.

> 🔗 **Site publicado:** _(adicionar o link após o deploy)_
> 📐 **Protótipos (Figma):** _(adicionar o link do Figma)_

## Protótipos

_Inserir aqui as imagens dos wireframes (média fidelidade) de cada página._

| Sobre mim | Projetos | Experiências | Contato |
|-----------|----------|--------------|---------|
| ![](docs/wireframe-sobre.png) | ![](docs/wireframe-projetos.png) | ![](docs/wireframe-experiencias.png) | ![](docs/wireframe-contato.png) |

## Funcionalidades

- **Sobre mim:** apresentação, formação, interesses, objetivos e tecnologias.
- **Projetos:** linha do tempo (do mais antigo ao mais recente) com descrição, tecnologias, link do GitHub e imagem de cada projeto.
- **Experiências:** empresa/atividade, cargo, período e descrição.
- **Contato:** ícones clicáveis (e-mail, WhatsApp, telefone, LinkedIn e GitHub) e formulário (nome, e-mail, mensagem) com validação e envio por e-mail.
- **Idioma PT/EN** no cabeçalho, salvo em cookie.
- Layout responsivo, menu mobile, foco visível e suporte a `prefers-reduced-motion`.

## Tecnologias utilizadas

- Java 25 (LTS)
- Spring Boot 3.5 (Spring MVC)
- Thymeleaf (templates no servidor)
- Bean Validation (validação do formulário)
- Spring Mail (envio por SMTP)
- HTML5, CSS3 e JavaScript (sem frameworks no front-end)
- Maven, Docker (para o deploy)

## Dependências

| Dependência | Uso |
|-------------|-----|
| `spring-boot-starter-web` | Servidor web e controllers |
| `spring-boot-starter-thymeleaf` | Templates HTML |
| `spring-boot-starter-validation` | Validação do formulário |
| `spring-boot-starter-mail` | Envio de e-mail |
| `spring-boot-starter-test` | Testes |

Fontes (Google Fonts): Sora, IBM Plex Sans e IBM Plex Mono.

## Estrutura de diretórios

```
portfolio/
├── pom.xml
├── Dockerfile
├── README.md
└── src/main/
    ├── java/com/guilherme/portfolio/
    │   ├── PortfolioApplication.java
    │   ├── config/        # I18nConfig (PT/EN) e ContactInfo (contatos)
    │   ├── controller/    # PageController e GlobalModelAdvice
    │   ├── model/         # Project, Experience, Education, ContactForm
    │   └── service/       # PortfolioService (dados) e MailService (e-mail)
    └── resources/
        ├── application.properties
        ├── messages.properties      # textos em português
        ├── messages_en.properties   # textos em inglês
        ├── templates/               # fragments, index, projetos, experiencias, contato
        └── static/                  # css/, js/, img/
```

## Como executar localmente

Pré-requisitos: **JDK 25** e **Maven 3.9+**.

```bash
git clone <URL-DO-REPOSITORIO>
cd portfolio
mvn spring-boot:run
```

Acesse http://localhost:8080.

### Envio de e-mail (formulário de contato)

O formulário usa SMTP. Para Gmail, crie uma **Senha de app** (conta Google com verificação em duas etapas) e defina:

```bash
export MAIL_USERNAME="seu-email@gmail.com"
export MAIL_PASSWORD="sua-senha-de-app"
mvn spring-boot:run
```

Sem essas variáveis o site funciona normalmente, mas o formulário exibirá a mensagem de falha no envio. **Nunca** coloque a senha no código.

### Editar conteúdo

- Textos (PT/EN): `src/main/resources/messages.properties` e `messages_en.properties`
- Projetos, experiências e formação: `PortfolioService.java`
- Contatos e redes (LinkedIn/GitHub): `application.properties` (`portfolio.contact.*`)
- Imagens dos projetos: `src/main/resources/static/img/`

### Docker

```bash
docker build -t portfolio .
docker run -p 8080:8080 -e MAIL_USERNAME=... -e MAIL_PASSWORD=... portfolio
```

## Deploy

_Descrever aqui a hospedagem escolhida e o link final._

## Autor

**Guilherme Luiz Santos Chebile**: guilhermechebiledev@gmail.com
