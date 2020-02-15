Istruzioni per avviare l'applicazione:
- importare il progetto come maven
- OPZIONALE: creare un database di prova eseguendo la classe InserimentoDBdiProva.java in src/test/java 
nel package gruppobirra4.brewday
- avviare la classe JHome.java in src/main/java nel package gruppobirra4.brewday.gui


Istruzioni per l’utilizzo
- HOME: pagina d’avvio dell’applicazione, comprende una serie di pulsanti che permettono di passare 
alla pagina desiderata
- CATALOGO INGREDIENTI: pagina contenente tutti gli ingredienti inseriti nell’applicazione con relativa 
quantità disponibile, permette di aggiungere un nuovo ingrediente, modificare, ed eliminare un ingrediente selezionato
- RICETTARIO: pagina contenente tutte le ricette presenti nell’applicazione, permette di creare, aprire una 
ricetta che si vuole modificare o eliminare una ricetta selezionata
- LISTA DELLA SPESA: pagina contenente, come anticipato dal nome, una lista della spesa per gli ingredienti 
che l’utente vuole acquistare, all’utente è permesso aggiungere un nuovo ingrediente, modificarne la quantità 
da acquistare ed eliminare un ingrediente selezionato nella lista o svuotare completamente la lista, permette
poi di acquistare un singolo ingrediente oppure di acquistare tutti gli ingredienti presenti
- LISTA LOTTI: pagina contenente tutti i lotti precedentemente prodotti, permette di aprire un lotto in modo da 
poterne modificare le note oppure di eliminarlo
- BIRRA DEL GIORNO: pagina contenente una funziona che restituisce, in base alla quantità di birra da produrre 
inserita, la pagina della ricetta che massimizza l’utilizzo degli ingredienti presenti nel catalogo.