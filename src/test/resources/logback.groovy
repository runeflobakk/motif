appender("STDOUT", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%-6level %-30logger{10} %msg%n"
  }
}


root(INFO, ["STDOUT"])

 