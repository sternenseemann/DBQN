package APL.types.functions.builtins.fns2;

import APL.types.Value;
import APL.types.functions.Builtin;

public class LaminateBuiltin extends Builtin {
  public String repr() {
    return "≍";
  }
  
  private static final int[] MSH = new int[]{2};
  public Value call(Value a, Value w) {
    return GTBuiltin.merge(new Value[]{a, w}, MSH, this);
  }
  
  public Value call(Value x) {
    int[] nsh = new int[x.rank+1];
    nsh[0] = 1;
    System.arraycopy(x.shape, 0, nsh, 1, x.shape.length);
    return x.ofShape(nsh);
  }
}
