package com.fakePocketmon;

import java.util.ArrayList;
import java.util.Arrays;
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

        Monster myMonster = new LightningMonster();
        Map<String, String> myMonsterAbility = ReadMonsterInfo.getMonAbility("피카츄");
        myMonster.setHealthPointMax(Integer.parseInt(myMonsterAbility.get("HEALTH_POINT_MAX")));
        myMonster.setAttackPoint(Integer.parseInt(myMonsterAbility.get("ATTACK_POINT")));
        myMonster.setName(myMonsterAbility.get("MONSTER_NAME"));
        myMonster.setLevel(1);
//        List<Map<String, String>> monsters = ReadMonsterInfo.monsterInfo;
        
        System.out.println(Constants.ELEMENTS[0]);
        
        Monster monster = new WaterMonster();
        
        Object[] monsters = ReadMonsterInfo.getSameTypeMonList(ReadMonsterInfo.ELEMENTS[3]);
        Map<String, String> monsterAbility = ReadMonsterInfo.getMonAbility(monsters[(int)(Math.random() * (monsters.length))].toString());
        monster.setHealthPointMax(Integer.parseInt(monsterAbility.get("HEALTH_POINT_MAX")));
        monster.setAttackPoint(Integer.parseInt(monsterAbility.get("ATTACK_POINT")));
        monster.setName(monsterAbility.get("MONSTER_NAME"));
        monster.setLevel(2);
        
        
        
//        Object[] headArr = monsters.get(0).keySet().toArray();
        

        ArrayList<String> element = new ArrayList<>(Arrays.asList(ReadMonsterInfo.ELEMENTS));
        int[][] eachComfotable    = {{0,1,0,0},{-1,0,1,-1},{0,-1,0,1},{0,1,-1,0}};// 속성 상성을 표로 만들어 2차원배열을 넣었다.
        
        //속성공격
        int eleEffect = eachComfotable[element.indexOf("전기")][element.indexOf("전기")];
        if(eleEffect > 0) System.out.print("효과는 굉장했다\n");
        if(eleEffect < 0) System.out.print("효과는 미미했다\n");
        
        for(int i = 0; i < eachComfotable.length; i ++)
        {
            for(int j= 0 ; j < eachComfotable[i].length; j++)
            {
                System.out.print(eachComfotable[i][j] + "  ");
            }
            System.out.println();
        }
        
//        myMonster.attack(monster);
        System.out.println(element.indexOf("전기") + ", " + element.indexOf("전기"));
        System.out.println(eleEffect);
    }
}
