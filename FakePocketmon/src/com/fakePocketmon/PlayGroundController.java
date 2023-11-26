package com.fakePocketmon;

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
            System.out.println("2. 자동 사냥한다");
            System.out.println("3. 현재상태를 본다");
            System.out.println("4. 잡은 몬스터 상태를 본다.");
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
                    //1. yes
                    if(parseDecision == 1)
                    {
                        //몬스터 선택 메뉴
                        selectMonster(me);
                    }
                    //2. no
                    else
                    {
                        break;
                    }
                }
//                Monster enemy = randomMonster(me.getMonstersAvgLevel());//몬스터를 랜덤으로 생성한다.
//                MonsterTrainer enemy = randomTrainer(me.getMonstersAvgLevel(), me.getMonsterBallCount());//트레이너를 랜덤으로 생성한다.
                Animal enemy = randomEnemy(me.getMonstersAvgLevel(), me.getMonsterBallCount());//적을 랜덤으로 생성한다.
                
                
                if(enemy instanceof Monster)
                {
                    Monster tmpTrainer = (Monster)enemy;
                    System.out.println("\n야생의 Lv." + tmpTrainer.getLevel() + " " + tmpTrainer.getName() + "이(가) 나왔다 뭐할까?");
                }
                if(enemy instanceof MonsterTrainer)
                {
                    MonsterTrainer tmpTrainer = (MonsterTrainer)enemy;
                    System.out.println("\n트레이너 Lv." + tmpTrainer.getLevel() + " " + tmpTrainer.getName() + " 씨가 도전해왔다 어떡할까?");
                }
                
                System.out.println("1. 싸운다");
                System.out.println("2. 도망간다");
                
                nextVal = sc.next();
                sc.nextLine();
                parseDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0))); 
                switch(parseDecision)
                {
                case 1 :
                    FightMode fightMode = new FightMode();
                    fightMode.startFightMain(me, enemy);
                    //FightMode.randomTrainer()
                    break;
                case 2 :
                    System.out.println(me.getName() + "는(은) 도망쳤다\n");
                    break;
                }
                break;
                
            //2. 자동 사냥한다.
            case 2 : 
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
                    //1. yes
                    if(parseDecision == 1)
                    {
                        //몬스터 선택 메뉴
                        selectMonster(me);
                    }
                    //2. no
                    else
                    {
                        break;
                    }
                }
                System.out.println("몇번의 자동 사냥을 진행하시겠습니까?");
                nextVal = sc.next();
                sc.nextLine();
                try
                {
                    parseDecision = Integer.parseInt(nextVal);
                    for(int i = 1; i < parseDecision + 1; i ++)
                    {
                        System.out.println("[" + i + " 번째 전투]");
                        Animal autoEnemy = randomEnemy(me.getMonstersAvgLevel(), me.getMonsterBallCount());//적을 랜덤으로 생성한다.
                        
                        if(autoEnemy instanceof Monster)
                        {
                            Monster tmpTrainer = (Monster)autoEnemy;
                            System.out.println("\n야생의 Lv." + tmpTrainer.getLevel() + " " + tmpTrainer.getName() + "이(가) 나왔다");
                        }
                        if(autoEnemy instanceof MonsterTrainer)
                        {
                            MonsterTrainer tmpTrainer = (MonsterTrainer)autoEnemy;
                            System.out.println("\n트레이너 Lv." + tmpTrainer.getLevel() + " " + tmpTrainer.getName() + " 씨가 도전해왔다");
                        }
                        FightMode fightMode = new FightMode();
                        fightMode.startAutoFightMain(me, autoEnemy);
                    }
                } catch (NumberFormatException e)
                {
                    System.out.println("잘못된 값을 입력하셨습니다. 메뉴로 돌아갑니다.\n");
                }
                break;
            //3. 현재상태를 본다
            case 3 : 
                System.out.println(me.printInfo());
                break;
            //4. 잡은 몬스터 상태를 본다.(누구를 볼까?)
            case 4 : 
                boolean isEvolution = false;
                //if 트레이너가 몬스터가 없다면 보여줄수 없다.
                if(me.getMonsterBallCount() == 0)
                {
                    System.out.println("보유한 포켓몬이 없습니다. 메뉴로 돌아갑니다.\n");
                    break;
                }
                System.out.println("\n잡은 몬스터 상태를 본다.(누구를 볼까?)");
                
                List<Monster> trainersMons = me.getMonsterBalls();
                
                int index = 1;
                
                //보유몬스터 목록을 만들어준다
                for(Monster trainersMon : trainersMons)
                {
                    System.out.println(index + ". Lv." + trainersMon.getLevel() + " " + trainersMon.getName() + (isEvolutionable(trainersMon)?" : 진화가능":"  : 진화불가"));
                    index++;
                }
                System.out.println((trainersMons.size()+1) + ". 메뉴로 돌아간다.");

                try
                {
                    nextVal = sc.nextLine();
                    parseDecision = Integer.parseInt(String.valueOf(nextVal)); 

                    //메뉴로 돌아간다.
                    if(parseDecision == trainersMons.size()+1)
                    {
                        break;
                    }
                    //목록에 없는 몬스터 선택시
                    if(parseDecision > trainersMons.size()+1 || parseDecision < 0)
                    {
                        System.out.println("잘못된 선택입니다.\n");
                        break;
                    }
                    
                    int monsIndex = parseDecision-1;
                    
                    Monster selctMon  = trainersMons.get(monsIndex);
                    String stringInfo = selctMon.printInfo();
                    
                    //진화가능한 상태인지 확인한다.
                    isEvolution = isEvolutionable(selctMon);
    
                    stringInfo += isEvolution?"진화가능상태  : 진화가능\n":"진화가능상태  : 진화불가\n";
                    
                    System.out.println(stringInfo);
                    System.out.println("1. 풀어준다");
                    System.out.println("2. 메뉴로 돌아간다");
                    System.out.println("3. 순서를 바꾼다.");
                    if(isEvolution) System.out.println("4. 진화시킨다");
                    nextVal = sc.nextLine();
                    parseDecision = Integer.parseInt(String.valueOf(nextVal)); 

                    if(parseDecision == 1)
                    {
                        System.out.println("잘살아야 되! " + trainersMons.get(monsIndex).getName() +"!\n");
                        trainersMons.remove(monsIndex);
                    }
                    //순서를 바꾼다.
                    if(parseDecision == 3)
                    {
                        if(me.getMonsterBallCount()<= 1)
                        {
                            System.out.println("\n1마리 이하일때는 순서를 바꿀수 없습니다.\n");
                            break;
                        }
                        System.out.println("\n누구와 바꿀까?");
                        
                        List<Monster> tmpMonters = me.getMonsterBalls();
                        
                        int tmpIndex = 1;
                        
                        //보유몬스터 목록을 만들어준다
                        for(Monster trainersMon : tmpMonters)
                        {
                            System.out.println(tmpIndex + ". Lv." + trainersMon.getLevel() + " " + trainersMon.getName());
                            tmpIndex++;
                        }

                        nextVal = sc.nextLine();
                        parseDecision = Integer.parseInt(String.valueOf(nextVal)); 
                        
                        Monster tmpMonster = trainersMons.get(monsIndex);
                        trainersMons.set(monsIndex, trainersMons.get(parseDecision-1));
                        trainersMons.set(parseDecision-1, tmpMonster);
                    }
                    if(parseDecision == 4 &&  isEvolution)
                    {
                        //EVOLUTION_NAME : 다음 진화할 포켓몬 명
                        String promptStr = selctMon.name + "(이)가 진화하여 ";
                        int monsterLevel = selctMon.getLevel();

                        Map<String, String> monsterInfo = ReadMonsterInfo.getMonAbility(selctMon.name);
                        promptStr += monsterInfo.get("EVOLUTION_NAME") + "(으)로 진화하였습니다!!\n\n";

                        selctMon.setHealthPointMax((int)((double)selctMon.getHealthPointMax()*1.5));//최대 HP 설정
                        selctMon.setAttackPoint((int)((double)selctMon.getAttackPoint()*1.5));       //공격력 설정
                        selctMon.setName(monsterInfo.get("EVOLUTION_NAME"));                         //몬스터 이름 설정
                        selctMon.setHealthPoint(selctMon.getHealthPointMax());                       //HP 풀피로 만들어준다.
                        selctMon.setCaughtLevel(monsterLevel);                                       //caughtLevel 설정
                        
                        System.out.println(promptStr);
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("잘못된 값을 입력하셨습니다.\n");
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

    /**
     * 입력된 몬스터가 진화가능한지 확인하는 메소드
     * @param selctMon
     * @return
     */
    private static boolean isEvolutionable(Monster selctMon)
    {
        boolean isEvolution;
        Map<String, String> monsterInfo = ReadMonsterInfo.getMonAbility(selctMon.name);
        
        //다음 진화형이 있는지 확인 - 없으면 false 반환
        if(monsterInfo.get("LIMIT_LEVEL")== null)
        {
            return false;
        }
        
        int limitLevel = Integer.parseInt(monsterInfo.get("LIMIT_LEVEL"));
        int diffLevel  = selctMon.getLevel() - selctMon.getCaughtLevel();
                
        //LIMIT_LEVEL : 동료가 된 이후로 설정된LIMIT_LEVEL을 넘으면 진화가 가능하다.
        //level - caughtLevel 이 LIMIT_LEVEL 이상일때
        if(limitLevel <= diffLevel)
        {
            isEvolution = true;
        }
        else
        {
            isEvolution = false;
        }
        return isEvolution;
    }

    /** 랜덤으로 몬스터를 생성하여 반환한다.
     * @return Monster
     */
    public static Monster randomMonster(double myLevel)
    {
        Monster ranMon = null;
        
        int randomLv   = (int)(Math.random() * 2);
        int plusMinus  = (int)(Math.random() * 10);
        plusMinus      = plusMinus>=7?-1:1;
        randomLv       = randomLv * plusMinus;
        int level      = (int)(myLevel - randomLv);
        level          = level < 0?0:level;
        int monSelInt  = (int)(Math.random() * 4);
        
        Object[] monsters = ReadMonsterInfo.getSameTypeMonList(ReadMonsterInfo.ELEMENTS[monSelInt]);
        Map<String, String> monsterAbility = ReadMonsterInfo.getMonAbility(monsters[(int)(Math.random() * (monsters.length))].toString());
        
        //ReadMonsterInfo.ELEMENTS 순서인 "불","물","풀","전기" 순으로 지정한다.
        switch(monSelInt)
        {
        case 0 :
            ranMon = new FlameMonster(); 
            break;
        case 1 :
            ranMon = new WaterMonster(); 
            break;
        case 2 :
            ranMon = new GrassMonster(); 
            break;
        case 3 :
            ranMon = new LightningMonster(); 
            break;
        }
        ranMon.setHealthPointMax(Integer.parseInt(monsterAbility.get("HEALTH_POINT_MAX")));//최대 HP 설정
        ranMon.setAttackPoint(Integer.parseInt(monsterAbility.get("ATTACK_POINT")));       //공격력 설정
        ranMon.setName(monsterAbility.get("MONSTER_NAME"));                                //몬스터 이름 설정
        ranMon.setHealthPoint(ranMon.getHealthPointMax());                                 //HP 풀피로 만들어준다.
        ranMon.setLevel(level);//생성된 몬스터의 레벨을 설정해준다.
        
        return ranMon;
    }

    /** 랜덤으로 트레이너를 생성하여 반환한다.
     * @return Monster
     */
    public static MonsterTrainer randomTrainer(double myLevel, int myMosterCount)
    {
        MonsterTrainer ranTrainer = null;
        
        String[] firstName   = {"김" , "이" , "박" , "최" , "정" , "강" , "조" , "윤" , "장" , "임" , "한" , "오" , "서" , "신" , "권" , "황" , "안" , "송" , "전" , "홍" , "유" , "고" , "문" , "양" , "손" , "배" , "조" , "백" , "허" , "유" , "남" , "심" , "노" , "정" , "하" , "곽" , "성" , "차" , "주" , "우" , "구" , "신" , "임" , "전" , "민" , "유" , "류" , "나" , "진" , "지" , "엄" , "채" , "원" , "천" , "방" , "공" , "강" , "현" , "함" , "변" , "염" , "양" , "변" , "여" , "추" , "노" , "도" , "소" , "신" , "석" , "선" , "설" , "마" , "길" , "주" , "연" , "방" , "위" , "표" , "명" , "기" , "반" , "라" , "왕" , "금" , "옥" , "육" , "인" , "맹" , "제" , "모" , "장" , "남궁" , "탁" , "국" , "여" , "진" , "어"};
        String[] krName      = {"길동", "개똥","국뽕","백만불", "안테나","것다","까따","하다", "성","나르샤", "현아", "동일" , "중기" , "홍철" , "은이" , "재석" , "준하" , "나라" , "이유" , "혜수" , "봉선" , "은혜" , "형돈" , "쯔위" , "동훈" , "명수" , "프콘" , "태현" , "태연" , "지현" , "효리" , "시완"};
        String[] animalName  = {"어흥","코끼리", "멍멍", "야옹", "짹짹", "까악", "원숭", "삐약" ,"꿀꿀" ,"히잉" ,"윙윙" ,"꺆꺆" ,"닭" ,"구구" ,"매미" ,"개굴" ,"말벌"};
        String[] lastName    = animalName.clone();
        String rtnName;
        
        rtnName  = firstName[(int)(Math.random()* firstName.length)];
        rtnName += krName[(int)(Math.random()* krName.length)];
        
        
        ranTrainer = new MonsterTrainer(rtnName); 
        
        int randomLv   = (int)(Math.random() * 2);
        int plusMinus  = (int)(Math.random() * 10);
        plusMinus      = plusMinus>=7?-1:1;
        randomLv       = randomLv * plusMinus;
        
        
        int level      = (int)(myLevel - randomLv);
        level          = level < 0?0:level;

        ranTrainer.setLevel(level);//생성된 몬스터의 레벨을 설정해준다.
        
        for(int i = 0 ; i < myMosterCount; i ++)
        {
            ranTrainer.addMonster(randomMonster(myLevel));
        }
        
        
        return ranTrainer;
    }

    /** 랜덤으로 적을 생성하여 반환한다.
     * @return Animal
     */
    public static Animal randomEnemy(double myLevel, int myMosterCount)
    {
        Animal rtnAnimal = null;
        MonsterTrainer ranTrainer = null;
        
        int ranInt = (int)(Math.random()*10); // 0 ~ 9
        
        //20% 확률로 트레이너 발생
        if(ranInt > 7)
        {
            rtnAnimal = randomTrainer(myLevel, myMosterCount);
        }
        else
        {
            rtnAnimal = randomMonster(myLevel);
        }
        return rtnAnimal;
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
            
            me.addMonster(pikachu); 
            break;
        case 2 :
            FlameMonster firey = new FlameMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("파이리");
            
            firey.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            firey.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.addMonster(firey); 
            break;
        case 3 :
            WaterMonster ggobugi = new WaterMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("꼬부기");
            
            ggobugi.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            ggobugi.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.addMonster(ggobugi); 
            break;
        case 4 :
            GrassMonster strageSeed = new GrassMonster();
            monsAbility = ReadMonsterInfo.getMonAbility("이상해씨");
            
            strageSeed.setHealthPointMax(Integer.parseInt(monsAbility.get("HEALTH_POINT_MAX")));
            strageSeed.setAttackPoint(Integer.parseInt(monsAbility.get("ATTACK_POINT")));
            
            me.addMonster(strageSeed);
            break;
        }
        selMonName = monsArr[monSelInt-1];
        System.out.println("\n" + selMonName + "! 넌 내꺼야~!");
        System.out.println("출발하자~\n");
    }
}
