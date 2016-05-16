# INGEGNERIA/PROGETTAZIONE DEL SOFTWARE — Laboratorio 9

## Processo

Questo laboratorio si svolge in **gruppi di quattro** studenti (suddivisi in
due **coppie da due** studenti ciascuna), il risultato *definitivo* del lavoro
va consegnato entro le 12:30 del giorno stesso in cui il laboratorio si
svolge.

Ogni gruppo di quattro studenti sceglie il **leader** che procede a [formare
il gruppo](https://sweng.di.unimi.it/gruppo) e ciascuno studente *clona* il
**fork del leader** di questo repository sulla propria macchina usando il
comando `git clone` riportato alla creazione del gruppo *(in nessun caso deve
essere clonato questo repository, ma solo il fork creato nell'account del
leader)*.

Una volta clonato il repository, il gruppo si *divide in coppie* e ciascuna
coppia implementa secondo la metodologia del TDD una parte delle specifiche
riportate di seguito; in maggior dettaglio, dopo aver concordato con l'altra
coppia le modalità di divisione ed organizzzione del lavoro, ogni coppia
ripete i passi seguenti fino ad aver implementato, in modo **indipendente**
dall'altra, tutte le funzionalità richieste dalla parte scelta:

* sceglie un **breve** (indicativamente di 15m) passo di sviluppo,
* scrive il codice di un *test* per il passo scelto, verificando che **il
  codice compili correttamente**, ma l'**esecuzione del test fallisca**;
  se lo ritiene utile (ossia *facoltativamente*, ma non prima di trovarsi
  in queste condizioni) effettua un *commit* (usando `git add` e `git commit`)
  iniziando il messaggio di commit con la stringa `ROSSO:`,
* aggiunge l'implementazione necessaria a realizzare il passo relativo al test
  introdotto, in modo che **il test esegua con successo**; a questo punto *deve*
  effettuare un *commit* (usando `git add` e `git commit`) iniziando il messaggio di
  commit con la stringa `VERDE:`,
* procede, se necessario, al **refactoring** del codice, accertandosi che le modifiche
  non comportino il fallimento di alcun test; solo in questo caso fa seguire ad ogni
  passo di questo tipo un *commit* (usando `git add` e `git commit`) iniziando il
  messaggio di commit con la stringa `REFACTORING:`,
* effettua un *push* dei passi svolti su Bitbucket con `git push origin master`.

Al termine di questa fase, le due coppie **integrano** il loro lavoro, mettendo
le classi scritte indipendentemente in grado di cooperare; a tale scopo
possono aggiungere eventuali test di integrazione (procedendo in maniera
analoga alla prima fase di sviluppo).

Al termine del periodo di lavoro il gruppo effettua un ultimo *push* e
**verifica su Bitbucket** che ci sia la completa traccia di *commit*
effettuati. Si suggerisce di eseguire i test non soltanto con Eclipse, ma
anche eseguendo il comando `./gradlew build` da riga di comando.

## Specifice

Scopo dell’esercizio di è implementare una **calcolatrice per semplici
espressioni aritmetiche**, basata sulla **notazione polacca inversa**.

### La notzione polacca inversa

Sin dalle elementari siamo abituati ad utilizzare la cosidetta *notazione
infissa*, in cui cioè l’operazione viene scritta tra gli operandi, come in `6
+ 5`, o `3 * 2`; questa notazione presenta però un problema nel caso in cui
vengano "mescolate" più operazioni nella stessa epressione; ad esempio
```
3 * 2 + 5
```
può valere `11` se eseguiamo prima `3 * 2` e quindi sommiamo `5`, oppure `21`
se seguiamo prima `2 + 5` e quindi moltiplichiamo per `3` il risultato
parziale.

Per risolvere questa ambiguità, viene usualmente definito un concetto di
*precedenza* tra le operazioni, ma implementare una calcolatrice per
espressioni infisse che tenga conto della precedenza delle operazioni non è
affatto banale.

Viceversa, come vedremo, c'è un modo elementare di implementare una
calcolatrice a patto di cambiare la notazione. Nulla vieta infatti di scrivere
l’operazione prima degli operandi, secondo la *notazione prefissa*, come in `+
6 5` e `* 3 2`. Questa notazione è stata introdotta1 dal logico polacco Jan
Łukasiewicz, ragione per la quale è anche chiamata *notazione polacca*. Un
grande vantaggio di questa notazione è che l’operazione in qualche senso
"annuncia" in anticipo quel che dobbiamo fare, ragion per cui non c’è più
alcuna ambiguità sull'ordine in cui eseguire le operazioni.

Vediamo qualche esempio. L’espressione `+ * 3 2 5` può essere "raggruppata"
(qui con l'uso delle parentesi quadre) solo come `+ [* 3 2] 5`: non c’è
nessun altro modo che abbia senso: se si intendesse `[+ * 3] 2 5`, oppure `+ *
[3 2 5]`, questo violerebbe la convenzione per cui una espressione è data da
una operazione seguita dai suoi operandi (che possono essere a loro volta
espressioni, ma che devono sempre seguire la medesima convenzione). Stesso
discorso vale per `* 3 + 2 5`: anche in questo caso, si intende  `* 3 [+ 2
5]`, perché non c’è altro modo legittimo di intendere l’espressione!
Facciamo un ultimo esempio, più complicato, che ci servirà per illustrare
l'*algoritmo di valutazione* di queste espressioni; consideriamo l'espressione
```
+ * 3 4 * 5 + 6 7
```
che vale `72`: infatti si tratta di una somma (inizia per `+`) tra le due
espressioni `* 3 4` (che vale `12`) e `* 5 + 6 7` (che vale `60`);
quest'ultima, a sua volta, è un prdotto (inizia per `*`) tra `5` e
l’espressione `+ 6 7` (che vale `12`).

#### E le parentesi?

Un'altra apparente soluzione può essere l'uso di parentesi. Tale approccio non
è ragionevole perché, se le parentesi fossero oobbligatorie per ogni
sottoespressione, ciò condurrebbe ad espressioni inutilmente "verbose", come
ad esempio `( ( ( ( ( 1 + 2 ) + 3 ) + 4 ) + 5 ) + 6 )`; d'altro canto, se le
parentesi fossero facoltative, resterebbe ancora la necessità di definire come
valutare espressioni come `3 * 2 + 5`.


### Algoritmo di valutazione

Per valutare una espressione in notazione polacca è sufficiente una [pila](https://en.wikipedia.org/wiki/Stack_(abstract_data_type))
di valori numerici; per comodità nell'esposizione seguente la rappresenteremo
come una sequenza di numeri racchiusi tra `⟨⟩`, aggiungendo nuovi valori a
sinistra e togliendoli sempre da sinistra.

Data una pila, l'algoritmo di valurazione è banalmente il seguente:

* partiamo da una pila vuota
* leggiamo l’espressione da destra a sinistra
     * se incontriamo un numero, lo mettiamo sulla pila,
     * se incontriamo una operazione:
           * preleviamo due valori dalla pila,
           * li componiamo con l’operazione che abbiamo incontrato,
           * rimettiamo il risultato nella pila,
* al termine dell'espressione, la pila conterrà un solo valore: il risultato!

Proviamo con l'ultimo esempio della precedente sezione, l'espressione `+ * 3 4
* 5 + 6 7`. All'inizio, la pila sarà `⟨⟩`, leggiamo `7` e lo mettiamo sulla
pila, che diventerà `⟨7⟩`, ora leggiamo `6` e la pila diventerà `⟨6 7⟩`. Ora
incontriamo un `+`, preleviamo due valori dalla pila, che sono `6` e `7`
(quindi la pila diventerà `⟨⟩`), li sommiamo e scriviamo il risultato `12`
sulla pila, che adesso sarà `⟨12⟩`. Proseguiamo leggendo `5` e mettendolo
sulla pila rendendola pari a `⟨5 12⟩`, troviamo il `*`, togliamo due valori
dalla pila, facciamo il prodotto e mettiamo sulla pila il risultato, così che
la pila diventerà `⟨60⟩`. Incontriamo quindi `4` e poi `3` che andranno sulla
pila, rendendola complessivamente `⟨3 4 60⟩`, a questo punto troviamo il `*`,
togliamo due valori dalla pila e mettiamo sulla pila il loro prodotto,
rendendo la pila uguale a `⟨12 60⟩`. Ci siamo: manca l’ultimo `+`, leviamo due
valori dalla pila e rimettiamo la somma: alla fine la pila sarà `⟨72⟩` che,
come promesso, è il valore dell’espressione!

Si può dimostrare (per induzione sulla lunghezza dell’espressione) che
l’algoritmo appena descritto è corretto, ovvero che funziona non solo
sull’esempio precedente, ma per qualunque espressione in notazione polacca.

Poiché è scomodo leggere l’espressione da destra a sinistra, possiamo
considerare direttamente la *notazione postfissa*, detta anche *notazione polacca
inversa*, in cui si scrivono prima gli operandi e quindi l’operazione. Il che,
semplicemente, corrisponde a riscrivere, da destra a sinistra, una espressione
in notazione prefissa. Ad esempio, l’espressione di cui sopra diventa
```
7 6 + 5 * 4 3 * +
```

### Note implementative

La creazione della calcolatrice pervede (almeno) l'implementazione di tre
interfacce:

* [Calculator](/src/main/java/it/unimi/di/sweng/lab09/Calculator.java)
  che descrive il comportamento della calcolatrice,
* [Tokenizer](/src/main/java/it/unimi/di/sweng/lab09/Tokenizer.java)
  che modella la tokenizzazione dell'espressione, ossia l'individuazione delle
  operazioni aritmetiche e dei valori numerici nella stringa che la codifica,
* [Stack](/src/main/java/it/unimi/di/sweng/lab09/Stack.java)
  che modella la pila necessaria per l'algoritmo di valutazione.

Maggiori dettagli sui comportamenti di tali implementazioni, nonché sulle
*segnature dei metodi* e sulle *eccezioni* sollevate sono disponibili nella
[documentazione delle classi](http://sweng201608m.surge.sh).

Si ricorda che è possibile generare una copia locale della documentazione con
il comando
```
./gradlew javadoc
```
a sequito del quale la documentazione può essere visualizzata aprendo il file
`build/docs/javadoc/index.html`.


#### Mockito

Secondo il processo descritto in precedenza, nella parte iniziale del lavoro
il gruppo si divide in coppie ed organizza il lavoro, ripartendosi
l'implementazione delle specifiche nel modo che ritiene più equo.

Poiché ciascuna coppia deve lavorare indipendentemente, si consiglia di fare
uso della libreria [Mockito](http://mockito.org/) per provvedere ai SUT i
*mock* e gli *stub* necessari al testing.

#### Integrazione e accettazione

Al termine delle fasi indipendenti di sviluppo, il gruppo elabora dei test di
*integrazione* e *accettazione* propri per verificare il comportamento
complessivo del software realizzato.

Inoltre, effettua il *merge* col *branch* remoto `qa_acceptance` usando il
comando
```
git merge origin/qa_acceptance -m'QA Acceptance'
```
che aggiungerà il file `src/test/java/it/unimi/di/sweng/lab09/QATest.java` alla
directory di lavoro.

Dopo aver provveduto a istanziare la propia implementazione dell'interfaccia
[Calculator](/src/main/java/it/unimi/di/sweng/lab09/Calculator.java) nel
metodo `setUp` di tale file, ma **senza modificarlo ulteriormente**, il gruppo
esegue l'intera batteria di test con il comando `./gradlew check`, riparando
agli eventuali fallimenti prima della consegna finale.

#### Coverage

Si richiede che i test sviluppati nelle varie fasi di sviluppo (e quelli di
QA) diano luogo ad una *copertutra completa* del codice, così come misurata
con [Jacoco](http://eclemma.org/jacoco/).

Al fine di ottenere il *report* sulla copertura è sufficiente invocare il
comando
```
./gradlew clean check jacocoTestReport
```
a sequito del quale il report può essere visualizzato aprendo il file
`build/reports/jacoco/index.html`.



