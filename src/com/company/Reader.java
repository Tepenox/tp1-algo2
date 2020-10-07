package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private String URL;

    public Reader(String URL) {
        this.URL = URL;
    }

    public String read(int line) throws IOException {
        BufferedReader lecteurAvecBuffer = null;
        String ligne;

        try
        {
            lecteurAvecBuffer = new BufferedReader(new FileReader(URL));
        }
        catch(FileNotFoundException exc)
        {
            System.out.println("Erreur d'ouverture du fichier à l'adresse " + URL);
            return null;
        }
        int i = 0;
        while ((ligne = lecteurAvecBuffer.readLine()) != null)
        {
            if(line == i)
                return ligne;
            else
                i++;
        }
        lecteurAvecBuffer.close();
        return null;
    }

    public int getSize() throws IOException {
        String line = read(1);
        line = line.substring(6);
        return Integer.parseInt(String.valueOf(line.charAt(0)));
    }

    public int getclose() throws IOException {
        String line = read(1);
        line = line.substring(6);
        return Integer.parseInt(String.valueOf(line.charAt(2)));
    }

    public List<List<Integer>> getList() throws IOException {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        BufferedReader lecteurAvecBuffer = null;
        String ligne;
        try {
            lecteurAvecBuffer = new BufferedReader(new FileReader(URL));
        } catch (FileNotFoundException exc) {
            System.out.println("Erreur d'ouverture du fichier à l'adresse " + URL);
            return null;
        }
        int currentLineIndex = 0;
        while ((ligne = lecteurAvecBuffer.readLine()) != null) {
            List<Integer> listOfLine = new ArrayList<>();
            if (currentLineIndex < 2) {
                currentLineIndex++;
                continue;
            }
            boolean isNegative = false;
            for (int i = 0; i < ligne.length(); i++) {
                if (ligne.charAt(i) != ' ') {
                    if (ligne.charAt(i) == '0')
                        break;
                    if (ligne.charAt(i) == '-') {
                        isNegative = true;
                        continue;
                    }
                    if (isNegative)
                        listOfLine.add(-Character.getNumericValue(ligne.charAt(i)));
                    else
                        listOfLine.add(Character.getNumericValue(ligne.charAt(i)));
                    isNegative = false;
                }
            }
            result.add(listOfLine);
        }
        lecteurAvecBuffer.close();
        return result;
    }
}
