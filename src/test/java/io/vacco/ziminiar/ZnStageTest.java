package io.vacco.ziminiar;

import com.google.gson.Gson;
import io.vacco.ziminiar.document.ZnDocument;
import io.vacco.ziminiar.document.ZnShingle;
import io.vacco.ziminiar.superminhash.ZnBuffer;
import io.vacco.ziminiar.superminhash.ZnBuffers;
import io.vacco.ziminiar.superminhash.ZnPipeline;
import j8spec.annotation.DefinedOrder;
import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;

import static j8spec.J8Spec.*;

@DefinedOrder
@RunWith(J8SpecRunner.class)
public class ZnStageTest {
  static {
    it("Extract fingerprints from struct objects", () -> {
      var g = new Gson();
      var u = Objects.requireNonNull(ZnStageTest.class.getResource("/restaurant.json"));
      var restaurants = g.fromJson(new InputStreamReader(u.openStream()), ZnRestaurantList.class);

      var hashFn = (Function<ZnShingle, Long>) (in) -> (long) in.token.hashCode();
      var ppl = new ZnPipeline<ZnRestaurant>()
          .withStage(new ZnDocument(1, hashFn, null), r -> r.name)
          .withStage(new ZnDocument(3, hashFn, null), r -> r.addr)
          // .withStage(new ZnDocument(1, hashFn, null), r -> r.city)
          // .withStage(new ZnDocument(2, hashFn, null), r -> r.phone)
          ;

      var sigLength = 128;
      var fullIdx = new List[sigLength][1024];
      var buff = new ZnBuffer().init(sigLength);

      for (ZnRestaurant r : restaurants) {
        ppl.update(r, buff);
        for (int i = 0; i < sigLength; i++) {
          var l = fullIdx[i][buff.hi[i]];
          if (l == null) {
            l = new ArrayList();
            fullIdx[i][buff.hi[i]] = l;
          }
          l.add(r);
        }
      }

      var test = restaurants.get(64);
      buff = ppl.update(test, buff);

      var matches = new TreeMap<Integer, ZnRestaurant>();
      var matchBuff = new ZnBuffer().init(sigLength);
      for (int i = 0; i < sigLength; i++) {
        var l = fullIdx[i][buff.hi[i]];
        if (l != null) {
          for (Object o : l) {
            var r = (ZnRestaurant) o;
            matchBuff = ppl.update(r, matchBuff);
            var sim = ZnBuffers.similarity(buff, matchBuff);
            if (sim > 0.7 && !matches.containsKey(r.id)) {
              matches.put(r.id, r);
            }
          }
        }
      }

      System.out.println("lol");

    });
  }
}
