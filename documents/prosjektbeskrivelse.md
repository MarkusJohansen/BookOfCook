# Prosjektbeskrivelse

## Beskrivelse av appen

*Kort om appen, hva skal den gjøre? Hva er målet med appen?*

*Applikasjonen skal gjøre om den analoge "Oppskriftsboka" til en digital kokebok, samt  tilby brukeren ett "Kjøleskapsverktøy" som finner oppskrifter basert på det brukeren har i sitt kjøleskap. Brukeren skal kunne opprette mat-oppskrifter som lagres i en kokebok. En sentral egenskap i applikasjonen blir hvordan brukeren kan filtrere sine oppskrifter ved hjelp av kategorier og sjekkbokser. Det vil også legges inn støtte for å endre antall personer retten skal lages til og endre mengden ingredienser basert på dette. tilsvarende vil også kalorier oppgitt for en rett kunne endres med mengden.

## Grunnklasser

*Fortelle kort hva (minimun to, kan flere) grunnklassene skal inneholde, og hvilken klasse som skal ha noen form for kalkulasjoner eller annen logikk.*

**```Oppskrift-klasse```**

* Oppskrift klassen kan regne ut eventuelt kalorier
* Kan regne ut ingrediens mengde for et gitt antall mennesker
* Holder arrays med ingredienser som har en tilhørende mengde i enhet (2stk, 3cl, 4l, 5g 2ss etc)
* Holder et navn, antall mennesker beregnet på, kategorier, fremgangsmåte, utstyr, ca. tid, vanskelighetsgrad, bilder.
* Vil ha getter og setter metoder for disse
* metoder for endring av oppskrift

**```Kokebok-klasse```**

* Inneholder kategoriene som er opprettet og kan hente ut disse, enkeltvis
* Inneholder kategori-grupper som feks vanskelighetsgrad og kan hente ut alle oppskrifter som har kategorier plassert i denne gruppen, slik som vanskelig, lett, medium etc
* Inneholder oppskrifter.
* Filter oppsjoner metoder (Kan gi ut oppskrifter som er større eller lik et gitt antall kalorier per pers)
* Inneholder alle navigasjons metoder og filtrerings metoder
* metoder for sletting av oppskrifter
* metoder for å opprette oppskrifter

**```Kategori-klasse```**

* Kategori navn
* Oppskrifter
* Metoder for å legge til og fjerne oppskrifter fra en kategori-klasse

## Filbehandling

*Kort setning om hvilken informasjon som skal leses fra og skrives til fil.*

Oppskriftene i samme kokebok lagres i én .json fil, slik at det vil være mulig å lagre kokebøker lokalt eller i skyen. Dette gir lagringsoppsjon for kokebøkene; nøkkelfunksjonen i applikasjonen.


## Testing

*Kort om hva som skal testes i appen. Merk: I den delen av prosjektet som går på testing, krever vi at den viktigste funksjonaliteten dekkes av tester, og ønsker derfor at dere tenker over dette allerede nå, slik at dere unngår å lage prosjekter som blir store og omfattende å lage tester til.*

### Kategorier

* Teste at du får plassert oppskrifter i riktig kategori
* teste at kategoriene tar inn flere oppskrifter og husker dette.
* teste kategori filter
* teste støtte for flere kategorier per oppskrift
* teste støtte for flere kategori filter samtidig

🠗🠗🠗

* Teste at en kategori inneholder oppskrift(er) etter man har kjørt metoden som legger oppskriften(e) til.
* Tester at riktig kategori(er) blir returnert når filtreringsmetoden kjøres.
* Tester at oppskrift inneholder kategori, etter oppskrift har blitt lagt til i kategori

### Oppskrifter

* Teste oppretting og lagring av oppskrifter
* teste endring og lagring av endring på oppskrifter

🠗🠗🠗

* Teste at oppskrifter blir lagret i .json-filer
* Teste at oppskrifter overskriver sin ...???

### Kokebok

* inneholder riktige oppskrifter.
* skalering av ingrediensmengde og kalorier i oppskrifter.

🠗🠗🠗

* Teste at kokebok inneholder oppskrift etter metode for å legge til oppskrift har blitt kjørt.
* Teste at skalering av antall personer stemmer overens med ingrediensmengden.
* Teste at antall kalorier per person stemmer overens med ingrediensmengde.

### Kjøleskapsverkøyet

* sjekke at den finner riktig antall oppskrifter basert på ingredienser
* sjekke at den ikke tar med oppskrifter du ikke har alle ingredienser til
* sjekke at den virker sammen med filtre

🠗🠗🠗

* Teste at riktig antall oppskrifter vises basert på ingredienser etter filtreringsmetoden er kjørt
* Teste at ingrediens blir lagt til i kjøleskapet etter legg-til metoden er kjørt

### Kjøring av app

* Teste at appen fungerer
* Teste at grensesnittet virker

🠗🠗🠗

* Trenger ikke tester til dette??
### Generelt

* Validering av alle sentrale tilstander.
* sjekke innkapsling
* Teste Skriving av fil
* Teste korrekt innlastning av fil.
* sjekke at brukeren ikke mister sine filer når programmet lukkes.

🠗🠗🠗

???