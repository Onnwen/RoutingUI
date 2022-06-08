# Routing
Routing è un software realizzato interamente in Java eseguibile su tutte le macchine compatibili. Il suo scopo è quello di definire i percorsi migliori in base al numero di nodi da attraversare e al facoltativo costo di ciascun collegamento. Il suo funzionamento, basato sulla logica di un grafo pesato, rappresenta il modo più semplice per rappresentare internet e la rete mondiale che ormai ci affianca giorno per giorno nelle nostre vite.
## Interfaccia utente
L’interfaccia utente è stata studiata in modo da risultare semplice e intuitiva anche agli utenti meno esperti. L’approccio impiegato consiste, da una parte, nella gestione delle reti e del percorso da individuare e, dall’altra, la rappresentazione grafica e istantanea della rete, dei nodi selezionati e del percorso identificato.
Dalla lista “reti” è possibile selezionare la rete sulla quale si vuole effettuare le simulazione. Le reti sono caricate dalla cartella /Networks e possono essere modificate a proprio piacimento dall’utente. A destra, vengono mostrati gli identificativi dei dispositivi della rete attualmente selezionata.
L’utente può selezionare le due estremità del percorso tramite due input di tipologia drop-down, quindi inserire ulteriori e facoltativi campi quali, per esempio, il costo e i salti massimi. Una volta definiti i parametri della simulazione, quest’ultima può essere avviata tramite l’apposito tasto “Calcola percorso”.
A simulazione completata, l’utente visualizza i risultati nell’ultima sezione. È possibile selezionare uno tra i più percorsi validi trovati e visualizzarne ogni tappa, i costi medi, i tentativi effettuati, eccetera
Sulla destra è presente la rappresentazione grafica delle rete che viene generata istantaneamente e simultaneamente alle scelte eseguite dall’utente. Sono rappresentati in blu i nodi della rete e i vari collegamenti tra di essi. In rosso, sono i nodi scelti dall’utente e i collegamenti individuati dall’algoritmo di routing per effettuare il collegamento.
## Storia, sviluppo ed evoluzione
Lo sviluppo del progetto è stato avviato a inizio Marzo dell’anno scolastico 2021/2022 con, in primo luogo, la realizzazione del software con interfaccia utente nel terminale. La diffcoltà di questa prima fase di sviluppo consisteva nel progettare un funzionante ed efficiente algoritmo di routing che rispettasse le linee guida date. Successivamente, il dilemma maggiore era come rappresentare, intuitivamente e all’interno del terminale, i dati elaborati. I percorsi individuati vengono suddivisi grazie all’uso di diversi glifi è possibile riconoscere in un batter d’occhio le scritte che altrimenti risulterebbero troppo impastate e porterebbero a una UX meccanica e non fluida. Con l’evoluzione del progetto si è poi scelto di optare per un’interfaccia utente vera propria, come tutti la intendono: una finestra con la quale si può conversare grazie al proprio mouse o trackpad. L’UI in questione è stata prima progettata e studiata, quindi realizzata col framework JavaFX e l’aiuto del software Scene Builder. La necessità dell’interfaccia utente si è posta dal momento che diventava necessaria una rappresentazione grafica delle reti per comprendere la loro geometria e rendere più immediata agli occhi di tutti le possibili soluzioni evidenziate dall’algoritmo.
## Linguaggi
Il progetto è stato interamente realizzato in Java (SDK 11). File in formato FXML e CSS sono utili alla gestione dell’interfaccia utente. L’uso di Java rende l’applicazione eseguibile su qualunque dispositivo sia confgurata una JVM, ciò significa che il software è compatibile per i sistemi, tra gli altri: macOS, Linux e Windows.
## Frameworks
L’algoritmo di routing è stato interamente realizzato in autonomia, ma per l’interfaccia utente è stato utilizzato il framework JavaFX (https://openjfx.io) e per la rappresentazione grafica della rete è stata utilizzata la libreria JavaFXSmarthGraph (https://github.com/brunomnsilva/JavaFXSmartGraph.git), però ampiamente personalizzata al contesto e al progetto sviluppato.
