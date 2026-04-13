# 🤖 AI Content Assistant

A command-line Java application that uses AI to generate professional content, summarize text, and answer questions. Built with Groq API (Llama 3.3) - completely free, no credit card needed.

![Java](https://img.shields.io/badge/Java-17+-orange)
![License](https://img.shields.io/badge/license-MIT-blue)
![Status](https://img.shields.io/badge/status-active-success)

## ✨ Features

### Content Generation
- 📝 **Blog Posts** - Generate SEO-friendly blog content
- 📱 **Social Media Posts** - Create engaging posts for LinkedIn, Twitter, Instagram
- ✉️ **Emails** - Draft professional emails for any purpose
- 🛍️ **Product Descriptions** - Write compelling product copy

### AI-Powered Tools
- 📄 **Text Summarization** - Condense long articles (short/medium/detailed)
- 📂 **File Summarization** - Summarize uploaded .txt files
- ❓ **Q&A System** - Ask anything, get detailed AI explanations

### Smart Features
- ✅ Automatic retry logic (3 attempts)
- ✅ Input validation
- ✅ Organized file saving (timestamped)
- ✅ Generation history viewer
- ✅ Built-in tutorial for new users

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Internet connection
- Groq API key (free at console.groq.com)

### Installation

1. **Clone this repository**
```bash
git clone https://github.com/abdulrafay20069/AI-Content-Assistant.git
cd AI-Content-Assistant
```

2. **Get your free Groq API key**
   - Visit https://console.groq.com
   - Sign up (takes 2 minutes)
   - Go to API Keys section
   - Create new key

3. **Save your API key**
   - Create a file named `api-key.txt` in project root
   - Paste your API key (starts with `gsk_...`)
   - Save and close

4. **Compile the project**
```bash
javac -cp "lib/*" src/*.java
```

5. **Run the application**
```bash
# Windows:
java -cp "src;lib/*" Main

# Mac/Linux:
java -cp "src:lib/*" Main
```

## 📖 Usage Examples

### Generate a Blog Post