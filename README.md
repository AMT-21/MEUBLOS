# MEUBLOS

![example workflow](https://github.com/AMT-21/MEUBLOS/actions/workflows/app_tests.yml/badge.svg)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/AMT-21/sprint0.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/AMT-21/sprint0/context:java)

MEUBLOS est un site e-commerce de meubles. Ce projet s'inscrit dans le cadre du sprint0 du cours d'AMT 2021, l'idée est de prendre en main les technologies comme Java EE Springboot, Thymeleaf. Le test de code automatique avec Travis, et l'intégration continue avec les GitHub Actions.

## Contribuer au projet

### Prérequis

1. Ce projet utilise Java 8, il faut s'assurer d'utiliser la bonne version.
2. Installer git-flow (voir https://github.com/AMT-21/sprint0/wiki/Convention-de-nommage#workflow)
3. Se renseigner sur les conventions de nommages utilisées dans ce projet : https://github.com/AMT-21/sprint0/wiki/Convention-de-nommage#java
4. Créer une issue sur le repo décrivant la fonctionnalité qui va être développée et pourquoi elle serait utile.

### Travailler sur le repo
1. Cloner le repo en local :
```
git clone https://github.com/AMT-21/sprint0
```
2. Créer une branch feature :
```
git flow feature start MYFEATURE 
```

3. Dans le répertoire `resources`, modifier le fichier `application.properties`. Le paramètre `server.port` doit contenir le port utilisé lors de l'accès au site. 
Les paramètres `spring.datasource.username` et `spring.datasource.password` doivent renseigner le mot de passe et nom d'utilisateur utilisés pour créer la base de données. Le paramètre `server.tomcat.upload-dir` doit indiquer le chemin aux images du site et le paramètre `server.auth.url` doit contenir l'adresse et le port du service d'authentification. 

4. Lancer la commande `ssh -L <PORT>:10.0.1.92:8080 MEUBLOS@16.170.194.237 -i <CERTIFICATE>.pem` pour créer un tunnel ssh avec le service de login. <PORT> correspond au port spécifié dans `application.properties` dans le paramètre `server.auth.url` et <CERTIFICATE> au fichier contenant votre certificat.

5. Le site est accessible à l'adresse `localhost:<PORT>` ou `<PORT>` correspond au port spécifié dans `application.properties`  
  
6. Une fois la fonctionnalité développée, créer une PR sur la branch dev. Nous ferons notre maximum pour traiter vos modifications le plus rapidement possible.

Plus d'informations sont disponibles sur le [wiki](https://github.com/AMT-21/MEUBLOS/wiki)

## License
[MIT](https://choosealicense.com/licenses/mit/)
