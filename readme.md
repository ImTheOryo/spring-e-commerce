![logo.png](readme-assets/logo.png)

## Description du projet et objectifs
TechZone est une plateforme e-commerce conçue pour répondre aux besoins d'une boutique spécialisée dans les produits high-tech (PC, smartphones, accessoires). L'application gère un catalogue complexe de plusieurs centaines de références, tout en assurant le suivi des commandes et la gestion des stocks pour l'équipe interne.

L'enjeu principal est de fournir une expérience fluide pour les clients, tout en offrant une interface de gestion sécurisée et performante pour les administrateurs.
## Stack technique utilis
| Catégorie   | Outils                | Rôle |
|-------------|-----------------------| --- |
| Langage     | Java 24               | Langage principal |
| Framework   | Spring Boot           | Auto-configuration et gestion des dépendances |
| Persistence | 	Spring Data JPA / H2 | Gestion de la BDD (H2) |
| Sécurité    | Spring Security + JWT | Protection des routes et gestion des rôles ADMIN/USER |
| Frontend    | Thymeleaf             | Moteur de templates pour l'affichage dynamique |
| Build       | Maven                 | Gestion du cycle de vie et du build |

## Architecture du projet 

src/</br>
├── main/</br>
├────  controller/</br>
├────  model/</br>
A FINIR

## Instructions de lancement 
Pour le lancement avec docker :<br/>
```docker-compose up --build```

Pour la connexion a la BDD H2 utiliser les settings suivants : <br/>
``` 
database : jdbc:h2:file:/app/data/ecommerce_db
username: sa
password: 
```
## Comptes de test 

### Compte admin: <br/>
&emsp; Adresse email: A REMPLIR<br/>
&emsp; Mot de passe: A REMPLIR

### Compte utilisateur:<br/>
&emsp; Adresse email: A REMPLIR<br/>
&emsp; Mot de passe: A REMPLIR

## Diagramme de classes UML 

| Entité JPA | Attributs Principaux | Relations (Annotations) |
| --- | ---  | --- |
|User | id, email, password | @ManyToOne Role, @OneToOne Cart, @OneToMany Order |
| Product | id, name, price, stock | @ManyToOne Category |
| Order | id, reference, status | @ManyToOne User, @ManyToOne OrderStatus, @OneToMany ProductDetailsOrder | 
| Cart | id, reference | @OneToOne User, @OneToMany ProductDetailsCart |
| Payment | id, sum, paymentDate | @OneToOne Order, @ManyToOne PaymentMethod |

## Schéma de base de données 

![bdd.png](readme-assets/bdd.png)





