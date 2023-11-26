package com.fakePocketmon;

import java.util.List;
import java.util.Map;

import com.fakePocketmon.data.ReadMonsterInfo;

public class Run
{
    public static void main(String[] args)
    {
        PlayGroundController pgc = new PlayGroundController();
//        FightMode fm = new FightMode();
        
        /**************************************************************/
//        //랜덤 몬스터 테스트
//        Monster ranMon = pgc.randomMonster(1);
//        
//        System.out.println(ranMon.printInfo());
        /**************************************************************/
        
        /**************************************************************/
        //트레이너 VS 몬스터 테스트
//        MonsterTrainer me = new MonsterTrainer();
//        Monster myMonster = new Monster();
//        
//        Map<String, String> monsterAbility = ReadMonsterInfo.getMonAbility("피카츄");
//
//        myMonster.setHealthPointMax(Integer.parseInt(monsterAbility.get("HEALTH_POINT_MAX")));
//        myMonster.setAttackPoint(Integer.parseInt(monsterAbility.get("ATTACK_POINT")));
//        myMonster.setElementAttr(monsterAbility.get("MONSTER_ELEMENT"));
//        myMonster.setName(monsterAbility.get("MONSTER_NAME"));
//        
//        me.addMonster(myMonster);
//        
//        Monster enemyMonster = new Monster();
//        
//        Map<String, String> enemyMonsterAbility = ReadMonsterInfo.getMonAbility("이상해꽃");
//
//        enemyMonster.setHealthPointMax(Integer.parseInt(enemyMonsterAbility.get("HEALTH_POINT_MAX")));
//        enemyMonster.setAttackPoint(Integer.parseInt(enemyMonsterAbility.get("ATTACK_POINT")));
//        enemyMonster.setElementAttr(enemyMonsterAbility.get("MONSTER_ELEMENT"));
//        enemyMonster.setName(enemyMonsterAbility.get("MONSTER_NAME"));
//        enemyMonster.setHealthPoint(enemyMonster.getHealthPointMax());
//        
//        fm.startFightMain(me, enemyMonster);
        /**************************************************************/
        
        /**************************************************************/
        //상성표 테스트
//        ArrayList<String> element = new ArrayList<>(Arrays.asList(Constants.ELEMENTS));
//        int[][] eachComfotable    = {{0,1,0,0},{-1,0,1,-1},{0,-1,0,1},{0,1,-1,0}};// 속성 상성을 표로 만들어 2차원배열을 넣었다.
//        String myElement = ReadMonsterInfo.ELEMENTS[3];//불 물 풀 전기
//        String enemyElement = ReadMonsterInfo.ELEMENTS[2];//불 물 풀 전기
//        
//        System.out.println(myElement + " VS " + enemyElement);
//        
//        //속성공격
//        int eleEffect = eachComfotable[element.indexOf(enemyElement)][element.indexOf(myElement)];
//        if(eleEffect > 0) System.out.println("효과는 굉장했다\n");
//        if(eleEffect < 0) System.out.println("효과는 미미했다\n");
//        System.out.println(eleEffect);
        
        /**************************************************************/
        
        /**************************************************************/
        //경험치 레벨업 테스트
//        MonsterTrainer me = new MonsterTrainer();
//        Monster myMonster = new Monster();
//        
//        Map<String, String> monsterAbility = ReadMonsterInfo.getMonAbility("어니부기");
//
//        myMonster.setHealthPointMax(Integer.parseInt(monsterAbility.get("HEALTH_POINT_MAX")));
//        myMonster.setAttackPoint(Integer.parseInt(monsterAbility.get("ATTACK_POINT")));
//        myMonster.setElementAttr(monsterAbility.get("MONSTER_ELEMENT"));
//        myMonster.setName(monsterAbility.get("MONSTER_NAME"));
//        
//        me.addMonster(myMonster);
//        
//        List<Monster>  myMonsters = me.getMonsterBalls();
//        double myLevel = 1.0;
//        
//        for(int i = 0; i < 1000; i++)
//        {
////            if(i%10 == 0) myLevel ++;
//            Monster curMonster   = myMonsters.get(0);
//            Monster enemyMonster = pgc.randomMonster(myLevel);
////            System.out.println("enemyMonster Level : " + enemyMonster.getLevel());
//            System.out.println(curMonster.printInfo());
//            curMonster.setExpCur(enemyMonster);
//        }
        
        /**************************************************************/
        
        
        /**************************************************************/
        //경험치 테스트
        int level         = 10;
        int origExcp      = 0;
        int expMax        = 100;
                
        for(int i = 0; i < 300; i ++)
        {
            int levelDiff     = 3 - level;
            int expGrowthRate = 20;//경험치 증가율(%) & 레벨업시, Max경험치 증가율(%)
            int expCur = (int)((10*(levelDiff<=0?1:levelDiff)) * ((double)(100 + expGrowthRate*levelDiff)/100.0));
            expCur = (expCur <= 0?0: expCur);
            origExcp = origExcp + expCur;// 현재 경험치에 획득 경험치를 더해준다.
            int overExp = origExcp - expMax; 
            
            System.out.println((i+1) +". 경험치 " + expCur + "를 획득했다!  " + origExcp + "/" + expMax);
            
            //TODO : 상극 속성을 이기면 보너스...?
            //Max 경험치를 초과하면 Level Up!
            if(overExp >= 0)
            {
                int tmpLevel   = origExcp / expMax; 
                expMax = expMax * (100 + expGrowthRate)/100;//expGrowthRate만큼 expMax 증가
                origExcp = overExp;
                
                System.out.println((i+1) +". 레벨이 " + tmpLevel + " 상승했습니다!");
                level++;
                System.out.println("Lv. " + level);
                
                System.out.println("**************************************************************");
            }
        }
        
        /**************************************************************/

        
    }
}
