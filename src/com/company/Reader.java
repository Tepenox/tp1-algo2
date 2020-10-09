package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    private String URL;

    /*
    * Ce Reader fonctionne pour des formules d'un taille <50 ((taille*2)<100)
    * De plus les littéraux des clauses ne doivent pas être supérieur à 99
    * Le nombre de clauses doit être lui aussi sur deux chiffres
    *
    *
    *
    * */


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
        String string = "";
        if(line.charAt(1)==' ')
            string = line.substring(0,1);
        else
            string = line.substring(0,2);
        return Integer.parseInt(string) * 2;
    }

    public int getclose() throws IOException {
        String line = read(1);
        line = line.substring(6);
        String string = "";
        if(line.charAt(1)==' ')
            string = line.substring(line.length()-1);
        else
            string = line.substring(line.length()-2);
        return Integer.parseInt(string);
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
            if (currentLineIndex < 2) {//jump 2 first line
                currentLineIndex++;
                continue;
            }
            boolean isNegative = false;
            String resultnumber = "";
            for (int i = 0; i < ligne.length(); i++) {
                if (ligne.charAt(i) != ' ') {
                    if (ligne.charAt(i) == '0' && ligne.charAt(i-1) == '0')
                        break;
                    if (ligne.charAt(i) == '-') {
                        isNegative = true;
                        continue;
                    }
                    resultnumber += ligne.charAt(i);
                }else
                {
                    listOfLine.add(isNegative?-Integer.parseInt(resultnumber):Integer.parseInt(resultnumber));
                    resultnumber ="";
                    isNegative = false;
                }
            }
            result.add(listOfLine);
        }
        lecteurAvecBuffer.close();
        return result;
    }
}
