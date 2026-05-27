# SkillSwap_1

Compilazione: javac -d out src/app/*.java src/domain/*.java src/resources/*.java src/service/*.java src/storage/*.java
Esecuzione: java -cp out app.Main

Compilazione test: javac -d out -cp out test/Tests.java
Esecuzione test: java -cp out Tests

Rimozione out: rm out/*class out/*/*class