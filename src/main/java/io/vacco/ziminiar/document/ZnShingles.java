package io.vacco.ziminiar.document;

import java.util.List;
import java.util.function.Function;
import java.util.stream.*;

public class ZnShingles {

  public static Stream<String> sliding(String in, int size) {
    if (size > in.length()) {
      return Stream.empty();
    }
    return IntStream.range(0, in.length() - size + 1)
        .mapToObj(start -> in.substring(start, start + size));
  }

  public static List<ZnShingle> apply(String in, int size) {
    return sliding(in, size)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .map(e -> ZnShingle.from(e.getKey(), e.getValue()))
        .sorted().collect(Collectors.toList());
  }

}
