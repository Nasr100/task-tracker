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
