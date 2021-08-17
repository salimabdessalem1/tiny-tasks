package com.coyoapp.tinytask.constant;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public enum Status {
  FINISHED, UNFINISHED;

  public static Optional<Status> parse(String name) {
    return Stream.of(Status.values())
      .filter(s-> s.name().equals(name))
      .findFirst();
  }

  public static Set<String> getValues() {
    return Stream.of(Status.values()).map(Status::name).collect(toSet());
  }
}
