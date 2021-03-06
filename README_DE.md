![Devoxx4Kids](http://www.devoxx4kids.de/wp-content/uploads/2015/07/cropped-header_hp.jpg)

[![Build Status](https://travis-ci.org/Devoxx4KidsDE/workshop-jumping-sumo.svg?branch=master)](https://travis-ci.org/Devoxx4KidsDE/workshop-jumping-sumo)

# Devoxx4Kids Workshop - Jumping Sumo

[Instructions in english](README.md)

Dieser Prototyp verbindet sich aktuell statisch zu einem JumpingSumo, d.h. 192.168.2.1 Port 44444.

Beinhaltet die folgenden 3 Modi:

```java -jar jumpingSumo-jar-with-dependencies.jar <keyboard|program|swing>```

## Tastatur (KeyboardDriver)
  - Pfeiltasten: Vorwärts, Rückwärts, Links, Rechts
  - Springen: 
      - (H) Hoch
      - (W) Weit
  - Animationen: 
      - (1) Drehen
      - (2) Tippen
      - (3) Schütteln
      - (4) Metronome
      - (5) Ondulation
      - (6) Drehsprung
      - (8) Spirale
      - (9) Slalom
  - Sound:
      - (Y) Layout
      - (X) Ohne Sound
      - (I) Monster Theme
      - (O) Insect Theme
      - (P) Robot Theme

## Programmatisch via Java (ProgrammaticDriver)

:construction: in Arbeit :construction:


## Natürliche Sprache (SwingBasedProgrammaticDriver)


| Befehl       | Jumpin Sumo führt aus:                              |
| ------------ |:--------------------------------------------------: |
| Vor          | Fährt eine Einheit nach vorne                       |
| Zurueck      | Fährt eine Einheit nach hinten                      |
| Links        | Linksdrehung 90°                                    |
| Links x      | Linksdrehung um x Grad (Hinweis: 90° entspricht 25) |
| Rechts x     | Rechtsdrehung um x Grad (Hinweis: 90° entspricht 25)|
| Springe hoch | Führt einen Hochsprung au                           |
| Springe weit | Führt einen Weitsprung aus                          |