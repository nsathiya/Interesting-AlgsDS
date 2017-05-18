/*
author: @nsathiya

Given a string, find the length of the longest substring without repeating characters.

Examples:

Given "abcabcbb", the answer is "abc", which the length is 3.
Given "bbbbb", the answer is "b", with the length of 1.
Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
*/

/*
My Explanation-
           abcabbab
           1 2 3 3 3 3
           0 1 2 3 4 5 6 7
       a   1     1
       b     1     1   1 1
       c       1     1

       O(n) solution
       create hashmap<letter, index they were last seen at>
       create array to keep track of count
       create variable to find max

       if char not in hashmap
           last count++;
           update max if needed;
       else
           get index of last char
           currentCont = currentIndex - lastIndex;
           if lastCount > curentCount
               this index = currentCount
           else
               this index = lastCount++;
           update max is needed;
*/

import java.util.*;

class Solution{
  public static int lengthOfLongestSubstring(String s) {

    //String is empty;
    if (s.length() == 0)
      return 0;

    HashMap<Character, Integer> mmp = new HashMap<Character, Integer>();
    mmp.put(s.charAt(0), 0);
    int[] counts = new int[s.length()];
    counts[0] = 1;
    int max = counts[0];

    for (int i=1; i < s.length(); i++){
      int countForIndex;
      if (!mmp.containsKey(s.charAt(i))){
        countForIndex = counts[i-1] + 1;
      } else {
        int currentCount = i - mmp.get(s.charAt(i));
        if (counts[i-1] >= currentCount)
          countForIndex = currentCount;
        else
          countForIndex = counts[i-1] + 1;
      }

      //update hashmap with new index;
      mmp.put(s.charAt(i), i);
      counts[i] = countForIndex;

      max = Math.max(max, countForIndex);

    }

    return max;

  }

  public static void main(String[] args){

    String input = "abcabcbb";
    int result = lengthOfLongestSubstring(input);

    System.out.println("Result is " + result);

  }

}
