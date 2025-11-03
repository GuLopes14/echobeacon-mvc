# 🚀 Guia de Deploy no Render

Este documento descreve o passo a passo para fazer deploy da aplicação **Echo Beacon MVC** no Render.

## 📋 Pré-requisitos

- Conta no [Render](https://render.com)
- GitHub com o repositório da aplicação
- Credenciais do Google OAuth2
- Variáveis de ambiente necessárias

## 🔧 Configuração no Render

### **Passo 1: Criar Banco de Dados PostgreSQL**

1. Acesse o [Render Dashboard](https://dashboard.render.com)
2. Clique em **"+ New"** → **"PostgreSQL"**
3. Preencha os dados:
   - **Name:** `echobeacon-postgres`
   - **Database:** `echobeacon`
   - **User:** `echobeacon`
   - **Region:** `Oregon` (ou mais próximo de você)
   - **Plan:** `Starter` (gratuito)
4. Clique em **"Create Database"**
5. Copie a **Internal Database URL** (você vai precisar dela)

**Exemplo de URL:**
```
postgresql://postgres:seu_password_aleatorio@dpg-xxxxx.oregondb.render.com:5432/echobeacon
```

### **Passo 2: Criar Web Service**

1. No Render Dashboard, clique em **"+ New"** → **"Web Service"**
2. Conecte seu repositório GitHub:
   - Selecione o repositório `echobeacon-mvc`
   - Branch: `main`
3. Preencha as configurações:
   - **Name:** `echobeacon-mvc`
   - **Environment:** `Docker`
   - **Region:** `Oregon`
   - **Plan:** `Starter` (gratuito)
   - **Root Directory:** `.` ⬅️ RAIZ DO REPOSITÓRIO
4. Clique em **"Create Web Service"**

### **Passo 3: Configurar Variáveis de Ambiente**

No formulário do Web Service, vá para **"Environment"** e adicione as seguintes variáveis:

| Variável | Valor | Descrição |
|----------|-------|-----------|
| `SPRING_DATASOURCE_URL` | `postgresql://postgres:password@dpg-xxxxx.oregondb.render.com:5432/echobeacon` | URL do banco de dados |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | `seu_password` | Senha do banco (pegue do painel do DB) |
| `GOOGLE_CLIENT_ID` | `seu_google_client_id.apps.googleusercontent.com` | ID do cliente Google |
| `GOOGLE_CLIENT_SECRET` | `seu_google_client_secret` | Secret do Google OAuth2 |
| `ADMIN_EMAILS` | `seu_email@example.com` | E-mail dos administradores |
| `MQTT_BROKER_URL` | `tcp://broker.hivemq.com:1883` | URL do broker MQTT |
| `MQTT_CLIENT_ID` | `echobeacon-mvc-render` | ID único do cliente MQTT |
| `MQTT_TOPIC` | `fiap/iot/echobeacon/comando` | Tópico MQTT |
| `JAVA_TOOL_OPTIONS` | `-Xmx512m -Xms256m` | Configuração de memória JVM |

### **Passo 4: Implantação**

1. Clique em **"Create Web Service"**
2. O Render iniciará automaticamente o build e deploy
3. Você pode acompanhar o progresso na aba **"Logs"**

## ✅ Verificação do Deploy

Depois que o deploy terminar:

1. Vá para a URL do seu serviço (algo como `https://echobeacon-mvc.onrender.com`)
2. Você deve ver a página de login
3. Verifique os logs para erros:
   ```
   Clique em "Logs" para verificar se tudo rodou sem problemas
   ```

## 🔐 Configuração do Google OAuth2

Se você ainda não tem as credenciais do Google:

1. Acesse [Google Cloud Console](https://console.cloud.google.com)
2. Crie um novo projeto
3. Vá para **"Credenciais"** → **"Criar credencial"** → **"ID do cliente OAuth 2.0"**
4. Selecione **"Aplicativo da Web"**
5. Em **"URIs de redirecionamento autorizados"**, adicione:
   ```
   https://echobeacon-mvc.onrender.com/login/oauth2/code/google
   ```
6. Salve o Client ID e Client Secret

## 📊 Atualizações Futuras

O Render detectará automaticamente mudanças no branch `main` do GitHub e fará redeploy:
- Alterações no código
- Alterações em variáveis de ambiente (não disparão redeploy automático)

Para fazer redeploy manual, clique em **"Manual Deploy"** → **"Latest Commit"**

## 🐛 Troubleshooting

### Erro: "Database connection refused"
- Verifique se o `SPRING_DATASOURCE_URL` está correto
- Certifique-se de que o banco PostgreSQL está rodando

### Erro: "Out of Memory"
- Aumentar a memória JVM em `JAVA_TOOL_OPTIONS`
- Considerar atualizar o plano do Render

### Erro: "Migration failed"
- Verifique se o Flyway consegue acessar os arquivos em `src/main/resources/db/migration/`
- Veja os logs para mensagens de erro específicas

### Erro: "OAuth2 redirect not working"
- Confirme que o URL de redirecionamento está exatamente igual no Google Cloud Console
- Verifique que o domínio do Render está correto

## 📞 Suporte

Documentação oficial do Render: https://render.com/docs
Documentação do Spring Boot: https://spring.io/projects/spring-boot
Documentação do Flyway: https://flywaydb.org/documentation/

---

**Última atualização:** Novembro 2025
