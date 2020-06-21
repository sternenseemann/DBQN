package APL.types.functions.builtins.fns;

import APL.*;
import APL.errors.*;
import APL.types.*;
import APL.types.functions.Builtin;

public class RShoeBuiltin extends Builtin {
  @Override public String repr() {
    return "⊃";
  }
  
  public RShoeBuiltin(Scope sc) {
    super(sc);
  }
  
  public Value call(Value w) {
    if (w instanceof Primitive) return w;
    else if (w.ia == 0) return w.prototype();
    else return w.first();
  }
  
  public Value call(Value a, Value w) {
    if (w instanceof APLMap) {
      APLMap map = (APLMap) w;
      return map.getRaw(a);
    }
    if (a instanceof Num) {
      if (w.rank != 1) throw new RankError("array rank was "+w.rank+", tried to get item at rank 0", this, w);
      if (w.ia == 0) throw new LengthError("⊃ on array with 0 elements", this, w);
      int p = a.asInt();
      if (p >= w.ia) throw new DomainError("Tried to access item at position "+a+" while shape was "+ Main.formatAPL(w.shape), this);
      return w.get(p);
    }
    for (Value v : a) {
      w = w.at(v.asIntVec());
    }
    return w;
  }
  
  public Value under(Obj o, Value w) {
    Value[] vs = w.valuesCopy();
    vs[0] = o instanceof Fun? ((Fun) o).call(call(w)) : (Value) o;
    return Arr.create(vs, w.shape);
  }
  
  public Value underW(Obj o, Value a, Value w) {
    Value v = o instanceof Fun? ((Fun) o).call(call(a, w)) : (Value) o;
    if (a instanceof Primitive) {
      Value[] vs = w.valuesCopy();
      vs[a.asInt()] = v;
      return Arr.create(vs, w.shape);
    } else {
      Value[] vs = w.valuesCopy();
      int[] is = a.asIntVec();
      replace(vs, v, is, 0);
      return Arr.create(vs, w.shape);
    }
  }
  private void replace(Value[] vs, Value w, int[] d, int i) {
    int c = d[i];
    if (i+1 == d.length) vs[c] = w;
    else {
      Value cv = vs[c];
      Value[] vsN = cv.valuesCopy();
      replace(vsN, w, d, i+1);
      vs[c] = Arr.create(vsN, cv.shape);
    }
  }
}