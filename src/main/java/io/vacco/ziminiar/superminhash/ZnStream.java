package io.vacco.ziminiar.superminhash;

import io.vacco.ziminiar.document.ZShingle;
import io.vacco.ziminiar.document.ZnShingles;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ZnStream {

  // 1. Real-Time/Streaming Document Processing
  public static ZnBuffer streamHash(String partialText, int shingleLen, Function<ZShingle, Long> hashFn, ZnBuffer accumulator) {
    var shingles = ZnShingles.sliding(partialText, shingleLen).map(s -> ZShingle.from(s, 1L));
    shingles.forEach(sh -> ZnBuffers.update(hashFn.apply(sh), 0, accumulator));
    return accumulator.fill();
  }

  // 2. Customizable Hashing Pipelines
  public static ZnBuffer pipelineHash(String input, List<Function<String, String>> preprocessors, int shingleLen, int sigLen, Function<ZShingle, Long> hashFn) {
    String processed = input;
    for (var fn : preprocessors) processed = fn.apply(processed);
    return ZnShingles.fromDocument(processed, shingleLen, sigLen, hashFn);
  }

  // 3. Monitoring and Debugging (overloaded fromDocument with callback)
  public static ZnBuffer fromDocumentWithCallback(String doc, int shingleLen, int sigLen, Function<ZShingle, Long> hashFn, Consumer<ZnBuffer> onUpdate) {
    var buf = new ZnBuffer();
    buf.init(sigLen);
    var shingles = ZnShingles.apply(doc, shingleLen);
    for (int i = 0; i < shingles.size(); i++) {
      ZnBuffers.update(hashFn.apply(shingles.get(i)), i, buf);
      if (onUpdate != null) onUpdate.accept(buf);
    }
    return buf.fill();
  }

  // 4. Incremental Similarity for Dynamic Data
  public static ZnBuffer appendToBuffer(ZnBuffer existing, String newText, int shingleLen, Function<ZShingle, Long> hashFn) {
    var newShingles = ZnShingles.apply(newText, shingleLen);
    for (int i = 0; i < newShingles.size(); i++) {
      ZnBuffers.update(hashFn.apply(newShingles.get(i)), i, existing);
    }
    return existing.fill();
  }

}