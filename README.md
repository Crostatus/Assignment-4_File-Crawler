# Assignment-4_File-Crawler

Si scriva un programma JAVA che:\
● riceve in input un filepath che individua una directory D. \
● stampa le informazioni del contenuto di quella directory e, ricorsivamente, 
di tutti i file contenuti nelle sottodirectory di D. 

Il programma deve essere strutturato come segue:\
● attiva un thread produttore ed un insieme di k thread consumatori.\
● il produttore comunica con i consumatori mediante una coda.\
● il produttore visita ricorsivamente la directory data ed, eventualmente tutte
le sottodirectory e mette nella coda il nome di ogni directory individuata.\
● i consumatori prelevano dalla coda i nomi delle directories e stampano il
loro contenuto.\
● la coda deve essere realizzata con una LinkedList. Ricordiamo che una Linked\
List non è una struttura thread-safe. Dalle API JAVA “Note that the
implementation is not synchronized. If multiple threads access a linked list\
concurrently, and at least one of the threads modifies the list structurally, it must be
synchronized externally”

-----------------------------------------------------------------------------------------------------------------

Write a JAVA program that:\
● receives a directory filepath as input.\
● prints all the informations contained in that directory and, recursively, of every subdirectory encountered.

The program must:\
● Activate a producer thread and a set of 'k' consumers threads.\
● The producer comunicates with the consumers using a queue.\
● The producer puts the name of every new directory he finds in the queue.\
● The consumers gets a directory name from the queue, then prints all his content.\
● The queue must be implemented as a LinkedList. Remember that LinkedList is not a thread-safe data structure, it must be
synchronized externally.
