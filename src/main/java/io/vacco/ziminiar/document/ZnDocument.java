package io.vacco.ziminiar.document;

import io.vacco.ziminiar.superminhash.*;
import java.util.Objects;
import java.util.function.*;

public class ZnDocument implements ZnStage<String> {

  private final int shingleLength;
  private final Function<ZnShingle, Long> hashFn;
  private final Consumer<ZnBuffer> onBufferUpdate;

  public ZnDocument(int shingleLength,
                    Function<ZnShingle, Long> hashFn,
                    Consumer<ZnBuffer> onBufferUpdate ) {
    this.shingleLength = shingleLength;
    this.hashFn = Objects.requireNonNull(hashFn);
    this.onBufferUpdate = onBufferUpdate;
  }

  @Override public ZnBuffer update(String in, ZnBuffer b) {
    var shl = ZnShingles.apply(in, shingleLength);
    for (int i = 0; i < shl.size(); i++) {
      ZnBuffers.update(hashFn.apply(shl.get(i)), i, b);
      if (onBufferUpdate != null) {
        onBufferUpdate.accept(b);
      }
    }
    return b.fill();
  }

}
