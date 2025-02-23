name: "CodeQL Java Security Analysis"

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]
  schedule:
    - cron: '0 12 * * 1'  # Runs every Monday at 12:00 UTC

jobs:
  analyze:
    name: Analyze Java Code
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
          languages: java  # Use 'java' for Java code analysis

      - name: Set up JDK 17
        run: |
          sudo apt update
          sudo apt install -y openjdk-17-jdk
          java -version  # Verify the JDK is installed

      - name: Build Java code (if needed)
        run: |
          sudo apt install -y maven  # Use Maven to build the Java project
          mvn clean install   # Build the project

      - name: Perform CodeQL Analysis
        uses: github/codeql-action/analyze@v3
        with:
          format: sarif  # Set the output format to SARIF
          output: codeql-results.sarif
