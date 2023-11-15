package com.fakePocketmon.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * input_file/listFile.txt 에 탭으로 연결된 텍스트를 넣어주면 표문자를 사용하여 출력한다.
 */
public class ReadMonsterInfo
{
    public  static final String[] ELEMENTS = {"불","물","풀","전기"};
    private static String inputFilePathName  = "input_file/monstersInfo.txt";
    private static String outputFilePathName = "output_file/monstersInfo.txt";
    public static List<Map<String, String>> monsterInfo = readMonsterInfo();
    
    public ReadMonsterInfo()
    {
        monsterInfo = readMonsterInfo();
    }
    
    

    public static Map<String, String> getMonAbility(String monsterName)
    {
        Map<String, String> monAbility = new HashMap<String, String>();
        
        for (Map<String, String> map : monsterInfo)
        {
            if(map.get("MONSTER_NAME").equals(monsterName))
            {
                monAbility = map;
            }
        }
        
        return monAbility;
    } 
    
    public static Object[] getSameTypeMonList(String element)
    {
        List<String> monsList = new ArrayList<String>();
        
        for (Map<String, String> map : monsterInfo)
        {
            if(map.get("MONSTER_ELEMENT").equals(element))
            {
                monsList.add(map.get("MONSTER_NAME"));
            }
        }
        
        return (Object[])monsList.toArray();
    } 
    /**
     * @param inputFile
     */
    private static List<Map<String, String>> readMonsterInfo()
    {
        File inputFile = new File(inputFilePathName);
        
        //존재하지 않으면 새로 생성
        if(!inputFile.exists())
        {
            try
            {
                inputFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();
        
        try(BufferedReader br = new BufferedReader(new FileReader(inputFile)))
        {
            String readLine = "";
            readLine = br.readLine();
            String[] headArr = readLine.split("\t");
            while((readLine = br.readLine()) != null)
            {
                Map<String, String> map = new HashMap<String, String>();
                String[] mapValues = readLine.split("\t");
                for (int i = 0; i < mapValues.length; i++)
                {
                    map.put(headArr[i], mapValues[i]);
                }
                listMap.add(map);
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return listMap;
    }

    /**
     * @param listMap
     */
    private static void saveFile(String filePathName, List<Map<String, String>> listMap)
    {
        File outputFile = new File(filePathName);

        //존재하지 않으면 새로 생성
        if(!outputFile.exists())
        {
            try
            {
                outputFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile)))
        {
            bw.write(listMapToString(listMap));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * 리스트 맵을 스트링으로 바꿔준다. 열은 고정적이어야한다.
     * @param listMap
     * @param map
     * @param sb
     */
    private static String listMapToString(List<Map<String, String>> listMap)
    {
        StringBuilder sb = new StringBuilder();
        Object[] keyArr = listMap.get(0).keySet().toArray();
        for (int i = 0; i < keyArr.length; i++)
        {
//            System.out.println(map.get(keyArr[i]));
            sb.append(keyArr[i]);
            
            if(i != keyArr.length-1)
            {
                sb.append("\t");
            }
            else
            {
                sb.append("\n");
            }
        }
        for(Map<String, String> map1 : listMap)
        {
            for (int i = 0; i < keyArr.length; i++)
            {
                sb.append(map1.get(keyArr[i]));
                
                if(i != keyArr.length-1)
                {
                    sb.append("\t");
                }
                else
                {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * @return the inputFilePathName
     */
    public String getInputFilePathName()
    {
        return inputFilePathName;
    }

    /**
     * @param inputFilePathName the inputFilePathName to set
     */
    public void setInputFilePathName(String inputFilePathName)
    {
        this.inputFilePathName = inputFilePathName;
    }

    /**
     * @return the outputFilePathName
     */
    public String getOutputFilePathName()
    {
        return outputFilePathName;
    }

    /**
     * @param outputFilePathName the outputFilePathName to set
     */
    public void setOutputFilePathName(String outputFilePathName)
    {
        this.outputFilePathName = outputFilePathName;
    }
}
