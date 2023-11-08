package com.fakePocketmon;

import java.util.ArrayList;
import java.util.List;

public class MonsterTrainer
{ 
    private String trainerName         = "";     //트레이너 네임 
    private int experiencePoint        = 0;      //현재경험치 
    private int experiencePointMax     = 100;    //경험치상한
    private int monsterBallsMax        = 3;      //잡을수 있는 몬스터수
    private int trainerLevel           = 1;      //트레이너레벨
    private List<Monster> monsterBalls = null;   //잡을수 있는 몬스터수 
    
    public MonsterTrainer()
    {
        trainerName  = "지우";
        monsterBalls = new ArrayList<Monster>();
    }
    

    /**
     *  체력을 리셋한다.
     */
    public void healthReset()
    {
        for(Monster monsterBall : monsterBalls)
        {
            monsterBall.setHealthPoint(monsterBall.getHealthPointMax());
            monsterBall.setBattleStatus(true);
        }
    }
    
    
    /**
     * @param sparePartner
     * @return boolean 몬스터 포획의 결과값 반환 true : 
     */
    public boolean catchMonster(Monster sparePartner)
    {
        double remainHealth = (double)sparePartner.getHealthPoint() / (double)sparePartner.getHealthPointMax()*100;// 남은 HP % 로 계산
        System.out.println(sparePartner.getHealthPoint()+ " / " + sparePartner.getHealthPointMax());// 남은 HP % 로 계산
        System.out.println("남은 체력 : "+ remainHealth + "%");
        //체력이 20% 초과로 남았을 때는 잡을수 없다.
        if(remainHealth > 20 && monsterBalls.size() != 0)
        {
            System.out.println("체력이 넘쳐 잡을수 없다!!");
            return false;
        }
        
        // TODO : 포획률 기능 추가하면...?
        System.out.println(sparePartner.getMonsterName() + "을(를) 잡았다!!");
        sparePartner.setBattleStatus(false);
        monsterBalls.add(sparePartner);
        return true;
    }

    public boolean addMonster(Monster sparePartner)
    {
        monsterBalls.add(sparePartner);
        return true;
    }
    public String getTtrainerName()
    {
        return trainerName;
    }
    
    public List<Monster> getMonsterBalls()
    {
        return monsterBalls;
    }

    public int getMonsterBallCount()
    {
        return monsterBalls.size();
    }

    public String printInfo()
    {
        String rtnInfo = "";
        
        rtnInfo += "\n";
        rtnInfo += "트레이너이름 : "  + trainerName  + "\n";
        rtnInfo += "트레이너레벨 : "  + trainerLevel + "\n";
        rtnInfo += "경험치 :"         + experiencePoint +"/" + experiencePointMax  +"\n";
        rtnInfo += "잡은몬스터 수 :"  + monsterBalls.size()  +"/" + monsterBallsMax  + "\n";
        return rtnInfo;
    }
}
