LAKME-SRCMF-visu
===============

This GUI tool regroup three ways to graphically view and edit syntactic tagged texts: 
- Laucnh MaltEval in a graphical way and with any TSV format
- Inspect parsing and part of speech tagging errors for any TSV format
- Quick edit sentence and save the changes using Arborator with any TSV format (need an internet connexion up till now, will be fixed later)

A documentation is included in the program. you can access it by clicking on the book button.

# Build

This repo is built using maven, and developped using eclipse. To facilitate the build I left the eclipse project AND the maven files. In order to build it, please use:

```
mvn clean compile
mvn package
```

Or just import the project in Eclipse using the maven Eclipse plugin.

Of course, you can also simply use the JAR file.

# Usage

Double click on Jar or type :

```
java -Xmx[nb RAM]G -jar LAKME-SRCMF-visu-{version}.jar
```

# Contacts

gael dot guibon at gmail.com

@2016 SRCMF LaTTiCe-CNRS# LAKME-SRCMF-visu
