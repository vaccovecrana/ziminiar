package io.vacco.ziminiar.transform;

import io.vacco.ziminiar.superminhash.*;
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

  public static List<ZShingle> apply(String in, int size) {
    return sliding(in, size)
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
        .entrySet().stream()
        .map(e -> ZShingle.from(e.getKey(), e.getValue()))
        .sorted().collect(Collectors.toList());
  }

  public static ZnBuffer fromDocument(String document,
                                      int shingleLength, int signatureLength,
                                      Function<ZShingle, Long> hashFn) {
    var shl = ZnShingles.apply(document, shingleLength);
    var sig = new ZnBuffer().init(signatureLength);
    for (int i = 0; i < shl.size(); i++) {
      ZnBuffers.update(hashFn.apply(shl.get(i)), sig.h, sig.b, sig.q, sig.p, sig.a, sig.m, i);
    }
    return sig.fill();
  }

}
