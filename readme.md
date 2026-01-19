## Description du projet et objectifs
TechZone

## Stack technique utilisée

## Architecture du projet 
(packages, couches)

## Instructions de lancement 
Lancement docker: docker build -t e-commerce-app .
(prérequis, commandes, URL principales)

## Comptes de test 

Compte admin: 
Adresse email: A REMPLIR
Mot de passe: A REMPLIR

Compte utilisateur:
Adresse email: A REMPLIR
Mot de passe: A REMPLIR

## Diagramme de classes UML 

| Entité JPA | Attributs Principaux | Relations (Annotations) |
| --- | ---  | --- |
|User | id, email, password | @ManyToOne Role, @OneToOne Cart, @OneToMany Order |
| Product | id, name, price, stock | @ManyToOne Category |
| Order | id, reference, status | @ManyToOne User, @ManyToOne OrderStatus, @OneToMany ProductDetailsOrder | 
| Cart | id, reference | @OneToOne User, @OneToMany ProductDetailsCart |
| Payment | id, sum, paymentDate | @OneToOne Order, @ManyToOne PaymentMethod |

## Schéma de base de données 

<img width="1512" alt="Shéma de la base de données" src="https://github.com/user-attachments/assets/MODIFIERICILELIEN" />







