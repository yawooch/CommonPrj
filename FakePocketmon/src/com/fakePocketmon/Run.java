package com.fakePocketmon;

import java.util.List;
import java.util.Map;

import com.fakePocketmon.data.Constants;
import com.fakePocketmon.data.ReadMonsterInfo;

public class Run
{
    public static void main(String[] args)
    {
//        PlayGroundController.main(args);
//        ReadMonsterInfo rmi = new ReadMonsterInfo();
        
        List<Map<String, String>> monsters = ReadMonsterInfo.monsterInfo;
        
        System.out.println(Constants.ELEMENTS[0]);
        
        Object[] headArr = monsters.get(0).keySet().toArray();
        
        
        
    }
}
