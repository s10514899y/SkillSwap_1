---

# 1. Introduzione 
Il progetto **SkillSwap** è un'applicazione sviluppata in Java che permette lo **scambio di competenze tra studenti**.

L'idea principale è quella di simulare una piattaforma in cui gli utenti possono: 
- offrire competenze (Offer) 
- richiedere competenze (Request)
- essere messi in corrispondenza tramite un sistema di matching 
- effettuare scambi (Exchange)
- lasciare recensioni (Review)

Il sistema è stato progettato seguendo una struttura modulare per separare responsabilità e migliorare la manutenibilità del codice.

---

# 2. Architettura del sistema 
Il progetto segue una **architettura a livelli (leyered architecture)**:

APP > SERVICE > DOMAIN > STATE 

# Codice 

---

## APP (Presentation Layer)

Responsabile di:
- interazione con l'utente 
- input da tastiera 
- stampa su console (terminale)
- visualizzazione dei risultati 

 E' il livello più esterno del sistema.

---

## DOMAIN (Model Layer)
Contiene: 
- entità principali del sistema 
- regole base del dominio 
- struttura dati fondamentale 

 Rappresenta il "cuore" dei dati.

---

## SERVICE (Business Logic Layer)
Contiene:
- logica di business
- algoritmi di matching 
- gestione stati degli scambi 
- sistema di review 

 E' il livello più "intelligente".

---

## STATE (Persistence Layer in-memory)
Contiene: 
- dati in memoria (HashMap)
- studenti, offerte, richieste, skill

 Simula un database. 

---

# 3. PACKAGE APP

---

## Classe *ConsoleReportPrinter* 

### Ruolo 
Classe responsabile della **visualizzazione dei dati su console**. 

### Funzionamento 
Questa classe non contiene logica di business, ma si occupa solo di presentare i dati in modo leggibile. 

---

### *printStudentProfile*
Stampa il profilo di uno studente:
- nome 
- email 
- lista offerte
- lista richieste 

 Serve per visualizzare lo stato completo di un studente.

---

### *printMatches*
Mostra i match tra offerte e richieste.

 Evidenzia le corrispondenze tra utenti. 

---

### *printExchangeDetails*
Mostra i dettagli di uno scambio: 
- offerta 
- richiesta 
- stato 

 Utile per tracciare il ciclo di vita di uno scambio. 

---

### *printLeaderBoard* 
Stampa la classifica studenti basata sugli scambi effettuati. 

 Introduce un sistema competitivo tra utenti.

---

## Classe *Main* 
### Ruolo 
E' il **punto di ingresso dell'applicazione**. 

---

### Funzionamento
- utilizza *Scanner* per input 
- mantiene uno stato globale (*SkillSwapState*)
- esegue un ciclo infinito con menu

---

### Menu 

1. Creazione studente 
2. Aggiunta offer 
3. Aggiunta request
4. Visualizzazione dati
0. Uscita

---

### Spiegazione logica 

Il *Main*: 
- raccoglie input
- crea oggetti del dominio 
- li salva nello state
- delega la logica ai service (indirettamente)

---

# 4. PACKAGE DOMAIN 

---

## Classe *Student* 
### Ruolo 
Rappresenta uno studente del sistema. 

### Attributi: 
- id 
- name
- schoolClass
- email
- ratingAvg 
- ratingCount

### Validazioni: 
- nome non vuoto 
- email non vuota

 Garantisce integrità dei dati. 

---

## Classe *Skill* 
Rappresenta una competenza. 
- id 
- name
- category

 Usata per offerte e richieste. 

--- 

## Classe *Offer* 
Rappresenta una competenza offerta da uno studente. 
- studente proprietario
- skill associata 
- livello 
- stato attivo 

 Definisce cosa un utente poù imparare. 

---

##  Classe *Request* 
Rappresenta una richiesta di apprendimento. 

 Definisce cosa uno studente vuole imparare. 

--- 

## Classe *Exchange* 
Rappreenta uno scambio tra offer e request. 

### Stati:
- PROPOSED 
- ACCEPTED
- COMPLETED
- CANCELLED 

 Simula il ciclo di vita di uno scambio. 

---

## Classe *Review* 
Sistema di valutazione tra utenti. 

### Regola:
- stelle da 1 a 5

 Garantisce qualità del feedback.

---

## Classe *Score* 
Gestisce punteggi numerici. 
- implementa equals/hashCode
- utile per confronti

---
## Enum *Level* 
Definisce livelli di competenza: 
- BEGINNER (1)
- INTERMEDIATE (2)
- ADVANCED (3)

### Metodo 
- confronto tra livelli (*isSufficient*)

---

# 5. PACKAGE SERVICE

---

### Stati: 
- PROPOSED 
- ACCEPTED
- COMPLETED
- CANCELLED

---

### Regole: 
- non si può accettare senza proposta 
- non si pù completare senza accettazione 
- non si possono usare valori nulli 

 Garantisce consistenza dello stato. 

## Classe *MatchingService* 
### Ruolo
Calcola la compatiblità tra utenti. 

--- 

### Algoritmo di scoring: 
- skill uguale +3
- livello compatibile +2 
- stessa classe +1 

 Più alto il punteggio, migliore il match. 

---

## Classe *ReviewService*
### Ruolo 
Gestisce le recensioni. 

---

### Validazioni: 
- stelle tra 1 e 5 
- commento non vuoto 

 Evita dati incoerenti. 

---

## Database (simulato)
- salva recensioni in memoria
- calcola media rating per exchange 

 Simula un database reale. 

---

## Enum *Status*
Stati dello scambio:
- PROPSED 
- ACCEPTED
- COMPLETED
- CANCELLED

---
## 6. PACKAGE STATE

---

## Classe `SkillSwapState`

### Ruolo

Contenitore centrale dei dati.

---

### Struttura:

- students
- skills
- offers
- requests
  
---

### Implementazione:

Utilizza `HashMap` per accesso O(1).

---

### Spiegazione

Questo componente funge da:
- memoria dell’applicazione
- alternativa a un database
- punto centrale di sincronizzazione dati

---

# 7. FLUSSO DEL SISTEMA

1. L’utente interagisce con `Main`

2. I dati vengono salvati in `SkillSwapState`

3. Le entità vengono create nel `domain`

4. La logica viene applicata nei `service`

5. I risultati vengono stampati dalla `app`

---

# 8. OBIETTIVO DEL PROGETTO

Il sistema SkillSwap permette:
- scambio di competenze tra studenti
- gestione offerte e richieste
- matching automatico
- sistema di reputazione

---

# 9. CONSIDERAZIONI FINALI

##  Punti di forza:
- architettura modulare
- separazione delle responsabilità
- codice estendibile

## Possibili miglioramenti:
- database reale
- API REST
- interfaccia grafica
- algoritmo di matching avanzato

---

# CONCLUSIONE

Il progetto SkillSwap rappresenta una simulazione completa di una piattaforma di scambio competenze, sviluppata con una chiara separazione tra dati, logica e interfaccia, garantendo modularità e scalabilità.
