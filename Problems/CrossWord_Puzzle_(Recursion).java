/*
  author: @nsathiya

  Hackerrank problem that was interesting, solvable with good understanding of recursion-
  https://www.hackerrank.com/challenges/crossword-puzzle
  This solution passes all test cases.

  A  Crossword grid is provided to you, along with a set of words (or names of places) which
  need to be filled into the grid.
  The cells in the grid are initially, either + signs or - signs.
  Cells marked with a + have to be left as they are. Cells marked with a - need to be filled
  up with an appropriate character.

  Sample Input A
  +-++++++++
  +-++++++++
  +-++++++++
  +-----++++
  +-+++-++++
  +-+++-++++
  +++++-++++
  ++------++
  +++++-++++
  +++++-++++
  LONDON;DELHI;ICELAND;ANKARA

  Sample Output A
  +L++++++++
  +O++++++++
  +N++++++++
  +DELHI++++
  +O+++C++++
  +N+++E++++
  +++++L++++
  ++ANKARA++
  +++++N++++
  +++++D++++

  Sample Input B
  +-++++++++
  +-++++++++
  +-------++
  +-++++++++
  +-++++++++
  +------+++
  +-+++-++++
  +++++-++++
  +++++-++++
  ++++++++++
  AGRA;NORWAY;ENGLAND;GWALIOR

  Sample Output B
  +E++++++++
  +N++++++++
  +GWALIOR++
  +L++++++++
  +A++++++++
  +NORWAY+++
  +D+++G++++
  +++++R++++
  +++++A++++
  ++++++++++

*/


import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class Solution {

    //Helper function to print map
    public static void printMap(Character[][] map){
        if (map == null) return;

        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[0].length; j++){
               System.out.print(map[i][j]);
            }
            System.out.println("");
        }
        return;
    }

    //Check if move is valid
    public static Boolean isValid(int row, int col, Character[][] map, int idx, String str){
        //if move is in-bounds and entry contans '-' or word : return true
        //else return false
        if (row >= 0 && row < map.length &&
            col >= 0 && col < map.length &&
            (map[row][col] == '-' ||
             map[row][col] == str.charAt(idx)))
            return true;
        else
            return false;
    }

    //return new instance of map to pass down to children moves
    public static Character[][] deepCopyMatrix(Character[][] input) {
        if (input == null)
            return null;
        Character[][] result = new Character[input.length][];
        for (int r = 0; r < input.length; r++) {
            result[r] = input[r].clone();
        }
        return result;
    }

    //With the current map, word and position, see if this word can be placed without breaking rules, vertically and horizontally
    public static Boolean findPlacement(int y, int x, Character[][] map, int idx, String str, int direction){

        if (idx == str.length()){
            return true;
        } else if (isValid(y, x, map, idx, str)){
            map[y][x] = str.charAt(idx);

            if (direction == 0) return findPlacement(y+1, x, map, idx+1, str, direction);
            else return findPlacement(y, x+1, map, idx+1, str, direction);

        } else {
            return false;
        }
    }

    //go through all possible positions and find if current word can fit. return list of all possible arrangements.
    public static ArrayList<Character[][]> findPossibleMaps(Character[][] map, String word){

        ArrayList<Character[][]> results = new ArrayList<>();

        for (int i=0; i<map.length; i++){
            for (int j=0; j<map[i].length; j++){
                if (map[i][j] == '-' || map[i][j] == word.charAt(0) ){
                   Character [][] newMapHoriz = deepCopyMatrix(map);
                   Character [][] newMapVert = deepCopyMatrix(map);
                   Boolean foundHoriz = findPlacement(i, j, newMapHoriz, 0, word, 1);
                   Boolean foundVert = findPlacement(i, j, newMapVert, 0, word, 0);

                   if (foundHoriz)
                       results.add(newMapHoriz);
                   if (foundVert)
                       results.add(newMapVert);
                }
            }
        }

        return results;
    }

    //for current word, get all possible maps, then recurse to the next word. if all words are reached, return map.
    public static Character[][] putNextWord(Character[][] map, int wordIdx, String[] wordDict){
        if (wordIdx == wordDict.length)
            return map;
        else{

            ArrayList<Character[][]> maps = findPossibleMaps(map, wordDict[wordIdx]);

            if (maps.size() == 0)
                return null;
            else {
                for (int i=0; i<maps.size(); i++){
                    Character[][] mapReturned = putNextWord(maps.get(i), wordIdx+1, wordDict);
                    if (mapReturned != null)
                        return mapReturned;
                }
                return null;
            }

        }
    }

    public static void solveProblem(String input){

      InputStream stdin = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      Scanner in = new Scanner(System.in);
      Character[][] initialMap = new Character[10][10];

      //Read in initial map
      for (int i=0; i<initialMap.length; i++){
          String line = in.nextLine();
          for (int j=0; j<initialMap[0].length; j++){
              initialMap[i][j] = line.charAt(j);
          }
      }
      //Read in dict of words
      String[] mapDict = in.nextLine().split(";");
      //Start with the first word
      Character[][] mapResult = putNextWord(initialMap, 0, mapDict);
      //Print end map
      printMap(mapResult);

      return;

    }

    public static void main(String[] args) {

        String testCase1 =  "+-++++++++\n" +
                            "+-++++++++\n" +
                            "+-++++++++\n" +
                            "+-----++++\n" +
                            "+-+++-++++\n" +
                            "+-+++-++++\n" +
                            "+++++-++++\n" +
                            "++------++\n" +
                            "+++++-++++\n" +
                            "+++++-++++\n" +
                            "LONDON;DELHI;ICELAND;ANKARA";

        String testCase2 =  "+-++++++++\n" +
                            "+-++++++++\n" +
                            "+-------++\n" +
                            "+-++++++++\n" +
                            "+-++++++++\n" +
                            "+------+++\n" +
                            "+-+++-++++\n" +
                            "+++++-++++\n" +
                            "+++++-++++\n" +
                            "++++++++++\n" +
                            "AGRA;NORWAY;ENGLAND;GWALIOR";

        solveProblem(testCase1);
        solveProblem(testCase2);
        return;
    }
}
