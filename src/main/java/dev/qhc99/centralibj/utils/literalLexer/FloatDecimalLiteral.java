package dev.qhc99.centralibj.utils.literalLexer;

import org.jetbrains.annotations.NotNull;

class FloatDecimalLiteral extends LiteralLexChecker {

  FloatDecimalLiteral(@NotNull String source) {
    super(source);
  }

  public boolean isTheLiteral() {
    if (s.length() == 0) {
      return false;
    }
    if (isFloatConstants()) {
      return true;
    }
    var first = s.charAt(idx);
    if (first == '+' || first == '-') {
      if (end()) {
        return false;
      }
    }
    var c = s.charAt(idx);
    if (c == '.') {
      if (end()) {
        return false;
      }
      else if (!isDigits()) {
        return false;
      }
      if (isEnd()) {
        return true;
      }
      else {
        var c1 = s.charAt(idx);
        return ExponentPartOrFloatTypeSuffixOrBoth(c1);
      }
    }
    else {
      if (!isDigits()) {
        return false;
      }
      if (isEnd()) {
        return false;
      }
      var c3 = s.charAt(idx);
      switch (c3) {
        case '.' -> {
          if (end()) {
            return true;
          }
          switch (s.charAt(idx)) {
            case 'e', 'E' -> {
              if (end()) {
                return false;
              }
              if (!isSignedInteger()) {
                return false;
              }
              if (isEnd()) {
                return true;
              }
              else {
                return isFloatTypeSuffix();
              }
            }
            case 'f', 'F', 'd', 'D' -> {
              return idx + 1 == s.length();
            }
            default -> {
              if (!isDigits()) {
                return false;
              }
              if (isEnd()) {
                return true;
              }
              var c2 = s.charAt(idx);
              return ExponentPartOrFloatTypeSuffixOrBoth(c2);
            }
          }

        }
        case 'e', 'E' -> {
          if (end()) {
            return false;
          }
          if (!isSignedInteger()) {
            return false;
          }
          if (isEnd()) {
            return true;
          }
          else {
            return isFloatTypeSuffix();
          }
        }
        case 'f', 'F', 'd', 'D' -> {
          return idx + 1 == s.length();
        }
        default -> {
          return false;
        }
      }
    }
  }

  protected boolean ExponentPartOrFloatTypeSuffixOrBoth(char c1) {
    switch (c1) {
      case 'e', 'E' -> {
        if (end()) {
          return false;
        }
        if (!isSignedInteger()) {
          return false;
        }
        if (isEnd()) {
          return true;
        }
        else {
          return isFloatTypeSuffix();
        }
      }
      case 'f', 'F', 'd', 'D' -> {
        return idx + 1 == s.length();
      }
      default -> {
        return false;
      }
    }
  }
}
