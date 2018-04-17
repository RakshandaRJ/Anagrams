

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.HashSet;
import java.util.HashMap;
public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();

    //*****************************************************
    ArrayList<String> wordList;
    HashSet<String> wordSet;
    HashMap<String,ArrayList<String>> lettersToWord;
    int wordLength=DEFAULT_WORD_LENGTH;
    HashMap<Integer, ArrayList<String>> sizeToWords;
    //*****************************************************

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;

        wordList=new ArrayList<String>();
        wordSet=new HashSet<String>();
        lettersToWord=new HashMap<String,ArrayList<String>>();
        sizeToWords = new HashMap<>();


        while((line = in.readLine()) != null) {
            String word = line.trim();
   //****************************************
            wordList.add(word);
            wordSet.add(word);
            String retword = sortLetters(word);
           ArrayList<String> value;
            if(lettersToWord.containsKey(retword))
            {
                ArrayList<String> temp=lettersToWord.get(retword);
                if(!temp.contains(word))
                {
                    temp.add(word);
                }
                lettersToWord.put(retword,temp);

            }
            if(!lettersToWord.containsKey(retword))
            {
                value=new ArrayList<String>();
                value.add(word);
                lettersToWord.put(retword,value);
            }

            int len = retword.length();

            if(sizeToWords.containsKey(len)) {

                sizeToWords.get(len).add(word);
            } else {
                ArrayList<String> temp1 = new ArrayList<>();
                temp1.add(word);
                sizeToWords.put(len, temp1);
            }

   //****************************************
        }
    }
//*****************************************************
    public String sortLetters(String str)
    {
        char tempArray[] = str.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }
    //**************************************************

    public boolean isGoodWord(String word, String base) {

        if(wordSet.contains(word))
        {
            if(!word.contains(base))
            {
                return true;
            }
        }
        return false;
    }

//*********************************

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String retword = sortLetters(targetWord);

        for(String tmp:wordList)
        {
            String retwordlist=sortLetters(tmp);
            if(retword.length()==tmp.length()) {
                if (retword.equals(retwordlist)) {
                    result.add(tmp);

                }
            }
        }


        return result;

//*****************************************
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<>();
        String retnewstring=new String();
        String temp;
        ArrayList temparrlist;
        String geti=new String();
        for(char j='a';j<='z';j++)
        {
            temp=word.concat(""+j);
            retnewstring=sortLetters(temp);
            if(lettersToWord.containsKey(retnewstring))
            {
                temparrlist=lettersToWord.get(retnewstring);
                for(int i=0;i<temparrlist.size();i++)
                {
                    geti= (String) temparrlist.get(i);
                if(!(geti.contains(word)))
                {
                    result.add(geti);
                }
                }
            }
        }


        return result;
    }

    public String pickGoodStarterWord()
    {

        ArrayList<String> arrlist = sizeToWords.get(wordLength);

        if(wordLength < MAX_WORD_LENGTH) {
            wordLength++;
        }

        while (true) {
            String temp2 = arrlist.get(random.nextInt(arrlist.size()));
            if(getAnagramsWithOneMoreLetter(temp2).size() >= MIN_NUM_ANAGRAMS) {
                return temp2;
            }

        }

    }
}


