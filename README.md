# ☁️ Aplicativo do Clima (APO - Unipar 2025)

## 📌 Visão Geral do Projeto

Olá a todos! Este é o projeto final da disciplina de **APO (Arquitetura e Padrões de Orientação a Objetos)** ou similar, desenvolvido para o período de 2025.

O objetivo principal desta aplicação Android é fornecer uma **interface simples e intuitiva para consultar a previsão do tempo** em várias localidades. Utilizamos uma API externa para obter os dados em tempo real e aplicamos os conceitos de orientação a objetos e padrões de design aprendidos em sala.

Basicamente, é um app para vermos se vai chover e se precisamos levar guarda-chuva, mas feito com muito código bem estruturado! 😅

---

## 🛠️ Tecnologias e Requisitos

Para rodar este projeto na sua máquina e poder modificá-lo, você precisará do seguinte:

* **Android Studio** (versão recomendada: a mais recente).
* **Linguagem de Programação:** Kotlin.
* **Android SDK:** Mínimo API 21 ou superior.
* **Gerenciador de Dependências:** Gradle.
* **Conexão com a Internet:** Necessária para obter os dados da API.

### Dependências Chave Utilizadas:

* **Retrofit:** Para as chamadas assíncronas à API do clima.
* **LiveData (do Android Jetpack):** Usado para gerenciar o estado da UI de forma observável, permitindo que os componentes da interface reajam às mudanças de dados de forma segura.
* **Arquitetura:** Tentamos seguir (ou pelo menos nos aproximar 🤞) do padrão **MVVM** (Model-View-ViewModel) para separar a lógica de negócio da interface do usuário.

---

## 🚀 Guia de Instalação e Uso

### 1. Clonar o Repositório

Se você quer ter o código, use o Git:

```bash
git clone [https://github.com/jjlemus23/Aplicativo-do-Clima-APO-Unipar-2025.git](https://github.com/jjlemus23/Aplicativo-do-Clima-APO-Unipar-2025.git)
