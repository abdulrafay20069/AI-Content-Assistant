# AI Cover Letter Generator

A command-line Java app that generates tailored cover letters using 
Groq's API with Llama 3.3 — completely free, no credit card needed.

## Demo

![Cover letter generation](demo/demo1.png)
![Generated output](demo/demo2.png)


## What it does
- You paste a job description
- You describe your background  
- You pick a tone (professional / enthusiastic / concise)
- AI generates a matching cover letter and saves it to a file

## Tech stack
Java | OkHttp | Gson | Groq API | Llama 3.3 70B

## How to run
1. Clone this repo
2. Download OkHttp and Gson jars into a `lib/` folder
3. Get a free API key from console.groq.com
4. Save it in `api-key.txt` in the project root
5. Compile: `javac -cp "lib/*" src/Main.java -d src`
6. Run: `java -cp "src;lib/*" Main`

## Why I built this
First project in my AI portfolio. Demonstrates REST API integration,
JSON parsing, and prompt engineering in Java.