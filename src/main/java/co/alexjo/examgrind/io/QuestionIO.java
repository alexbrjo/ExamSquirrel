/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.alexjo.examgrind.io;

import co.alexjo.examgrind.exam.Question;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Reads a Question from a json file.
 * @author Alex Johnson
 */
public class QuestionIO {
    
    
    public static ArrayList<Question> readQuestionsFile(File file) {
        
        FileReader in;
        
        try {
            in = new FileReader(file);
        } catch (IOException e) {
            return null;
        }
        
        Gson gson = new Gson();
        Question[] qs = gson.fromJson(in, Question[].class);
        
        ArrayList<Question> questions = new ArrayList<>();
        questions.addAll(Arrays.asList(qs));
        
        return questions;
    }
}
