package org.nathan.centralibj.algsJ.misc;

public class Manacher {
  /**
   * @param s string
   * @return max len palindrome string
   */
  public static String longestPalindrome(String s) {
    char[] inserted = new char[2 * s.length() + 1];
    int idx_inserted = 0, idx_str = 0;
    char placeHolder = '$';
    while (idx_str < s.length()) {
      inserted[idx_inserted++] = placeHolder;
      inserted[idx_inserted++] = s.charAt(idx_str++);
    }
    inserted[idx_inserted] = placeHolder;

    int[] ArmLen = new int[inserted.length];
    int pos = 0, arm_len = 1;
    int target_pos = 0, target_len = 1;
    while (pos < inserted.length) {
      while ((pos + arm_len) < inserted.length &&
              (pos - arm_len) >= 0 &&
              inserted[pos + arm_len] == inserted[pos - arm_len]) {
        arm_len++;
      }
      ArmLen[pos] = arm_len;
      if(arm_len > target_len){
        target_len = arm_len;
        target_pos = pos;
      }

      int mid_pos = pos, mid_arm_len = arm_len;
      pos++;
      arm_len = 1;
      while (pos <= mid_pos + mid_arm_len - 1){
        int sym_pos = mid_pos - (pos - mid_pos);
        int sym_pos_arm_len = ArmLen[sym_pos];

        int mid_left_bound = mid_pos - (mid_arm_len - 1);
        int sym_left_bound = sym_pos - (sym_pos_arm_len - 1);

        if(sym_left_bound > mid_left_bound){
          ArmLen[pos] = sym_pos_arm_len;
          pos++;
        }
        else if (sym_left_bound < mid_left_bound){
          ArmLen[pos] = sym_pos - mid_left_bound + 1;
          pos++;
        }
        else {
          arm_len = sym_pos_arm_len;
          break;
        }
      }
    }

    int left = (target_pos - (target_len - 1)), right = (target_pos + target_len - 1);
    StringBuilder ans = new StringBuilder();
    for(int i = left + 1; i <= right; i += 2){
      ans.append(inserted[i]);
    }

    return ans.toString();
  }
}
