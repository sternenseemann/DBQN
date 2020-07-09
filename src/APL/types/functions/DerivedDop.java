package APL.types.functions;

import APL.types.*;

public class DerivedDop extends Fun {
  public final Value aa, ww;
  public final Dop op;
  DerivedDop(Value aa, Value ww, Dop op) {
    this.aa = aa;
    this.ww = ww;
    this.op = op;
    token = op.token;
  }
  
  public Value call(Value x) {
    return op.call(aa, ww, x, this);
  }
  public Value call(Value w, Value x) {
    return op.call(aa, ww, w, x, this);
  }
  public Value callInv(Value x) {
    return op.callInv(aa, ww, x);
  }
  public Value callInvW(Value w, Value x) {
    return op.callInvW(aa, ww, w, x);
  }
  public Value callInvA(Value w, Value x) {
    return op.callInvA(aa, ww, w, x);
  }
  @Override public String repr() {
    String wws = ww.toString();
    if (!(ww instanceof Arr) && wws.length() != 1) wws = "("+wws+")";
    return aa.toString()+op.repr()+wws;
  }
  
  public Value under(Value o, Value w) {
    return op.under(aa, ww, o, w, this);
  }
  public Value underW(Value o, Value a, Value w) {
    return op.underW(aa, ww, o, a, w, this);
  }
  public Value underA(Value o, Value a, Value w) {
    return op.underA(aa, ww, o, a, w, this);
  }
}