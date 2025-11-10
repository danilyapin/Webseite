# Mietgeräte Verwaltungs- und Buchungssystem

Das **Mietgeräte Verwaltungs- und Buchungssystem** ist eine eigenständige Webanwendung zur **Verwaltung und Vermietung von Mietgeräten**.  
Kunden können Geräte auswählen, die Verfügbarkeit über einen Kalender prüfen und Buchungen direkt online durchführen.  
Nach erfolgreicher Buchung wird automatisch eine **Buchungsbestätigung per E-Mail** an den Kunden und den Administrator gesendet.

Administratoren können alle Buchungen und Artikel im System verwalten, neue Geräte hinzufügen, bearbeiten oder löschen  
sowie den gesamten Buchungsprozess überblicken.

Das Projekt wurde **im Rahmen der Abschlussprüfung zum Fachinformatiker für Anwendungsentwicklung**  
bei der **Multimedia Display GmbH** entwickelt und umgesetzt.

---

## Ziel des Projekts

Ziel war es, ein vollständiges und praxisnahes **Verwaltungssystem für Mietgeräte** zu entwickeln,  
das sowohl die Anforderungen von Kunden (Buchung und Übersicht) als auch die von Administratoren (Verwaltung und Kontrolle) abdeckt.  

Der Fokus lag auf:
- einer **klaren, benutzerfreundlichen Oberfläche**  
- **automatisierten Abläufen** (z. B. E-Mail-Versand bei Buchungen)  
- einer **robusten Backend-Architektur** auf Basis von Spring Boot  
- **praxisorientiertem Einsatz**  wie in einem echten Unternehmenskontext

---

## Hauptfunktionen

### **Für Kunden**
- **Registrierung und Login**  
  Benutzer können eigene Konten erstellen und sich sicher anmelden.  
- **Geräteübersicht und Detailseiten**  
  Alle verfügbaren Geräte werden mit Beschreibung, Preis und Bild dargestellt.  
- **Kalenderintegration**  
  Verfügbarkeit prüfen und freie Tage direkt einsehen.  
- **Buchung durchführen**  
  Buchung über die Webseite mit automatischer E-Mail-Bestätigung an Kunde und Admin.  
- **Buchungshistorie**  
  Übersicht aller eigenen Buchungen im persönlichen Bereich.

### **Für Administratoren**
- **Artikelverwaltung**  
  Geräte hinzufügen, bearbeiten oder löschen.  
- **Buchungsverwaltung**  
  Alle Buchungen im System einsehen, bestätigen oder stornieren.  
- **E-Mail-Benachrichtigungen**  
  Automatische Mitteilungen bei neuen Buchungen.  
- **Übersichtliches Admin-Dashboard**

---

## Technologie-Stack

**Backend:**
- Spring Boot (Java)
- Spring Data JPA / Hibernate
- MySQL-Datenbank
- JavaMail / Spring Mail (E-Mail-Benachrichtigung)
- Lombok, Validation, Security

**Frontend:**
- React
- React-Bootstrap für UI-Komponenten und Layout
- CSS3 / eigene Styles für Anpassungen
- react-datepicker für Kalender und Verfügbarkeit
- react-quill für Rich-Text-Felder
- Bootstrap Icons für Symbole
- Axios für API-Aufrufe

---

## Entwicklungsphasen

Das Projekt wurde innerhalb von **zwei Wochen** im Rahmen der Abschlussprüfung umgesetzt.  
Während der Entwicklung wurden **eine Präsentation** erstellt und **eine schriftliche Projektdokumentation** verfasst,  
um den Projektfortschritt und die Funktionsweise des Systems zu dokumentieren.

### Woche 1 – Planung und Backend
- Anforderungsanalyse und Datenmodellierung  
- Einrichtung des Spring Boot Projekts  
- Implementierung der Datenbank und Repositories  
- Buchungslogik und E-Mail-Funktion

### Woche 2 – Frontend und Integration
- Benutzeroberfläche mit Thymeleaf und Bootstrap entwickelt  
- Kalender zur Verfügbarkeitsprüfung integriert  
- Kunden- und Adminfunktionen implementiert  
- Testphase und Vorbereitung der Präsentation und Dokumentation

---

## Deployment

Das Projekt wurde **nicht online bereitgestellt**,  
da im Rahmen der Abschlussprüfung **kein Deployment vorgesehen** war.  
Die Anwendung wurde jedoch vollständig lokal implementiert und getestet.  

Ein späteres Deployment auf Plattform wie **Render** wäre problemlos möglich.

---

## Learnings

- Konzeption und Umsetzung eines kompletten Websystems mit Spring Boot  
- Anwendung von Datenbankstrukturen und ORM mit JPA  
- Integration von Kalender- und Buchungslogik  
- E-Mail-Versand und Benachrichtigungssystem mit Spring Mail  
- Benutzerrollen und Authentifizierung  
- Projektplanung, Präsentation und Dokumentation im Rahmen der Ausbildung  

---

## Projektkontext

Dieses Projekt wurde im Rahmen der **Abschlussprüfung zum Fachinformatiker für Anwendungsentwicklung**  
bei der **Multimedia Display GmbH** entwickelt.  
Während der Entwicklung wurden **Präsentation und Projektdokumentation** erstellt,  
um den Projektfortschritt zu demonstrieren und die Ergebnisse verständlich zu präsentieren.

---

## Fazit

Das Projekt zeigt, wie sich ein vollständiger Buchungsprozess von der Geräteverwaltung bis zur automatischen Benachrichtigung technisch und logisch abbilden lässt.  
Es verbindet praxisorientierte Softwareentwicklung mit sauberer Architektur und einer klaren Benutzerführung.
