# Task Tracker (Java CLI)

A command-line task manager written in Java, built with Maven.
Supports CRUD operations, filtering, and persistent storage in JSON.

## Features

- Add, edit, delete, and list tasks
- Mark tasks as done / not done
- Filter tasks (e.g. by status)
- Persistent storage — tasks are saved to and loaded from `Tasks.json`
- Clean OOP design with separated concerns (model, service, storage, CLI)

## Tech Stack

- **Java** (OOP, collections, streams)
- **Maven** (build & dependency management)
- **Jackson** *(or whatever JSON lib you used — adjust)* for JSON serialization
- File I/O for persistence

## Project Structure
src/
├── main/java/... # application code
└── ...
Tasks.json # persisted task data
pom.xml # Maven build file


## How to Run

```bash
# Clone the repo
git clone https://github.com/Nasr100/task-tracker.git
cd task-tracker

# Build
mvn clean package

# Run
java -jar target/<your-jar>.jar
````
What I Learned
Structuring a small Java application with clear separation of concerns

Using Maven for build and dependency management

Reading/writing JSON with a real library instead of manual file parsing

Designing a simple CLI interaction loop
