package com.fakePocketmon;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fakePocketmon.data.ReadMonsterInfo;

public class FightMode
{
    private boolean turningCoin = true; //턴제 코인 true : 내차례, false : 니차례
    
    public static void main(String[] args)
    {
        FightMode testClass = new FightMode();
        
        MonsterTrainer me = new MonsterTrainer();
        Monster myPocketmon = new LightningMonster();
        
        Map<String, String> myMonsterAbility = ReadMonsterInfo.getMonAbility("보송송");
        
        myPocketmon.setHealthPointMax(Integer.parseInt(myMonsterAbility.get("HEALTH_POINT_MAX")));
        myPocketmon.setAttackPoint(Integer.parseInt(myMonsterAbility.get("ATTACK_POINT")));
        myPocketmon.setName(myMonsterAbility.get("MONSTER_NAME"));
        
        me.addMonster(myPocketmon);

        Monster enemyPocketmon = new LightningMonster();
        
        Map<String, String> enemyMonsterAbility = ReadMonsterInfo.getMonAbility("피카츄");
        
        enemyPocketmon.setHealthPointMax(Integer.parseInt(enemyMonsterAbility.get("HEALTH_POINT_MAX")));
        enemyPocketmon.setAttackPoint(Integer.parseInt(enemyMonsterAbility.get("ATTACK_POINT")));
        enemyPocketmon.setName(enemyMonsterAbility.get("MONSTER_NAME"));
        
        testClass.startFight(me, enemyPocketmon);
//        info.getSameTypeMonList(ReadMonsterInfo.ELEMENTS[0]);
        
        
    }
    
    /**
     * 싸움실행 메인
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     * @param <T>
     */
    public void startFightMain(MonsterTrainer me, Animal  enemy)
    {
        System.out.print("싸움실행 메인 ");
        
        if(enemy instanceof Monster)
        {
            startFight(me,(Monster)enemy);
        }
        if(enemy instanceof MonsterTrainer)
        {
            startFight(me,(MonsterTrainer)enemy);
        }
    }
    
    /**
     * 트레이너 VS 몬스터
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    @SuppressWarnings("resource")
    public void startFight(MonsterTrainer me, Monster enemy)
    {
        System.out.println("트레이너 VS 몬스터");
        me.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.
        
        List<Monster> myMonsters = me.getMonsterBalls();
        Monster currMonster      = null; 

        Scanner sc1 = null;
        while(true)
        {   
            sc1 = new Scanner(System.in);
            //둘중에 하나라도 전투불가 상태가 있으면 전투를 종료한다.
            if(whoIsWinner(me, enemy) != null)
            {
                String winnersName = "";
                if(whoIsWinner(me, enemy) instanceof MonsterTrainer)
                {
                    MonsterTrainer winner = (MonsterTrainer)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                    winner.setExpCur(enemy);
                }
                if(whoIsWinner(me, enemy) instanceof Monster)
                {
                    Monster winner = (Monster)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                }
                //전적계산 : 내가 이겼을떄
                if(winnersName.equals(me.getName())) me.setWinCount(me.getWinCount()+1);
                //전적계산 : 내가 졌을떄
                else me.setLoseCount(me.getLoseCount()+1);
                System.out.println("\n" + winnersName + "가 승리하였다!!");
                System.out.println("전투를 종료합니다\n\n");
                break;
            }

            //me 트레이너 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster myMonster : myMonsters)
            {
                if(myMonster.isBattleStatus())
                {
                    currMonster = myMonster;
                }
            }

            
            System.out.println("\n*********************************************************");
            //내차례냐 니차례냐 true : 내 차례 , false : 니 차례
            if(turningCoin)
            {
                System.out.println("\n내 포켓몬 정보");
                System.out.println(currMonster.printInfo());
                System.out.println("\n적 포켓몬 정보");
                System.out.println(enemy.printInfo()+ "\n\n");
                //내 차례
                System.out.println("<나의 차례>");
                System.out.println("Lv. " + currMonster.getLevel() + " " + currMonster.getName() + "의 체력 : " + currMonster.getHealthPoint() +"/" + currMonster.getHealthPointMax());
                System.out.println("1. 공격");
                System.out.println("2. 몬스터 볼을 던진다.");
                System.out.println("3. 도망간다.");
                
                String nextVal = sc1.next();
                sc1.nextLine();
                int casePrsDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0)));
                switch(casePrsDecision)
                {
                //1-1. 공격
                case 1 :
                    System.out.println("힘내라 !!" + currMonster.getName());
                    boolean isWin = !currMonster.attack(enemy);
                    
                    //상대를 쓰러뜨렸으면 경험치를 얻는다.
                    if(isWin) currMonster.setExpCur(enemy);
                    
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    break;
                //1-2. 몬스터 볼을 던진다.
                case 2 :
                    me.catchMonster(enemy);//true : 잡음 , false : 못잡음
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    break;
                //1-3. 도망간다.
                case 3 :
                    System.out.println(me.getName() + "는(은) 전의를 잃고 도망쳤다.");
                    for(Monster myMonster : myMonsters)
                    {
                        myMonster.setBattleStatus(false);//전투불능 상태로 변경
                    }
                    break;
                }
            }
            else
            {
                System.out.println("<상대의 차례>");
                //니 차례
                //상대차례를 계산하는 랜덤함수 사용
                //0 ~ 99까지의 랜덤 함수 실행
                //5미만은 도망을 선택한다(5%확률로 도망)
                int actionInt = (int)(Math.random() * 100);
                System.out.println("Lv." + enemy.getLevel() + " " + enemy.getName() + "의 체력 : " + enemy.getHealthPoint() +"/" + enemy.getHealthPointMax());
                if(actionInt < 5)
                {
                    System.out.println("적의 " + enemy.getName() + "는(은) 전의를 잃고 도망쳤다.\n");
                    currMonster.setExpCur(enemy);//적이 도망가도 경험치를 준다.
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    enemy.setBattleStatus(false);
                }
                else
                {
                    System.out.print("적의 ");
                    //attack 메소드는 상태의 전투상태를 반환한다.
                    boolean currMonStatus = enemy.attack(currMonster);

                    // 내 몬스터가 쓰러졌을때
                    if(!currMonStatus)
                    {
                        //다음 주자가 있는지 확인
                        if(isFightable(me))
                        {
                            System.out.println("돌아와!  "+ currMonster.getName() +"!");
                        }
                    }
                    turningCoin = !turningCoin;//턴을 돌려준다.
                }
            }
        }
    }

    /**
     * 트레이너 VS 트레이너
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    @SuppressWarnings("resource")
    public void startFight(MonsterTrainer me, MonsterTrainer enemy)
    {
        System.out.println("트레이너 VS 트레이너");
        me.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.
        enemy.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.

        List<Monster> myMonsters    = me.getMonsterBalls();
        List<Monster> enemyMonsters = enemy.getMonsterBalls();
        Monster currMonster         = null; 
        Monster currEnemyMonster    = null;

        Scanner sc1 = null;
        while(true)
        {   
            sc1 = new Scanner(System.in);
            //둘중에 하나라도 전투불가 상태가 있으면 전투를 종료한다.
            if(whoIsWinner(me, enemy) != null)
            {
                String winnersName = "";
                if(whoIsWinner(me, enemy) instanceof MonsterTrainer)
                {
                    MonsterTrainer winner = (MonsterTrainer)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                    winner.setExpCur(enemy);
                }
                if(whoIsWinner(me, enemy) instanceof Monster)
                {
                    Monster winner = (Monster)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                }
                //전적계산 : 내가 이겼을떄
                if(winnersName.equals(me.getName())) me.setWinCount(me.getWinCount()+1);
                //전적계산 : 내가 졌을떄
                else me.setLoseCount(me.getLoseCount()+1);
                System.out.println("\n" + winnersName + "가 승리하였다!!");
                System.out.println("전투를 종료합니다\n\n");
                break;
            }

            //me 트레이너 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster myMonster : myMonsters)
            {
                if(myMonster.isBattleStatus())
                {
                    currMonster = myMonster;
                }
            }
            //enemy 트레이너 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster enemyMonster : enemyMonsters)
            {
                if(enemyMonster.isBattleStatus())
                {
                    currEnemyMonster = enemyMonster;
                }
            }
            
            System.out.println("\n*********************************************************");
            //내차례냐 니차례냐 true : 내 차례 , false : 니 차례
            if(turningCoin)
            {
                System.out.println("\n내 포켓몬 정보");
                System.out.println(currMonster.printInfo());
                System.out.println("\n적 포켓몬 정보");
                System.out.println(currEnemyMonster.printInfo()+ "\n\n");
                //내 차례
                System.out.println("<나의 차례> 포켓몬 수 : " + getFightableMonsterCount(me) + "/" + me.getMonsterBallCount());
                System.out.println("Lv. " + currMonster.getLevel() + " " + currMonster.getName() + "의 체력 : " + currMonster.getHealthPoint() +"/" + currMonster.getHealthPointMax());
                System.out.println("1. 공격");
                System.out.println("2. 몬스터 볼을 던진다.");
                System.out.println("3. 도망간다.");
                
                String nextVal = sc1.next();
                sc1.nextLine();
                int casePrsDecision = Integer.parseInt(String.valueOf(nextVal.charAt(0)));
                switch(casePrsDecision)
                {
                //1-1. 공격
                case 1 :
                    System.out.println("힘내라 !!" + currMonster.getName());
                    boolean isWin = !currMonster.attack(currEnemyMonster);
                    
                    //상대를 쓰러뜨렸으면 경험치를 얻는다.
                    if(isWin)
                    {
                        currMonster.setExpCur(currEnemyMonster);

                        //다음 주자가 있는지 확인
                        if(isFightable(enemy))
                        {
                            System.out.println(currEnemyMonster.getTrainerName() + " : 돌아와!  "+ currEnemyMonster.getName() +"!");
                        }
                    }
                    
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    break;
                //1-2. 몬스터 볼을 던진다.
                case 2 :
                    me.catchMonster(currEnemyMonster);//true : 잡음 , false : 못잡음
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    break;
                //1-3. 도망간다.
                case 3 :
                    System.out.println(me.getName() + "는(은) 전의를 잃고 도망쳤다.");
                    for(Monster myMonster : myMonsters)
                    {
                        myMonster.setBattleStatus(false);//전투불능 상태로 변경
                    }
                    break;
                }
            }
            else
            {
                System.out.println("<상대의 차례> 포켓몬 수 : " + getFightableMonsterCount(enemy) + "/" + enemy.getMonsterBallCount());
                //니 차례
                System.out.println("Lv." + currEnemyMonster.getLevel() + " " + currEnemyMonster.getName() + "의 체력 : " + currEnemyMonster.getHealthPoint() +"/" + currEnemyMonster.getHealthPointMax());
                System.out.println("힘내라 !!" + currEnemyMonster.getName());
                System.out.print(currEnemyMonster.getTrainerName() + "씨의 ");
                //attack 메소드는 상태의 전투상태를 반환한다.
                boolean currMonStatus = currEnemyMonster.attack(currMonster);
                
                // 내 몬스터가 쓰러졌을때
                if(!currMonStatus)
                {
                    //다음 주자가 있는지 확인
                    if(isFightable(me))
                    {
                        System.out.println(currMonster.getTrainerName() + " : 돌아와!  "+ currMonster.getName() +"!");
                    }
                }
                turningCoin = !turningCoin;//턴을 돌려준다.
            }
        }
    }
    
    /**
     * 자동 싸움실행 메인
     * 자동 사냥시 상대는 도망가지 않고 오직 전투 메세지만 출력된다.
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     * @param Animal
     */
    public void startAutoFightMain(MonsterTrainer me, Animal  enemy)
    {
        System.out.print("자동 싸움실행 메인 ");
        
        if(enemy instanceof Monster)
        {
            startAutoFight(me,(Monster)enemy);
        }
        if(enemy instanceof MonsterTrainer)
        {
            startAutoFight(me,(MonsterTrainer)enemy);
        }
    }
    
    /**
     * 자동 사냥 트레이너 VS 몬스터
     * 자동 사냥시 몬스터는 도망가지 않는다.
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    @SuppressWarnings("resource")
    public void startAutoFight(MonsterTrainer me, Monster enemy)
    {
        System.out.println("트레이너 VS 몬스터");
        me.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.
        
        List<Monster> myMonsters = me.getMonsterBalls();
        Monster currMonster      = null; 

        Scanner sc1 = null;
        while(true)
        {   
            sc1 = new Scanner(System.in);
            //둘중에 하나라도 전투불가 상태가 있으면 전투를 종료한다.
            if(whoIsWinner(me, enemy) != null)
            {
                String winnersName = "";
                if(whoIsWinner(me, enemy) instanceof MonsterTrainer)
                {
                    MonsterTrainer winner = (MonsterTrainer)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                    winner.setExpCur(enemy);
                }
                if(whoIsWinner(me, enemy) instanceof Monster)
                {
                    Monster winner = (Monster)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                }
                //전적계산 : 내가 이겼을떄
                if(winnersName.equals(me.getName())) me.setWinCount(me.getWinCount()+1);
                //전적계산 : 내가 졌을떄
                else me.setLoseCount(me.getLoseCount()+1);
                
                System.out.println("\n" + winnersName + "가 승리하였다!!");
                System.out.println("전투를 종료합니다\n\n");
                break;
            }

            //me 트레이너 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster myMonster : myMonsters)
            {
                if(myMonster.isBattleStatus())
                {
                    currMonster = myMonster;
                }
            }

            
            System.out.println("\n*********************************************************");
            //내차례냐 니차례냐 true : 내 차례 , false : 니 차례
            if(turningCoin)
            {
                //내 차례
                System.out.println("\n내 포켓몬 정보");
                System.out.println(currMonster.printInfo());
                System.out.println("\n적 포켓몬 정보");
                System.out.println(enemy.printInfo()+ "\n\n");
                System.out.println("<나의 차례>");
                System.out.println("Lv. " + currMonster.getLevel() + " " + currMonster.getName() + "의 체력 : " + currMonster.getHealthPoint() +"/" + currMonster.getHealthPointMax());
                System.out.println("힘내라 !!" + currMonster.getName());
                boolean isWin = !currMonster.attack(enemy);
                
                //상대를 쓰러뜨렸으면 경험치를 얻는다.
                if(isWin) currMonster.setExpCur(enemy);
                
                turningCoin = !turningCoin;//턴을 돌려준다.
            }
            else
            {
                //니 차례
                System.out.println("<상대의 차례>");
                System.out.println("Lv." + enemy.getLevel() + " " + enemy.getName() + "의 체력 : " + enemy.getHealthPoint() +"/" + enemy.getHealthPointMax());
                System.out.print("적의 ");
                //attack 메소드는 상태의 전투상태를 반환한다.
                enemy.attack(currMonster);
                turningCoin = !turningCoin;//턴을 돌려준다.
            }
        }
    }

    /**
     * 자동 사냥 트레이너 VS 트레이너
     * 트레이너는 도망가지 않는다.
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    @SuppressWarnings("resource")
    public void startAutoFight(MonsterTrainer me, MonsterTrainer enemy)
    {
        System.out.println("트레이너 VS 트레이너");
        me.healthReset();   // 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.
        enemy.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.

        List<Monster> myMonsters    = me.getMonsterBalls();
        List<Monster> enemyMonsters = enemy.getMonsterBalls();
        Monster currMonster         = null; 
        Monster currEnemyMonster    = null;

        while(true)
        {   
            //둘중에 하나라도 전투불가 상태가 있으면 전투를 종료한다.
            if(whoIsWinner(me, enemy) != null)
            {
                String winnersName = "";
                if(whoIsWinner(me, enemy) instanceof MonsterTrainer)
                {
                    MonsterTrainer winner = (MonsterTrainer)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                    winner.setExpCur(enemy);
                }
                if(whoIsWinner(me, enemy) instanceof Monster)
                {
                    Monster winner = (Monster)whoIsWinner(me, enemy);
                    winnersName = winner.getName();
                }
                //전적계산 : 내가 이겼을떄
                if(winnersName.equals(me.getName())) me.setWinCount(me.getWinCount()+1);
                //전적계산 : 내가 졌을떄
                else me.setLoseCount(me.getLoseCount()+1);
                
                System.out.println("\n" + winnersName + "가 승리하였다!!");
                System.out.println("전투를 종료합니다\n\n");
                break;
            }

            //me 트레이너 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster myMonster : myMonsters)
            {
                if(myMonster.isBattleStatus())
                {
                    currMonster = myMonster;
                }
            }
            //enemy 트레이너 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster enemyMonster : enemyMonsters)
            {
                if(enemyMonster.isBattleStatus())
                {
                    currEnemyMonster = enemyMonster;
                }
            }
            
            System.out.println("\n*********************************************************");
            //내차례냐 니차례냐 true : 내 차례 , false : 니 차례
            if(turningCoin)
            {
                //내 차례
                System.out.println("\n내 포켓몬 정보");
                System.out.println(currMonster.printInfo());
                System.out.println("\n적 포켓몬 정보");
                System.out.println(currEnemyMonster.printInfo()+ "\n\n");
                System.out.println("<나의 차례> 포켓몬 수 : "  + getFightableMonsterCount(me) + "/" + me.getMonsterBallCount());
                System.out.println("Lv. " + currMonster.getLevel() + " " + currMonster.getName() + "의 체력 : " + currMonster.getHealthPoint() +"/" + currMonster.getHealthPointMax());
                System.out.println("힘내라 !!" + currMonster.getName());
                boolean isWin = !currMonster.attack(currEnemyMonster);
                
                //상대를 쓰러뜨렸으면 경험치를 얻는다.
                if(isWin)
                {
                    currMonster.setExpCur(currEnemyMonster);
                    
                    //다음 주자가 있는지 확인
                    if(isFightable(enemy))
                    {
                        System.out.println(currEnemyMonster.getTrainerName() + " : 돌아와!  "+ currEnemyMonster.getName() +"!");
                    }
                }
                turningCoin = !turningCoin;//턴을 돌려준다.
            }
            else
            {
                //니 차례
                System.out.println("<상대의 차례> 포켓몬 수 : " + getFightableMonsterCount(enemy) + "/" + enemy.getMonsterBallCount());
                System.out.println("Lv." + currEnemyMonster.getLevel() + " " + currEnemyMonster.getName() + "의 체력 : " + currEnemyMonster.getHealthPoint() +"/" + currEnemyMonster.getHealthPointMax());
                System.out.println("힘내라 !!" + currEnemyMonster.getName());
                System.out.print(currEnemyMonster.getTrainerName() + "씨의 ");
                //attack 메소드는 상태의 전투상태를 반환한다.
                boolean currMonStatus = currEnemyMonster.attack(currMonster);
                
                // 내 몬스터가 쓰러졌을때
                if(!currMonStatus)
                {
                    //다음 주자가 있는지 확인
                    if(isFightable(me))
                    {
                        System.out.println(currMonster.getTrainerName() + " : 돌아와!  "+ currMonster.getName() +"!");
                    }
                }
                turningCoin = !turningCoin;//턴을 돌려준다.
            }
        }
    }
    
    /** 전투가능상태를 알아본다.
     * false : 전투불가, true : 전투가능
     * @param anyone
     * @return
     */
    public static boolean isFightable(Object anyone)
    {
        boolean rtnBool = false;
        
        //Monster 가 들어왔을때
        if(anyone instanceof Monster)
        {
            return ((Monster)anyone).isBattleStatus();
        }
        //MonsterTrainer 가 들어왔을때
        else
        {
            List<Monster> monsters = ((MonsterTrainer)anyone).getMonsterBalls();
            
            for (Monster monster : monsters)
            {
                //한마리라도 전투가 가능하면 전투가능상태
                if(monster.isBattleStatus())
                {
                    rtnBool = true;
                }
            }
            return rtnBool;
        }
    }

    /** 전투가능한 몬스터 수를 구한다
     * @param anyone
     * @return
     */
    public static int getFightableMonsterCount(MonsterTrainer anyone)
    {
        int count = 0;
        
        List<Monster> monsters = anyone.getMonsterBalls();
        
        for (Monster monster : monsters)
        {
            //한마리라도 전투가 가능하면 전투가능상태
            if(monster.isBattleStatus())
            {
                count ++;
            }
        }
        return count;
    }
    
    /**
     * 두선수의 상태를 파악해서 승자를 판단한다.
     * @param endFight
     * @param trainer
     * @param enemy
     * @return
     */
    public static Object whoIsWinner(MonsterTrainer trainer, Object enemy)
    {
        Object obj = null;
        
        //둘다 전투가 가능한 상태 : 무승무 결판 안남
        if(isFightable(trainer) && isFightable(enemy))
        {
            return null;//무승부
        }
        //트레이너가 전투불능인상태 : enemy 승리
        if(!isFightable(trainer) && isFightable(enemy))
        {
            obj = (Object)enemy;//enemy 승리
            return obj;
        }
        //enemy 가 전투불능인상태 : 트레이너 승리
        if(isFightable(trainer) && !isFightable(enemy))
        {
            obj = (Object)trainer;//트레이너 승리
            return obj;
        }
        
        return null;
    }
    
    /**
     * 싸움실행 몬스터 VS 몬스터
     */
    public void startFight(Monster myMonster, Monster enemy)
    {
        System.out.println("싸움실행 메인 몬스터 VS 몬스터");
    }
}
