package APL.types.functions.builtins.fns;

import APL.APL;
import APL.errors.DomainError;
import APL.types.*;
import APL.types.functions.Builtin;

public class CompareBuiltin extends Builtin {
  public CompareBuiltin () {
    super("⌺");
    valid = 0x010;
  }
  public Obj call(Value a, Value w) {
    return APL.compareObj(a, w);
  }
}