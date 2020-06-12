package APL.types.functions.userDefined;

import APL.*;
import APL.errors.DomainError;
import APL.tokenizer.types.DfnTok;
import APL.types.*;
import APL.types.functions.*;


public class Dmop extends Mop {
  public final DfnTok code;
  
  @Override public String repr() {
    return code.toRepr();
  }
  
  Dmop(DfnTok t, Scope sc) {
    super(sc);
    code = t;
  }
  
  public Value call(Obj f, Value w, DerivedMop derv) {
    Obj o = callObj(f, w, derv);
    if (o instanceof Value) return (Value) o;
    throw new DomainError("Was expected to give array, got "+o.humanType(true), this);
  }
  public Obj callObj(Obj aa, Value w, DerivedMop derv) {
    Main.printdbg("dmop call", w);
    Scope nsc = new Scope(sc);
    nsc.set("𝕗", aa);
    nsc.set("𝕨", new Variable(nsc, "𝕨"));
    nsc.set("𝕩", w);
    nsc.set("∇", derv);
    var res = Main.execLines(code, nsc);
    if (res instanceof VarArr) return ((VarArr)res).get();
    if (res instanceof Settable) return ((Settable)res).get();
    return res;
  }
  
  public Value call(Obj f, Value a, Value w, DerivedMop derv) {
    Obj o = callObj(f, a, w, derv);
    if (o instanceof Value) return (Value) o;
    throw new DomainError("Was expected to give array, got "+o.humanType(true), this);
  }
  public Obj callObj(Obj aa, Value a, Value w, DerivedMop derv) {
    Main.printdbg("dmop call", a, w);
    Scope nsc = new Scope(sc);
    nsc.set("𝕗", aa);
    nsc.set("𝕨", a);
    nsc.set("𝕩", w);
    nsc.set("∇", derv);
    nsc.alphaDefined = true;
    var res = Main.execLines(code, nsc);
    if (res instanceof VarArr) return ((VarArr)res).get();
    if (res instanceof Settable) return ((Settable)res).get();
    return res;
  }
}