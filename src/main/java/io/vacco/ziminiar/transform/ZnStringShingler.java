package io.vacco.ziminiar.transform;

import java.util.List;
import java.util.function.Function;
import java.util.stream.*;

public class ZnStringShingler {

  public static Stream<String> sliding(String in, int size) {
    var in0 = in.toLowerCase().replaceAll("[^\\w\\u4e00-\\u9fcc]+", "");
    if (size > in0.length()) {
      return Stream.empty();
    }
    return IntStream.range(0, in0.length() - size + 1)
        .mapToObj(start -> in0.substring(start, start + size));
  }

  public static List<ZShingle> apply(String in, int size) {
    return sliding(in, size)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .map(e -> ZShingle.from(e.getKey(), e.getValue()))
        .sorted().collect(Collectors.toList());
  }

}
