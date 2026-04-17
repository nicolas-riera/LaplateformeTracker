# Laplateforme Tracker

![pythonversion](https://img.shields.io/badge/java-25-brown)
![javafx](https://img.shields.io/badge/javafx-required-yellow)

![screenshot](/docs/Capture%20d’écran%202026-04-17%20105246.png)

LaPlateforme Tracker est un outil Java permettant de gérer une base de données PostGreSQL d'étudiants, avec une interface graphique réalisé avec JavaFx.

## Fonctionnalités

- Système de connexion sécurisé
- Gestion complète des étudiants
- Statistiques globales
- Gestions des notes des étudiants
- Interface graphique simple mais intuitive
- Base de données PostGreSQL pour stocker les données

## Prérequis

Vérifiez que vous avez installé :

- Java 25
- Maven
- PostGreSQL

### Clone

Clonez le repository :

```bash
git clone https://github.com/nicolas-riera/LaplateformeTracker.git
```

### Préparer la base de données

Dans PostGreSQL, créez une base de données nommée "laplateforme_tracker" :

```SQL
CREATE DATABASE laplateforme_tracker;
```

Ensuite, importer ```laplateforme_tracker.sql```, trouvable dans ```/docs/database``` :

```bash
psql -U {utilisateur} -d laplateforme_tracker < laplateforme_tracker.sql
```

Enfin, créer un fichier ```.env``` dans le dossier ```laplateformetracker```, et remplissez les informations de cette façon :

```
POSTGRE_IP="localhost"
POSTGRE_PORT="5432"
POSTGRE_USER="{utilisateur}"
POSTGRE_PASSWORD="{mot de passe}"
```

### Exécuter

Pour exécuter le programme, exécuter dans le dossier ```laplateformetracker``` la commande ci-dessous :

```bash
mvn clean javafx:run
```

## Auteurs

Ce projet a été réalisé par [Nicolas](https://github.com/nicolas-riera), [Arthur](https://github.com/arthur-georget) et [Axel](https://github.com/Axel-RODRIGUEZ).