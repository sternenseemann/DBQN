package APL.types.functions.builtins.fns2;

import APL.errors.*;
import APL.types.Value;
import APL.types.arrs.DoubleArr;
import APL.types.functions.Builtin;

import java.util.*;

public class RBoxUBBuiltin extends Builtin {
  public String repr() {
    return "⊒";
  }
  
  public Value call(Value x) {
    if (x.rank==0) throw new DomainError("⊒: rank=0", this, x);
    if (x.rank!=1) throw new NYIError("⊒ on rank≠1", this, x);
    HashMap<Value, Integer> vs = new HashMap<>();
    double[] res = new double[x.ia];
    int i = 0;
    for (Value v : x) {
      Integer c = vs.get(v);
      if (c==null) {
        vs.put(v, 1);
      } else {
        res[i] = c;
        vs.put(v, c+1);
      }
      i++;
    }
    return new DoubleArr(res);
  }
  
  public Value call(Value w, Value x) {
    if (x.rank!=1 || w.rank!=1) throw new NYIError("⊒ on rank≠1", this, w);
    HashMap<Value, MutIA> vs = new HashMap<>();
    int i = 0;
    for (Value v : w) {
      MutIA c = vs.get(v);
      if (c==null) {
        c = new MutIA();
        vs.put(v, c);
      }
      c.add(i);
      i++;
    }
    double[] res = new double[x.ia];
    i=0;
    for (Value v : x) {
      MutIA c = vs.get(v);
      if (c==null || c.pos >= c.sz) res[i] = w.ia;
      else res[i] = c.is[c.pos++];
      i++;
    }
    return new DoubleArr(res);
  }
  
  private static class MutIA {
    int[] is = new int[2];
    int sz;
    int pos;
    void add(int i) {
      if (sz>=is.length) {
        is = Arrays.copyOf(is, is.length*2);
      }
      is[sz] = i;
      sz++;
    }
  }
}