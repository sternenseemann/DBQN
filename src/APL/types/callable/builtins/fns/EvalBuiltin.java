package APL.types.callable.builtins.fns;

import APL.*;
import APL.tools.FmtInfo;
import APL.types.Value;
import APL.types.callable.builtins.FnBuiltin;

public class EvalBuiltin extends FnBuiltin {
  public String ln(FmtInfo f) { return "⍎"; }
  
  public final Scope sc;
  public EvalBuiltin(Scope sc) {
    this.sc = sc;
  }
  
  public Value call(Value x) {
    return Main.exec(x.asString(), sc, null);
  }
  
  public Value call(Value w, Value x) {
    return Main.exec(x.asString(), sc, w.values());
  }
}