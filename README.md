# ITProjektSS15
Ein verteiltes System zum Management von Strukturstücklisten, wie sie z.B. in der Produktionsplanung und -steuerung Anwendung
finden.

# Beschreibung des Zielsystems
Jede Gruppe hat die Aufgabe, ein verteiltes System zum Management von Strukturstücklisten, wie sie z.B. in der Produktionsplanung
und -steuerung Anwendung finden, zu realisieren. Stücklisten stellen die Struktur von Baugruppen dar. Eine Baugruppe ist ein 
spezielles Bauteil, das aus weiteren Baugruppen und Bauteilen besteht, zu denen jeweils deren Anzahl in der übergeordneten
Baugruppe anzugeben ist. Bauteile und Baugruppen können in beliebig vielen Stücklisten verwendet werden. Vergleichen Sie dies 
z.B. mit der Verwendung eines bestimmten Schraubentyps oder eines bestimmten Motors in PKWs. Ein Enderzeugnis ist eine spezielle 
Baugruppe. Das System soll das Erstellen, Verwalten und Anzeigen von Stücklisten sowie deren Elemente ermöglichen. Für eine
solche Funktionsweise müssen dem System die Begriffe Stückliste, Baugruppe, Bauteil, Enderzeugnis bekannt sein. Objekte der 
vorgenannten Begriffe (Klassen) sollen in dem Zielsystem jederzeit angelegt, editiert und gelöscht werden können. Außerdem
soll zu jedem Objekt dieser Klassen die Identität des letzten Bearbeiters sowie der Zeitpunkt der letzten Bearbeitung gespeichert
werden und jederzeit nachvollziehbar sein. Für jedes Element kann eine Reihe von Attributen festgelegt werden. Dies sind für
Bauteile mindestens: Teilnummer, Bezeichnung und textuelle Beschreibung des Bauteils, Materialbezeichnung (z.B. Eisen, Stahl, 
Messing). Jeder Stückliste soll eine Listennummer, ein Name sowie das Datum der Erstellung (dieses ggf. automatisch) zuordenbar
sein. Baugruppen besitzen ebenso mindestens eine Nummer, einen Namen sowie ein Datum der letzten Änderung (auch dieses ggf.
automatisch). Enderzeugnisse sind speziell gekennzeichnete Baugruppen. Gemeinsame Attribute werden dabei vorteilhaft in einer
gemeinsamen Basisklasse definiert.
