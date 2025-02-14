name: "CodeQL C/C++ Security Analysis"

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]
  schedule:
    - cron: '0 12 * * 1'  # Runs every Monday at 12:00 UTC

jobs:
  analyze:
    name: Analyze C/C++ Code
    runs-on: ubuntu-latest
    permissions:
      security-events: write
      actions: read
      contents: read

    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Initialize CodeQL
        uses: github/codeql-action/init@v3
        with:
          languages: java  # For Java-based security analysis

      - name: Build C/C++ code (Java project in this case)
        run: |
          sudo apt update && sudo apt install -y build-essential
          javac CWE90_LDAP_Injection__Environment_01.java

      - name: Perform CodeQL Analysis
        uses: github/codeql-action/analyze@v3
        with:
          format: json
          output: results.json
