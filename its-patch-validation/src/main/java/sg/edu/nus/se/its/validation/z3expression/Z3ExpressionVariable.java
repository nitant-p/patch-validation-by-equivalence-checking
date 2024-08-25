package sg.edu.nus.se.its.validation.z3expression;

import com.microsoft.z3.*;
import sg.edu.nus.se.its.validation.translator.TranslationContext;

public interface Z3ExpressionVariable {

  Expr interpret(TranslationContext translationContext, Context ctx);
  String toString();
  public String getName();
  
}
