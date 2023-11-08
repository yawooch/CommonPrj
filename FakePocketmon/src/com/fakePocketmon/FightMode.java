package com.fakePocketmon;

import java.util.List;
import java.util.Scanner;

public class FightMode
{
    private boolean turningCoin = true; //턴제 코인 true : 내차례, false : 니차례
    /**
     * 싸움실행 메인 트레이너 VS 몬스터
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    @SuppressWarnings("resource")
    public void startFight(MonsterTrainer me, Monster enemy)
    {
        System.out.println("싸움실행 메인 트레이너 VS 몬스터");
        me.healthReset();// 이전 싸움에서 전투불능된 포켓몬을 다시 체력 리셋한다.
        
        List<Monster> myMonsters = me.getMonsterBalls();
        Monster currMonster      = null; 

        //트레이터 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
        for(Monster myMonster : myMonsters)
        {
            if(myMonster.isBattleStatus())
            {
                currMonster = myMonster;
            }
        }

        Scanner sc1 = null;
        while(true)
        {   
            sc1 = new Scanner(System.in);
            //둘중에 하나라도 전투불가 상태가 있으면 전투를 종료한다.
            if(whoIsWinner(me, enemy) != null)
            {
                System.out.println("\n" + whoIsWinner(me, enemy) + "가 승리하였다!!");
                System.out.println("전투를 종료합니다");
                break;
            }

            //트레이터 몬스터에서 사용가능한 포켓몬을현재 몬스터로 지정
            for(Monster myMonster : myMonsters)
            {
                if(myMonster.isBattleStatus())
                {
                    currMonster = myMonster;
                }
            }
            
            //내차례냐 니차례냐 true : 내 차례 , false : 니 차례
            if(turningCoin)
            {
                //내 차례
                System.out.println("\n<나의 차례>");
                System.out.println(currMonster.getMonsterName() + "의 체력 : " + currMonster.getHealthPoint() +"/" + currMonster.getHealthPointMax());
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
                    System.out.println("힘내라 !!" + currMonster.getMonsterName());
                    currMonster.attack(enemy, currMonster.getAttackPoint());
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    break;
                //1-2. 몬스터 볼을 던진다.
                case 2 :
                    me.catchMonster(enemy);//true : 잡음 , false : 못잡음
                    break;
                //1-3. 도망간다.
                case 3 :
                    System.out.println(me.getTtrainerName() + "는(은) 전의를 잃고 도망쳤다.");
                    for(Monster myMonster : myMonsters)
                    {
                        myMonster.setBattleStatus(false);//전투불능 상태로 변경
                    }
                    break;
                }
            }
            else
            {
                System.out.println("\n<상대의 차례>");
                //니 차례
                //상대차례를 계산하는 랜덤함수 사용
                //0 ~ 9까지의 랜덤 함수 실행
                //0은 도망을 선택한다(10%확률로 도망)
                int actionInt = (int)(Math.random() * 10);
                System.out.println(enemy.getMonsterName() + "의 체력 : " + enemy.getHealthPoint() +"/" + enemy.getHealthPointMax());
                if(actionInt == 0)
                {
                    System.out.println(enemy.getMonsterName() + "는(은) 전의를 잃고 도망쳤다.");
                    turningCoin = !turningCoin;//턴을 돌려준다.
                    enemy.setBattleStatus(false);
                }
                else
                {
                    //attack 메소드는 상태의 전투상태를 반환한다.
                    boolean currMonStatus = enemy.attack(currMonster, enemy.getAttackPoint());

                    // 내 몬스터가 쓰러졌을때
                    if(!currMonStatus)
                    {
                        //다음 주자가 있는지 확인
                        if(isFightable(me))
                        {
                            System.out.println("돌아와!  "+ currMonster +"!");
                        }
                    }
                    turningCoin = !turningCoin;//턴을 돌려준다.
                }
            }
        }
    }

    /** 
     * 몬스터의 전투가능상태를 알아본다.
     * false : 전투불가, true : 전투가능
     * @param monster
     * @return
     */
    public static boolean isFightable(Monster monster)
    {
        return monster.isBattleStatus();
    }
    /** 트레이너의 전투가능상태를 알아본다.
     * false : 전투불가, true : 전투가능
     * @param trainer
     * @return
     */
    public static boolean isFightable(MonsterTrainer trainer)
    {
        boolean rtnBool = false;
        
        List<Monster> monsters = trainer.getMonsterBalls();
        
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
    
    /**
     * 두선수의 상태를 파악해서 승자를 판단한다.
     * @param endFight
     * @param trainer
     * @param monster
     * @return
     */
    public static String whoIsWinner(MonsterTrainer trainer, Monster monster)
    {
        //둘다 전투가 가능한 상태 : 무승무 결판 안남
        if(isFightable(trainer) && isFightable(monster))
        {
            return null;//무승부
        }
        //트레이너가 전투불능인상태 : 몬스터 승리
        if(!isFightable(trainer) && isFightable(monster))
        {
            return monster.getMonsterName();//몬스터승리
        }
        //몬스터가 전투불능인상태 : 트레이너 승리
        if(isFightable(trainer) && !isFightable(monster))
        {
            return trainer.getTtrainerName();//트레이너
        }
        return null;
    }
    
    /**
     * 싸움실행 메인 트레이너 VS 트레이너
     * "나" 는 싸움시킬 포켓몬이 한마리 이상 있다는 전제하에 startFight가 실행됨(아니면 오류)
     */
    public void startFight(MonsterTrainer me, MonsterTrainer enemy)
    {
        System.out.println("싸움실행 메인 트레이너 VS 트레이너");
    }
    /**
     * 싸움실행 몬스터 VS 몬스터
     */
    public void startFight(Monster myMonster, Monster enemy)
    {
        System.out.println("싸움실행 메인 몬스터 VS 몬스터");
    }
}
