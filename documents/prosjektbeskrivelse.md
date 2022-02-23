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

* Teste at en kategori inneholder oppskrift(er) etter man har kjørt metoden som legger til oppskriften(e) i kategorien(e).
* teste at en oppskrift inneholder en kategori etter man har kjørt metoden som legger til oppskriften(e) i kategorien(e). n-n relasjon
* Tester at riktig oppskrifter(er) blir returnert når filtreringsmetoden kjøres. Det vil si at når kun oppskrifter som har de kategoriene vi vil hente ut, som blir returnert.

### Oppskrifter

* Teste at oppskrifter blir skrives til .json-filer på en korrekt måte når man bruker writeToFile metoden.
* Teste at JSON-filen oppdateres ved endring av innhold (ingredienser, fremgangsmåte, bilde, utstyr) i en oppskrift.

### Kokebok

* Teste at kokebok inneholder oppskrift etter metode for å legge til oppskrift har blitt kjørt i kokebok.
* Teste at skalering av antall personer stemmer overens med ingrediensmengden. alle skal få nok mat.
* Teste at antall kalorier per person stemmer overens med ingrediensmengde og skalering av oppskrift.

### Kjøleskapsverkøyet

* Teste at riktig antall oppskrifter vises basert på ingredienser etter filtreringsmetoden for kjøleskapsverktøyet er kjørt.
* teste at alle oppskrifter som vises kan lages med de ingrediensene man oppgir at man har i kjøleskapet. kjøleskapsverktøyet skal ikke gi feilinfo om hva du får til å lage.
* Teste at ingrediens blir lagt til i kjøleskapet etter legg-til-i-kjøleskap metoden er kjørt.
