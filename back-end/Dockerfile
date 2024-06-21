# Estágio 1: Construir a aplicação com Maven
FROM maven:3.8.4-openjdk-11 AS build
WORKDIR /app

# Copiar os arquivos de configuração do Maven
COPY pom.xml .
COPY src ./src

# Construir a aplicação e copiar as dependências
RUN mvn clean package -DskipTests && \
    mkdir -p dependency-jars && \
    mvn dependency:copy-dependencies -DoutputDirectory=dependency-jars

# Estágio 2: Criar a imagem final com Tomcat
FROM tomcat:10.1-jdk11-openjdk-slim

# Remover as aplicações padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar o arquivo WAR do estágio de build para o diretório webapps do Tomcat
COPY --from=build /app/target/senac-20241-backend-exemplos-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Copiar as dependências do estágio de build para o diretório lib do Tomcat
COPY --from=build /app/dependency-jars /usr/local/tomcat/lib/

# Copiar context.xml para o diretório conf do Tomcat
COPY src/main/webapp/META-INF/Context.xml /usr/local/tomcat/conf/context.xml

# Expor a porta na qual a sua aplicação está rodando
EXPOSE 8080

# Iniciar o Tomcat
CMD ["catalina.sh", "run"]
