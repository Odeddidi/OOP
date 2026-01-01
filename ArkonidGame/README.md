# ArkonidGame

A simple Arkanoid/Breakout-style game built in Java using the `biuoop` GUI library.

## Gameplay
- You control a paddle at the bottom of the screen.
- 3 balls bounce around the screen and break blocks when they hit them.
- You win when all blocks are removed (a bonus is added to the score).
- You lose when all balls fall below the paddle (hit the bottom frame).

## Controls
- Move paddle left: Left Arrow
- Move paddle right: Right Arrow

## Requirements
- Java (JDK) 17
- `biuoop-1.4.jar` (already included in this folder)

## Run
From the workspace root:

```sh
cd ArkonidGame

# compile
find src -name "*.java" > sources.txt
rm -rf out && mkdir -p out
javac -cp "biuoop-1.4.jar" -d out @sources.txt

# run
java -cp "$PWD/out:$PWD/biuoop-1.4.jar" GameMain.Main
```

If you’re on Windows, use `;` instead of `:` in the classpath:

```sh
java -cp "%CD%\\out;biuoop-1.4.jar" GameMain.Main
```

## Project entry point
- Main class: `GameMain.Main` (`src/GameMain/Main.java`)
