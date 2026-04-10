# AI Content Assistant

A command-line Java application that uses AI to generate content, summarize text, and answer questions. Built with Groq API (Llama 3.3) - completely free, no credit card needed.

## Features (Building in Progress)

- ✅ **Day 1:** Basic API integration working
- ✅ **Day 2:** Content generation (blog posts, social media, emails, products)
- 🚧 **Day 3:** Text summarization
- 🚧 **Day 4:** Q&A system


## Tech Stack

- Java
- OkHttp (HTTP client)
- Gson (JSON parsing)
- Groq API (Llama 3.3 70B)

## How to Run

1. Clone this repo
2. Download required JARs into `lib/` folder:
   - okhttp-4.12.0.jar
   - okio-jvm-3.6.0.jar
   - gson-2.10.1.jar
   - kotlin-stdlib-1.9.22.jar
3. Get free API key from console.groq.com
4. Save key in `api-key.txt` at project root
5. Compile: `javac -cp "lib/*" src/Main.java`
6. Run: `java -cp "src;lib/*" Main`

## Why This Project

First project in my AI application development portfolio. Demonstrates:
- REST API integration
- JSON parsing and handling
- Prompt engineering
- Building practical AI tools in Java

## Next Steps

Week 1: Complete CLI version with all features
Week 2: Build web version with Spring Boot + React
Week 3: Deploy and add automation features