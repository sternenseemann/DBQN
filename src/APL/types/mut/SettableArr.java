package APL.types.mut;

import APL.*;
import APL.errors.LengthError;
import APL.types.*;

public class SettableArr extends Settable {
  public final Settable[] arr;
  public final int ia;
  public SettableArr(Settable[] arr) {
    ia = arr.length;
    if (arr.length > 0) this.token = arr[0].token;
    this.arr = arr;
  }
  
  public Arr get(Scope sc) {
    if (this.token != null) Main.faulty = this;
    Value[] res = new Value[arr.length];
    for (int i = 0; i < ia; i++) res[i] = arr[i].get(sc);
    return Arr.create(res);
  }
  
  
  public String toString() {
    return "vararr";
  }
  
  
  public void set(Value x, boolean update, Scope sc, Callable blame) {
    if (x.rank != 1) throw new LengthError((update?'↩':'←')+": scatter rank ≠1", x);
    if (x.ia != ia) throw new LengthError((update?'↩':'←')+": scatter argument lengths not equal", x);
    
    for (int i = 0; i < ia; i++) arr[i].set(x.get(i), update, sc, null);
  }
  
  public boolean seth(Value x, Scope sc) {
    if (x.rank != 1) return false;
    if (x.ia != ia) return false;
  
    for (int i = 0; i < ia; i++) if (!arr[i].seth(x.get(i), sc)) return false;
    return true;
  }
}