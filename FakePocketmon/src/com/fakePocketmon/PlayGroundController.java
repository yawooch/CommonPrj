package com.fakePocketmon;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fakePocketmon.data.ReadMonsterInfo;

public class PlayGroundController
{

    public static void main(String[] args)
    {
        MonsterTrainer me = new MonsterTrainer();
        
        Scanner sc       = null;
        String nextVal   = null;
        boolean exitGame = true;
        
        while(exitGame)
        {
            sc = new Scanner(System.in);
            System.out.println("행동을 선택해 주세요");
            System.out.println("1. 여행한다");
            System.out.println("2. 현재상태를 본다");
            System.out.println("3. 잡은 몬스터 상태를 본다.");
            System.out.println("9. 여행을 종료한다");
            
            nextVal = sc.next();
            sc.nextLine();
            int parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
            
            switch(parseDecision)
            {
            case 1 : 
                //if 트레이너가 몬스터가 없다면 기본 포켓몬을 선택하도록 한다.
                if(me.getMonsterBallCount() == 0)
                {
                    //보유한 포켓몬이 없다. 홍박사에게 포켓몬을 받을까?
                    System.out.println();
                    System.out.println("포켓몬이 없다. 오박사에게 포켓몬을 받을까?");
                    System.out.println("1. yes");
                    System.out.println("2. no");

                    nextVal = sc.next();
                    sc.nextLine();
                    parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
                    switch(parseDecision)
                    {
                    //1. yes
                    case 1 :
                        //몬스터 선택 메
                        selectMonster(me);
                        break;
                    //2. no
                    case 2 : 
                        break;
                    }
                }
                
                Monster enemy = randomMonster(me);//몬스터를 랜덤으로 생성한다.
                System.out.println("\n야생의 Lv." + enemy.getLevel() + " " + enemy.getMonsterName() + "이(가) 나왔다 뭐할까?");
                System.out.println("1. 싸운다");
                System.out.println("2. 도망간다");

                nextVal = sc.next();
                sc.nextLine();
                parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
                switch(parseDecision)
                {
                case 1 :
                    FightMode fightMode = new FightMode();
                    fightMode.startFight(me, enemy);
                    //FightMode.randomTrainer()
                    break;
                case 2 :
                    System.out.println(me.getTtrainerName() + "는(은) 도망쳤다");
                    break;
                }
                break;
            //2. 현재상태를 본다
            case 2 : 
                System.out.println(me.printInfo());
                break;
            //3. 잡은 몬스터 상태를 본다.(누구를 볼까?)
            case 3 : 

                //if 트레이너가 몬스터가 없다면 보여줄수 없다.
                if(me.getMonsterBallCount() == 0)
                {
                    System.out.println("보유한 포켓몬이 없습니다. 메뉴로 돌아갑니다.");
                    break;
                }
                System.out.println("\n잡은 몬스터 상태를 본다.(누구를 볼까?)");
                
                List<Monster> trainersMons = me.getMonsterBalls();
                
                int index = 1;
                
                //보유몬스터 목록을 만들어준다
                for(Monster trainersMon : trainersMons)
                {
                    System.out.println(index + ". " + trainersMon.getMonsterName());
                    index++;
                }

                nextVal = sc.next();
                sc.nextLine();
                parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
                
                //목록에 없는 몬스터 선택시
                if(parseDecision > trainersMons.size() || parseDecision < 0)
                {
                    System.out.println("잘못된 선택입니다.");
                    break;
                }
                int monsIndex = parseDecision-1;
                
                System.out.println(trainersMons.get(monsIndex).printInfo());
                System.out.println("1. 풀어준다");
                System.out.println("2. 메뉴로 돌아간다");
                nextVal = sc.next();
                sc.nextLine();
                parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
                if(parseDecision == 1)
                {
                    System.out.println("잘살아야 되! " + trainersMons.get(monsIndex).getMonsterName() +"!\n");
                    trainersMons.remove(monsIndex);
                }
                
                break;
            //9. 여행을 종료한다
            case 9 :
                System.out.println("게임을 종료합니다.");
                exitGame = false;
                break;
            }
        }
    }

    /** 랜덤으로 몬스터를생성하여 반환한다.
     * @return Monster
     */
    public static Monster randomMonster(MonsterTrainer me)
    {
        Monster ranMon = null;
        
        int randomLv   = (int)(Math.random() * 2);
        int plusMinus  = (int)(Math.random() * 10);
        plusMinus      = plusMinus>=7?-1:1;
        randomLv       = randomLv * plusMinus;
        
        
        int level      = (int)(me.getMonstersAvgLevel() - randomLv);
        level          = level < 0?0:level;
        
        int monSelInt  = (int)(Math.random() * 4);
        
        Object[] monsters = ReadMonsterInfo.getSameTypeMonList(ReadMonsterInfo.ELEMENTS[monSelInt]);
        Map<String, String> monsterAbility = ReadMonsterInfo.getMonAbility(monsters[(int)(Math.random() * (monsters.length))].toString());
        
        switch(monSelInt)
        {
        case 0 :
            ranMon = new LightningMonster(); 
            break;
        case 1 :
            ranMon = new FlameMonster(); 
            break;
        case 2 :
            ranMon = new WaterMonster(); 
            break;
        case 3 :
            ranMon = new GrassMonster(); 
            break;
        }
        
        ranMon.setHealthPointMax(Integer.parseInt(monsterAbility.get("HEALTH_POINT_MAX")));
        ranMon.setAttackPoint(Integer.parseInt(monsterAbility.get("ATTACK_POINT")));
        ranMon.setMonsterName(monsterAbility.get("MONSTER_NAME"));
        
        ranMon.setLevel(level);//생성된 몬스터의 레벨을 설정해준다.
        
        return ranMon;
    }
    
    /** "피카츄","파이리","꼬부기","이상해씨" 를 String 배열로 만들어 선택하고 트레이터 몬스터 볼에 넣는다.
     * @param me
     */
    @SuppressWarnings("resource")
    public static void selectMonster(MonsterTrainer me)
    {
        String[] monsArr  = {"피카츄","파이리","꼬부기","이상해씨"};
        String selMonName = "";
        Map<String, String> monsAbility;
        Scanner sc        = new Scanner(System.in); 
        //배열에 있는 몬스터 선택지를 출력한다.
        for (int i = 1; i <= monsArr.length; i++)
        {
            System.out.println(i +". " + monsArr[i-1]);
        }

        String monSel = String.valueOf(sc.nextLine().charAt(0));
        int monSelInt = Integer.parseInt(monSel);
        switch(monSelInt)
        {
        case 1 :
            LightningMonster pikachu = new LightningMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("피카츄");
            
            pikachu.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            pikachu.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.catchMonster(pikachu); 
            break;
        case 2 :
            FlameMonster firey = new FlameMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("파이리");
            
            firey.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            firey.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.catchMonster(firey); 
            break;
        case 3 :
            WaterMonster ggobugi = new WaterMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("꼬부기");
            
            ggobugi.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            ggobugi.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.catchMonster(ggobugi); 
            break;
        case 4 :
            GrassMonster strageSeed = new GrassMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("이상해씨");
            
            strageSeed.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            strageSeed.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.catchMonster(strageSeed); 
            me.catchMonster(new GrassMonster()); 
            break;
        }
        selMonName = monsArr[monSelInt-1];
        System.out.println("\n" + selMonName + "! 넌 내꺼야~!");
        System.out.println("출발하자~\n");
    }
}
