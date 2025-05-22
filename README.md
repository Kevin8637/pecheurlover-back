Pecheur-Lover Backend

Description :
Pecheur-Lover est une API REST permettant la gestion des produits, des commandes, ainsi que l'authentification des utilisateurs à l'aide de tokens JWT. Cette API est construit autour du projet pecheur-lover frontend

Langage principal : Java

Framework : Spring Boot

Spring Security pour la gestion des tokens JWT

Base de données : MySQL


Outils complémentaires : 
 - JWT pour l'authentification sécurisée

Packages et architecture :
 - controller : Gestion des contrôleurs d'API
 - service : Logique métier
 - dao : Accès aux données (Data Access Object)
 - entities : Modèles de données
 - exceptions : Gestion des exceptions
 - security : Gestion de la sécurité (JWT, filtres)


Fonctionnalités principales :
 - Authentification JWT
 - CRUD pour : Product, Invoice, Orders
 - CR pour : User


API REST :
L'API permet de :
 - Créer, lire, mettre à jour et supprimer des produits, des factures et des commandes.
 - Créer et lire des utilisateurs.
 - Authentification via JWT pour sécuriser les endpoints.


Installation et lancement :
Pré-requis JDK : version 23.0.1
Étapes pour lancer le projet en local
 - Cloner le repo : git clone {clé SSH}
 - Configurer les variables d'environnement
 - Créer un fichier .env et configure-le avec les informations suivantes en te basant sur le fichier .envsample :
    - DB_URL=jdbc:mysql://localhost:3306/pecheur_lover_db (L'URL de la base de données MySQL)
    - DB_USERNAME=ton_username (Le nom d'utilisateur pour la base de données)
    - DB_PASSWORD=ton_mot_de_passe (Le mot de passe associé à l'utilisateur MySQL)
    - SECRET_KEY=ton_secret_jwt (Clé secrète pour générer les tokens JWT)
    - JWT_EXPIRATION=3600 (Durée de validité du token JWT (en secondes))
 - Lancer le serveur local


Pour démarrer le serveur local :

Aller dans PecheurLoverApplication et lancer le serveur local



Contributeurs
Kevin8637
