package com.memorychallenger.projects.memorychallenger.helpers;

import com.memorychallenger.projects.memorychallenger.models.Label;
import com.memorychallenger.projects.memorychallenger.models.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2/19/2017.
 */

public class IOHelper {
    public static String generateFileContent(HashMap<String, Object> objectMap) {
        String content = "";
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            content += entry.getValue().toString().replace("\n", "") + "\n";
        }
        return  content;
    }
}
